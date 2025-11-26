package com.ussp.service.impl;

import com.alibaba.fastjson.JSON;
import com.ussp.mapper.QuestionMapper;
import com.ussp.mapper.QuestionnaireMapper;
import com.ussp.mapper.PlanningMapper;
import com.ussp.pojo.Answer;
import com.ussp.pojo.Question;
import com.ussp.service.PlanningService;
import com.ussp.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningMapper planningMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

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
}