package com.ussp.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class PromptBuilder {

    public static String buildReportPrompt(
            String preferredDirection,
            String systemDirection,
            String finalDirection,
            String aiReason,
            String rejectReason,
            int kaoyan, int kaogong, int jiuye,
            String grade, String major,
            String interestTags, String abilityTags, String personalityTags,
            String otherSelf,
            String templateJson
    ) {
        String scores = String.format("考研=%d，考公=%d，就业=%d", kaoyan, kaogong, jiuye);

        // JSON 格式化
        String prettyJson = templateJson;
        try {
            prettyJson = JSONObject.parseObject(templateJson).toJSONString();
        } catch (Exception ignore) {}

        // 清洗标签
        String cleanInterest = stripBrackets(interestTags);
        String cleanAbility = stripBrackets(abilityTags);
        String cleanPersonality = stripBrackets(personalityTags);

        String prompt = """
你是一名“大学生学习与发展规划专家”。你需要基于用户画像、方向得分、系统识别结果和方向规则模板，
生成 **自然、专业、逻辑完备、可直接执行的个性化发展规划报告**。
不得出现任何占位符、AI 口吻或生成提示语。
----------------------------------------
【核心前提要求】
所有方向得分，仅参考，权重最低，报告中不能出现所有方向的得分。
方向规则模板仅作为参考依据，需先对模板逻辑、内容合理性进行研判。
模板优化需遵循“逻辑闭环、贴合个人、可执行”原则，比如补充模板缺失的关键任务、替换不适配的学习内容、调整阶段规划节奏等。
优化后需在报告相关部分自然融入，无需单独标注“模板优化”，确保全文风格统一。

----------------------------------------
【用户发展方向信息】
- 用户选择：%s
- AI推荐：%s
- 最终确定方向：%s
- AI 推荐方向及理由：%s
- 用户拒绝 AI 推荐的原因：%s
- 所有方向得分：%s

【用户画像】
- 年级：%s    专业：%s
- 兴趣标签：%s
- 能力标签：%s
- 性格特征：%s
- 其他自述信息：%s

【方向规则模板（可参考，但不能机械照搬。仅为辅助参考，不可被模板限制核心规划逻辑）】
%s
----------------------------------------

【最终生成要求】
你必须根据 **最终方向 + 用户画像 + 方向得分 + 规则模板** 进行综合分析，写出一份完整规划报告。
不得出现对话式语气、不得复述题目、不得解释写作过程。

最终报告必须严格包含以下 7 个部分，每部分以中文序号标题呈现（如：“一、适合该方向的理由”）：

一、适合该方向的理由
- 结合兴趣、能力、性格、专业背景解释匹配原因；
- 如用户选择与系统推荐不一致，需温和分析差异并给出合理性论证；
- 必须解释最终方向的科学依据。

二、总体规划
- 按短期/中期/长期分阶段拆解；
- 每阶段写关键目标 + 核心任务 + 可量化成果；
- 内容必须明确可执行，不得空泛。

三、时间安排（自然语言化 timeline）
- 若为考研/考公：写关键时间节点（资料准备、复习节奏、报名、初试、复试等）；
- 若为就业：写能力建设节奏、项目实践、实习节奏；
- 语句必须流畅，而不是列表堆叠。

四、学习路径建议
- 基于模板中的 study_path，但必须经过自然语言扩写；
- 解释为什么这样学、学习顺序、每一步的目标；
- 不能只是罗列知识点。

五、考试或关键节点参考
- 必须包含考试日期预测参考（如公务员考试、计算机等级考试、四六级考试、考研等适配方向的考试等）；
- 若方向为考研或考公涉及固定考试：结合适配考试的日期预测，同步结合 exam_reference 展开策略、误区、得分思路；
- 若方向为就业无固定考试：优先明确适配就业场景的基础/加分考试并给出日期预测，再补充关键发展节点、准备策略和执行流程。

六、风险点与应对策略
- 基于模板 risks，但需结合用户画像个性化展开；
- 每个风险必须给出对应且可执行的解决方案。

七、额外个性化建议
- 深度结合兴趣、能力、性格、自述信息；
- 给出用户能立即实施的具体“小目标”“小习惯”；
- 禁止使用通用模板语气，必须体现“私人定制”。

----------------------------------------
【写作规范】
- 全文总字数必须严格控制在 **2800–3000 字**；
- 文风自然、友好、专业，无 AI 痕迹；
- 结构完整、内容连贯、段落之间过渡自然；
- 不能出现任何格式占位符（比如占位符符号，例如百分号形式、花括号、方括号等）；
- 不能使用“我会”“作为 AI”等措辞；
- 最终内容必须完整，不得戛然而止。

请开始生成报告。
""".formatted(
                safe(preferredDirection),
                safe(systemDirection),
                safe(finalDirection),
                safe(aiReason),
                safe(rejectReason),
                scores,
                safe(grade),
                safe(major),
                cleanInterest,
                cleanAbility,
                cleanPersonality,
                safe(otherSelf),
                prettyJson
        );

        return prompt;
    }

    public static String buildDirectionPrompt(
            String preferredDirection,
            Map<String, Integer> riasec,
            Map<String, Integer> ability,
            Map<String, Integer> self,
            int kaoyan, int kaogong, int jiuye,
            String grade,
            String major,
            String interestTags,
            String abilityTags,
            String selfEvalTags,
            String otherSelf
    ) {

        String riasecStr = mapToReadableString(riasec);
        String abilityStr = mapToReadableString(ability);   // learning=xx...
        String selfStr = mapToReadableString(self);         // postgraduate_intent=...

        String scores = String.format("考研=%d，考公=%d，就业=%d", kaoyan, kaogong, jiuye);

        String cleanInterest = stripBrackets(interestTags);
        String cleanAbility  = stripBrackets(abilityTags);
        String cleanSelfEval = stripBrackets(selfEvalTags);

        return """
你是一名大学生学习与发展规划分析助手。请基于以下信息生成一段 150–220 字的推荐方向理由说明。

要求（非常重要）：
1. **第一句话必须严格使用以下格式：**
   “AI推荐你最适合的方向是：XXX。”
   其中 XXX 必须从【考研 / 考公 / 就业】中选择且只能出现一个。
2. **决定推荐方向的主要依据必须是：**
   - 用户六维度兴趣（霍兰德职业兴趣理论RIASEC）
   - 用户能力测评分数
   - 用户自我评价分数
   - 用户的兴趣/能力/自评标签
   - 用户的年级、专业情况
   - 用户自述内容
3. **三方向匹配分数（kaoyan/kaogong/jiuye）只能作为“次要参考”，不能直接决定推荐方向。**
   推荐理由中不得提到和出现三方向匹配分数。  
   若三方向分数最高的方向与综合特征不一致，应根据综合特征给出更合理的方向，并简要说明原因。
4. 若用户偏好方向与系统推荐不同，请自然、委婉说明原因。
5. 语言自然、简洁、专业，不使用条目符号，不使用标题，全篇不超过 220 字。

以下是用户数据：
- 用户偏好方向：%s
- 用户六维度兴趣分数（霍兰德职业兴趣理论RIASEC）：%s
- 用户能力测评分数：%s
- 用户自我评价分数：%s
- 三方向匹配分数（仅供参考，权重最低）：%s
- 用户年级：%s
- 用户专业：%s
- 兴趣标签：%s
- 能力标签：%s
- 自我评价标签：%s
- 用户自述：%s

请基于以上信息生成一段推荐理由文本，不超过 220 字。
""".formatted(
                safe(preferredDirection),
                riasecStr,
                abilityStr,
                selfStr,
                scores,
                safe(grade),
                safe(major),
                cleanInterest,
                cleanAbility,
                cleanSelfEval,
                safe(otherSelf)
        );

    }

    // Map 转简短字符串：{R=10,I=5} → "R=10, I=5"
    private static String mapToReadableString(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) return "";
        return map.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }


    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String stripBrackets(String s) {
        if (s == null) return "";
        return s.replace("[", "").replace("]", "");
    }
}