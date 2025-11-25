package com.ussp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ussp.dto.AnswerDTO;
import com.ussp.dto.QuestionnaireSubmitDTO;
import com.ussp.mapper.*;
import com.ussp.pojo.OptionItem;
import com.ussp.pojo.Question;
import com.ussp.pojo.Questionnaire;
import com.ussp.service.QuestionnaireService;
import com.ussp.vo.DirectionResult;
import com.ussp.vo.QuestionnaireResultVO;
import com.ussp.vo.ScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserDirectionMapper userDirectionMapper;

    @Override
    public List<Questionnaire> getAllQuestionnaires(Integer status) {
        return questionnaireMapper.findQuestionnaires(status);
    }

    @Override
    public boolean deleteQuestionnaireById(Long id) {
        return questionnaireMapper.deleteQuestionnaireById(id) > 0;
    }

    @Override
    public Questionnaire addQuestionnaire(Questionnaire questionnaire) {
        // 1. 校验标题是否重复
        Questionnaire existing = questionnaireMapper.findByTitle(questionnaire.getTitle());
        if (existing != null) {
            return null; // 已存在同名问卷
        }

        // 2. 默认状态
        if (questionnaire.getStatus() == null) {
            questionnaire.setStatus((byte) 1);
        }
        questionnaire.setCreateTime(LocalDateTime.now());

        // 3. 插入
        int rows = questionnaireMapper.insertQuestionnaire(questionnaire);
        if (rows > 0) {
            return questionnaireMapper.findById(questionnaire.getId());
        }

        return null;
    }

    @Override
    public Questionnaire updateQuestionnaire(Long id, Questionnaire questionnaire) {
        // 1. 检查是否存在
        Questionnaire existing = questionnaireMapper.findById(id);
        if (existing == null) return null;

        // 2. 更新非 null 字段
        questionnaire.setId(id);
        int updated = questionnaireMapper.updateQuestionnaire(questionnaire);

        // 3. 返回最新数据
        return updated > 0 ? questionnaireMapper.findById(id) : null;
    }

    @Override
    public int getQuestionnaireCount() {
        return questionnaireMapper.countQuestionnaires();
    }

    @Transactional
    @Override
    public QuestionnaireResultVO processSubmit(QuestionnaireSubmitDTO dto) {
        Long userId = dto.getUserId();

        // ① 更新基础信息
        userMapper.updateMajorAndGrade(userId, dto.getMajor(), dto.getGrade());

        // ② 保存答案（只存内容不存分）
        saveAnswers(userId, dto.getAnswers());

        // ③ 获取用户主动选择方向（Q25）
        String preferred = extractPreferredDirection(dto.getAnswers());

        // ④ 计算分数
        ScoreVO score = calculateScores(dto.getAnswers());

        // ⑤ 保存 user_direction
        DirectionResult result = saveUserDirection(userId, preferred, score);

        return new QuestionnaireResultVO(score, result);
    }



    private void saveAnswers(Long userId, List<AnswerDTO> answers) {
        answers.forEach(a -> {
            String content = (a.getValue() instanceof List)
                    ? JSON.toJSONString(a.getValue())
                    : a.getValue().toString();
            answerMapper.insertAnswer(userId, a.getQuestionId(), content);
        });
    }

    private String extractPreferredDirection(List<AnswerDTO> answers) {
        return answers.stream()
                .filter(a -> a.getQuestionId() == 25)
                .findFirst()
                .map(a -> a.getValue().toString())
                .orElse(null);
    }

    private int safeInt(Integer v) {
        return v == null ? 0 : v;
    }

    private OptionItem findOptionSafely(List<OptionItem> list, String key) {
        if (list == null || key == null) return null;
        return list.stream()
                .filter(o -> key.equals(o.getKey()))
                .findFirst()
                .orElse(null);
    }

    // 主函数：计算分数
    private ScoreVO calculateScores(List<AnswerDTO> answers) {
        int ky = 0, kg = 0, jy = 0;

        if (answers == null || answers.isEmpty()) {
            return new ScoreVO(0, 0, 0);
        }

        for (AnswerDTO a : answers) {
            if (a == null) continue;
            Long qid = a.getQuestionId();
            if (qid == null) continue;

            Question q = questionMapper.findById(qid);
            if (q == null) continue;

            // 跳过文本题
            if (q.getType() != null && q.getType().intValue() == 3) continue;

            // --- 将 question.options 统一成 List<OptionItem> ---
            List<OptionItem> opts;
            Object raw = q.getOptions(); // POJO 中的类型是 List<Map<String,Object>>
            try {
                if (raw == null) {
                    opts = Collections.emptyList();
                } else if (raw instanceof String) {
                    // JSON 字符串 -> 解析成对象列表
                    opts = JSON.parseArray((String) raw, OptionItem.class);
                } else {
                    // 已经是 List<Map<...>>，先转成 JSON 再解析为 OptionItem 列表
                    String json = JSON.toJSONString(raw);
                    opts = JSON.parseObject(json, new TypeReference<List<OptionItem>>() {});
                }
            } catch (Exception ex) {
                // 容错：解析失败时跳过这题并记录日志
                System.err.println("解析 question.options 失败，questionId=" + qid + " -> " + ex.getMessage());
                opts = Collections.emptyList();
            }

            // --- 处理 answer 的值（可能是 String、List、或者其他 JSON 可解析的结构） ---
            Object val = a.getValue();
            if (val == null) continue;

            try {
                if (val instanceof String) {
                    String sel = (String) val;
                    OptionItem oi = findOptionSafely(opts, sel);
                    if (oi != null && oi.getWeights() != null) {
                        ky += safeInt(oi.getWeights().getKaoyan());
                        kg += safeInt(oi.getWeights().getKaogong());
                        jy += safeInt(oi.getWeights().getJiuye());
                    }
                } else if (val instanceof List) {
                    // 多选：列表内可能是 String 或其他类型
                    List<?> arr = (List<?>) val;
                    for (Object item : arr) {
                        if (item == null) continue;
                        String sel = item.toString();
                        OptionItem oi = findOptionSafely(opts, sel);
                        if (oi != null && oi.getWeights() != null) {
                            ky += safeInt(oi.getWeights().getKaoyan());
                            kg += safeInt(oi.getWeights().getKaogong());
                            jy += safeInt(oi.getWeights().getJiuye());
                        }
                    }
                } else {
                    // 其他可解析类型（例如 FastJSON 的 JSONArray 等），将其先转为 JSON 字符串再解析为 List<String>
                    String jsonv = JSON.toJSONString(val);
                    List<String> arr = JSON.parseArray(jsonv, String.class);
                    for (String sel : arr) {
                        OptionItem oi = findOptionSafely(opts, sel);
                        if (oi != null && oi.getWeights() != null) {
                            ky += safeInt(oi.getWeights().getKaoyan());
                            kg += safeInt(oi.getWeights().getKaogong());
                            jy += safeInt(oi.getWeights().getJiuye());
                        }
                    }
                }
            } catch (Exception ex) {
                // 单题处理异常时不要中断整体流程
                System.err.println("处理答案时出错 questionId=" + qid + " val=" + a.getValue() + " -> " + ex.getMessage());
            }
        }

        return new ScoreVO(ky, kg, jy);
    }

    private OptionItem findOption(List<OptionItem> list, String key) {
        return list.stream().filter(o -> o.getKey().equals(key)).findFirst().get();
    }

    private DirectionResult saveUserDirection(Long userId, String preferred, ScoreVO score) {
        String system = score.maxDirection();
        int conflict = (preferred != null && !preferred.equals(system)) ? 1 : 0;

        userDirectionMapper.insertOrUpdate(userId, preferred, system, conflict);

        return new DirectionResult(preferred, system, conflict);
    }
}