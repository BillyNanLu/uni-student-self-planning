package com.ussp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ussp.dto.*;
import com.ussp.mapper.*;
import com.ussp.pojo.*;
import com.ussp.service.AiReportService;
import com.ussp.service.PlanningService;
import com.ussp.vo.DirectionMatchVO;
import com.ussp.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningMapper planningMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private UserPlanMapper userPlanMapper;

    @Autowired
    private UserDirectionMapper userDirectionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PlanTemplateMapper planTemplateMapper;

    @Autowired
    private AiReportService aiReportService;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public UserProfileVO getUserProfileTags(Long userId) {

        List<Answer> answers = planningMapper.findAnswersByUserId(userId);
        if (answers == null) answers = Collections.emptyList();

        Set<String> interests = new HashSet<>();
        Set<String> abilities = new HashSet<>();
        Set<String> selfEval = new HashSet<>();

        for (Answer a : answers) {
            if (a == null || a.getQuestionId() == null) continue;

            // 查询问题
            Question q = questionMapper.findById(a.getQuestionId());
            if (q == null) continue;

            // 查询问卷标题
            String questionnaireTitle = questionnaireMapper.findTitleById(q.getQuestionnaireId());
            if (questionnaireTitle == null) continue;

            // 跳过文本题
            if (q.getType() != null && q.getType() == 3) continue;

            // 安全解析 options
            List<Map<String, Object>> options = new ArrayList<>();
            Object optObj = q.getOptions();
            if (optObj != null) {
                try {
                    String jsonStr = (optObj instanceof String) ? (String) optObj : JSON.toJSONString(optObj);
                    List rawList = JSON.parseArray(jsonStr, Map.class); // 返回 List<Map>
                    // 强制转换每个元素为 Map<String,Object>
                    for (Object o : rawList) {
                        if (o instanceof Map) {
                            options.add((Map<String, Object>) o);
                        }
                    }
                } catch (Exception ex) {
                    continue; // 解析失败跳过
                }
            }
            if (options.isEmpty()) continue;
            // 解析用户答案
            List<String> selectedKeys = new ArrayList<>();
            String content = a.getAnswerContent() != null ? a.getAnswerContent().trim() : "";
            try {
                if (content.startsWith("[")) {
                    selectedKeys = JSON.parseArray(content, String.class);
                } else if (content.contains(",")) {
                    selectedKeys = Arrays.asList(content.split(","));
                } else if (!content.isEmpty()) {
                    selectedKeys.add(content);
                }
            } catch (Exception ex) {
                selectedKeys.clear();
                selectedKeys.add(content);
            }

            // 收集 tags
            for (Map<String, Object> opt : options) {
                if (opt == null) continue;
                Object keyObj = opt.get("key");
                if (keyObj == null) continue;
                String key = String.valueOf(keyObj);
                if (!selectedKeys.contains(key)) continue;

                Object tagsObj = opt.get("tags");
                List<String> tags = new ArrayList<>();
                if (tagsObj instanceof List) {
                    for (Object t : (List<?>) tagsObj) if (t != null) tags.add(t.toString());
                } else if (tagsObj != null) {
                    tags.add(tagsObj.toString());
                }

                // 根据问卷 title 分类
                if ("兴趣测评问卷".equals(questionnaireTitle)) interests.addAll(tags);
                else if ("能力测评问卷".equals(questionnaireTitle)) abilities.addAll(tags);
                else if ("自我评价问卷".equals(questionnaireTitle)) selfEval.addAll(tags);
            }
        }

        return new UserProfileVO(
                new ArrayList<>(interests),
                new ArrayList<>(abilities),
                new ArrayList<>(selfEval)
        );
    }

    @Override
    public DirectionMatchVO getDirectionResult(Long userId) {
        UserPlan latest = userPlanMapper.findLatestByUserId(userId);
        UserDirection ud = userDirectionMapper.findByUserId(userId);

        System.out.println(latest);
        System.out.println(ud);

        DirectionMatchVO vo = new DirectionMatchVO();
        vo.setPreferredDirection(ud.getPreferredDirection());
        vo.setRecommend(ud.getSystemDirection());
        vo.setConfirmStatus(ud.getConfirmStatus());
        vo.setFinalDirection(ud.getFinalDirection());

        // 处理 matchRate
        Map<String, Integer> scoreMap = new HashMap<>();
        if (latest != null && latest.getUserScore() != null) {
            Map<String, Integer> json = latest.getUserScore();
            scoreMap.put("考研", json.get("考研"));
            scoreMap.put("考公", json.get("考公"));
            scoreMap.put("就业", json.get("就业"));
        }
        vo.setMatchRate(scoreMap);

        return vo;
    }

    @Override
    public void updateFinalDirection(ConfirmDirectionDTO dto) {
        userDirectionMapper.updateDirection(dto.getUserId(), dto.getStatus(), dto.getFinalDirection(), dto.getRejectReason());
    }


    public PlanningScoreDTO calculateTotalScores(List<AnswerDTO> answers) {
        PlanningScoreDTO result = new PlanningScoreDTO();

        // 初始化六维度 RIASEC
        Arrays.asList("R", "I", "A", "S", "E", "C")
                .forEach(d -> result.getRiasec().put(d, 0));

        // 初始化能力维度
        Arrays.asList("learning","analysis","communication","stress","discipline")
                .forEach(d -> result.getAbility().put(d, 0));

        // 初始化自我评价维度
        Arrays.asList("postgraduate_intent","civil_intent","job_intent","major_interest","exam_patience")
                .forEach(d -> result.getSelfEvaluation().put(d, 0));

        if (answers == null || answers.isEmpty()) return result;

        for (AnswerDTO a : answers) {
            if (a == null || a.getQuestionId() == null) continue;

            Question q = questionMapper.findById(a.getQuestionId());
            if (q == null) continue;

            // --- 从数据库查询问卷标题 ---
            Questionnaire questionnaire = questionnaireMapper.findById(q.getQuestionnaireId());
            if (questionnaire == null) continue;
            String qTitle = questionnaire.getTitle();

            // --- 解析题目选项 ---
            List<OptionItem> opts = parseOptions(q);
            if (opts.isEmpty()) continue;

            // --- 获取用户选择 ---
            Object val = a.getValue();
            if (val == null) continue;

            String sel = val.toString();
            OptionItem oi = findOptionSafely(opts, sel);
            if (oi == null) continue;

            switch (qTitle) {
                case "兴趣测评问卷":
                    if (oi.getInterest() != null) {
                        oi.getInterest().forEach((k, v) ->
                                result.getRiasec().put(k, result.getRiasec().getOrDefault(k, 0) + safeInt(v))
                        );
                    }
                    break;

                case "能力测评问卷":
                    if (oi.getAbility() != null) {
                        oi.getAbility().forEach((k, v) ->
                                result.getAbility().put(k, result.getAbility().getOrDefault(k, 0) + safeInt(v))
                        );
                    }
                    break;

                case "自我评价问卷":
                    if (oi.getSelfEvaluation() != null) {
                        oi.getSelfEvaluation().forEach((k, v) -> {
                            result.getSelfEvaluation().put(
                                    k,
                                    result.getSelfEvaluation().getOrDefault(k, 0) + safeInt(v)
                            );
                        });
                    }
                    break;

                default:
                    break;
            }
        }

        return result;
    }

    private OptionItem findOptionSafely(List<OptionItem> list, String key) {
        if (list == null || key == null) return null;
        System.out.println("用户选择: " + key + " | 所有option.key: " +
                list.stream().map(OptionItem::getKey).toList()
        );
        return list.stream()
                .filter(o -> key.equals(o.getKey()))
                .findFirst()
                .orElse(null);
    }

    private List<OptionItem> parseOptions(Question q) {
        if (q.getOptions() == null) return Collections.emptyList();

        Object raw = q.getOptions();
        try {
            if (raw instanceof String) {
                return JSON.parseArray((String) raw, OptionItem.class);
            } else {
                String json = JSON.toJSONString(raw);
                return JSON.parseObject(json, new TypeReference<List<OptionItem>>() {});
            }
        } catch (Exception ex) {
            System.err.println("解析 options 失败，questionId=" + q.getId() + " -> " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    private int safeInt(Integer i) {
        return i == null ? 0 : i;
    }

    @Override
    @Transactional
    public AiDirectionResponseDTO generateDirection(Long userId, AiDirectionRequestDTO request) {

        // 1. 读取用户相关基础数据
        UserDirection ud = userDirectionMapper.findByUserId(userId);
        Map<String, Object> userBase = userMapper.findBasicInfoById(userId);
        UserPlan latest = userPlanMapper.findLatestByUserId(userId);

        if (ud == null || latest == null) {
            throw new RuntimeException("缺少 user_direction 或 user_plan 数据，无法生成推荐方向");
        }

        // ---------------------- 2. 计算分数（优先使用 request.matchRate）----------------------
        Map<String, Integer> reqScores = request.getMatchRate();
        Map<String, Integer> planScores = latest.getUserScore();

        int ky = getScore(reqScores, planScores, "考研", "kaoyan");
        int kg = getScore(reqScores, planScores, "考公", "kaogong");
        int jy = getScore(reqScores, planScores, "就业", "jiuye");

        System.out.println("ky: " + ky + ", kg: " + kg + ", jy: " + jy);

        // ---------------------- 3. 用户基础信息 ----------------------
        String grade = userBase == null ? "" : (String) userBase.getOrDefault("grade", "");
        String major = userBase == null ? "" : (String) userBase.getOrDefault("major", "");
        System.out.println("grade: " + grade + ", major: " + major);

        // ---------------------- 4. 文本题答案（从数据库获取）----------------------
        String otherSelf = answerMapper.findTextAnswerByUserId(userId);
        System.out.println("用户提交的文本题答案：" + otherSelf);
        if (otherSelf == null) otherSelf = "";

        // ---------------------- 5. 用户画像列表 ----------------------
        String interestTags = request.getInterests().toString();
        String abilityTags = request.getAbilities().toString();
        String selfEvalTags = request.getSelfEvaluation().toString();
        System.out.println("用户的自我画像：" +  "interestTags: " + interestTags + ", abilityTags: " + abilityTags + ", selfEvalTags: " + selfEvalTags);

        // ---------------------- 6. 用户三套分数列表 ----------------------
        List<AnswerDTO> allAnswers = answerMapper.findAllByUserId(userId);
        System.out.println("三套分数列表：" + allAnswers.toString());

        PlanningScoreDTO scoreDTO = calculateTotalScores(allAnswers);

        Map<String, Integer> riasec = scoreDTO.getRiasec();
        Map<String, Integer> ability = scoreDTO.getAbility();
        Map<String, Integer> self = scoreDTO.getSelfEvaluation();

        System.out.println("RIASEC: " + riasec);
        System.out.println("Ability: " + ability);
        System.out.println("SelfEval: " + self);

        // 把三套分数放进 request，AI prompt 会用
        request.setRiasec(riasec);
        request.setAbility(ability);
        request.setSelf(self);


        // ---------------------- 7. 构建 Prompt ----------------------
        String preferred = request.getPreferredDirection();
        System.out.println("用户偏好方向：" + preferred);

        String prompt = com.ussp.utils.PromptBuilder.buildDirectionPrompt(
                preferred,
                riasec, ability, self,
                ky, kg, jy,
                grade, major,
                interestTags, abilityTags, selfEvalTags,
                otherSelf
        );

        // ---------------------- 10. 调用 AI 服务 ----------------------
        String report = aiReportService.generateReport(prompt);

        System.out.println("AI 生成推荐方向：" + report);
        String systemDirection = extractDirection(report);
        System.out.println("AI 生成推荐方向（提取）：" + systemDirection);

        // ---------------------- 11. 更新 user_direction ----------------------
        ud.setAiReason(report);
        ud.setSystemDirection(systemDirection);

        boolean isEqual = systemDirection.equals(preferred);
        if (isEqual) {
            ud.setIsConflict(0);
            ud.setFinalDirection(systemDirection);
            userDirectionMapper.updateAiReasonSystemDirectionAndISConflict(ud);
        }
        else {
            ud.setIsConflict(1);
            ud.setFinalDirection(null);
            userDirectionMapper.updateAiReasonSystemDirectionAndISConflict(ud);
        }

        return new AiDirectionResponseDTO(report, systemDirection);
    }

    @Override
    public AiDirectionResponseDTO getLatestDirectionResult(Long userId) {
        UserDirection ud = userDirectionMapper.findByUserId(userId);
        return new AiDirectionResponseDTO(ud.getAiReason(), ud.getSystemDirection());
    }


    @Override
    @Transactional
    public String generateAiReportForUser(Long userId, AiReportRequestDTO request) {

        // 1. 读取用户相关基础数据
        UserPlan latest = userPlanMapper.findLatestByUserId(userId);
        UserDirection ud = userDirectionMapper.findByUserId(userId);
        Map<String, Object> userBase = userMapper.findBasicInfoById(userId);

        if (ud == null || latest == null) {
            throw new RuntimeException("缺少 user_direction 或 user_plan 数据，无法生成报告");
        }

        // ---------------------- 2. 计算分数----------------------
        Map<String, Integer> reqScores = request.getMatchRate();
        Map<String, Integer> planScores = latest.getUserScore();

        int ky = getScore(reqScores, planScores, "考研", "kaoyan");
        int kg = getScore(reqScores, planScores, "考公", "kaogong");
        int jy = getScore(reqScores, planScores, "就业", "jiuye");

        System.out.println("ky: " + ky + ", kg: " + kg + ", jy: " + jy);

        // ---------------------- 3. 根据三个方向得分选最高分方向 ----------------------
        String maxDir = "考研";
        int maxScore = ky;
        if (kg > maxScore) {
            maxScore = kg;
            maxDir = "考公";
        }
        if (jy > maxScore) {
            maxScore = jy;
            maxDir = "就业";
        }

        System.out.println("最高方向：" + maxDir);

        // ---------------------- 4. 模板选择 ----------------------
        // 现在采用 finalDirection（用户最终确认方向）作为模板方向
        String finalDirection = request.getFinalDirection();
        if (finalDirection == null || finalDirection.isEmpty()) {
            finalDirection = maxDir;  // 兜底
        }

        // 查询数据库中该方向对应的模板
        List<PlanTemplate> templates = planTemplateMapper.findByDirection(finalDirection);

        PlanTemplate chosen = null;
        if (templates != null && !templates.isEmpty()) {

            // 根据最高分选择等级：>=70 → 3，>=40 → 2，其他 → 1
            int rank = 1;
            if (maxScore >= 70) rank = 3;
            else if (maxScore >= 40) rank = 2;

            // 先尝试按 template_name 包含数字匹配
            for (PlanTemplate t : templates) {
                if (t.getTemplateName() != null && t.getTemplateName().contains(String.valueOf(rank))) {
                    chosen = t;
                    break;
                }
            }

            // 若没找到，按下标 fallback
            if (chosen == null) {
                int idx = Math.min(rank - 1, templates.size() - 1);
                chosen = templates.get(idx);
            }
        }

        // ---------------------- 5. 用户基础信息 ----------------------
        String grade = userBase == null ? "" : (String) userBase.getOrDefault("grade", "");
        String major = userBase == null ? "" : (String) userBase.getOrDefault("major", "");
        System.out.println("grade: " + grade + ", major: " + major);

        // ---------------------- 6. 文本题答案（从数据库获取）----------------------
        String otherSelf = answerMapper.findTextAnswerByUserId(userId);
        System.out.println("用户提交的文本题答案：" + otherSelf);
        if (otherSelf == null) otherSelf = "";

        // ---------------------- 7. 模板 JSON ----------------------
        // String templateJson = chosen == null ? "{}" : chosen.getEvaluationRules().toJSONString();
        String templateJson = "{}";
        if (chosen != null && chosen.getEvaluationRules() != null) {
            templateJson = JSONObject.parseObject(chosen.getEvaluationRules().toString()).toJSONString();
        }
        System.out.println("templateJson: " + templateJson);

        // ---------------------- 8. 用户画像列表 ----------------------
        String interestTags = request.getInterests().toString();
        String abilityTags = request.getAbilities().toString();
        String selfEvalTags = request.getSelfEvaluation().toString();
        System.out.println("interestTags: " + interestTags + ", abilityTags: " + abilityTags + ", selfEvalTags: " + selfEvalTags);

        // ---------------------- 9. 用户拒绝理由（不同） ----------------------
        String rejectReason = ud.getRejectReason();

        // ---------------------- 10. Ai推荐方向理由 ----------------------
        String aiReason = ud.getAiReason();

        // ---------------------- 11. 构建 Prompt ----------------------
        String preferred = request.getPreferredDirection();
        String recommend = request.getRecommend();

        String prompt = com.ussp.utils.PromptBuilder.buildReportPrompt(
                preferred, recommend, finalDirection,
                aiReason, rejectReason,
                ky, kg, jy,
                grade, major,
                interestTags, abilityTags, selfEvalTags,
                otherSelf,
                templateJson
        );

        // ---------------------- 12. 调用 AI 服务 ----------------------
        String report = aiReportService.generateReport(prompt);

        // ---------------------- 13. 更新 user_plan ----------------------
        latest.setGeneratedPlan(report);
        latest.setDirection(finalDirection);
        userPlanMapper.updateGeneratedPlanAndDirection(latest);

        return report;
    }

    @Override
    public String getLatestAiReport(Long userId) {
        String report = userPlanMapper.getLatestAiReportByUserId(userId);
        return report;
    }

    @Override
    public List<UserPlan> getUserPlanHistory(Long userId) {
        return userPlanMapper.getUserPlansByUserId(userId);
    }

    private int getScore(Map<String, Integer> req, Map<String, Integer> plan, String cnKey, String enKey) {
        if (req != null) {
            if (req.containsKey(cnKey)) return req.get(cnKey);
            if (req.containsKey(enKey)) return req.get(enKey);
        }
        if (plan != null) {
            if (plan.containsKey(cnKey)) return plan.get(cnKey);
            if (plan.containsKey(enKey)) return plan.get(enKey);
        }
        return 0;
    }

    // 提取AI推荐方向
    public static String extractDirection(String aiText) {
        if (aiText == null) return null;

        // 只取前 40 字（方向一定在前面）
        String head = aiText.trim();
        if (head.length() > 40) {
            head = head.substring(0, 40);
        }

        // 标准关键词
        String[] dirs = {"考研", "考公", "就业"};

        // 逐个匹配
        for (String d : dirs) {
            if (head.contains(d)) {
                return d;
            }
        }

        // 若 GPT 不守规矩，兜底处理 —— 用 “研究生/公务员/工作” 推断
        if (head.contains("研究生") || head.contains("硕士") || head.contains("深造")) return "考研";
        if (head.contains("公务员") || head.contains("体制内") || head.contains("公职")) return "考公";
        if (head.contains("求职") || head.contains("就业") || head.contains("工作")) return "就业";

        return null; // 未匹配到
    }

}