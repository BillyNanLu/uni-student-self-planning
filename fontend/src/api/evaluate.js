// 后端AI报告生成模板（伪代码）
export const generateAiReport = (aiMaterial) => {
    const { userInfo, directionResult, timeRule, examList } = aiMaterial;
    // 模板1：开头（结合用户信息）
    let report = `尊敬的${userInfo.grade}${userInfo.major}专业同学，根据你的测评结果，你的核心发展方向为${directionResult.recommend}（匹配度${directionResult.matchRate[directionResult.recommend]}%），以下是专属规划建议：\n`;

    // 模板2：时间安排（填充时间规则）
    report += `一、阶段性时间安排\n当前处于${timeRule.stage}，建议每日学习计划：${timeRule.dailyPlan}。此阶段核心目标是夯实基础，避免后期冲刺压力过大。\n`;

    // 模板3：考试提醒（遍历考试节点）
    report += `二、关键考试节点提醒\n`;
    examList.forEach(exam => {
        report += `- ${exam.name}（预计${exam.time}）：${exam.note}，建议提前1-2个月进入专项备考。\n`;
    });

    // 模板4：学习路径（结合专业+方向）
    if (directionResult.recommend === '考研' && userInfo.major === '计算机科学') {
        report += `三、学习路径建议\n1. 专业课：3-6月完成《数据结构》《计算机组成原理》一轮复习；7-9月开始刷真题，重点突破算法题；10-12月模拟考试+错题复盘。\n2. 英语：每天背诵50个单词，每周完成2套阅读真题，10月开始准备作文模板。\n`;
    }

    return report;
}



// 生成AI报告的方法

// const generateAiReport = async (userData) => {
//   try {
//     // 这里调用真实的AI接口
//     const res = await generateAiReportApi({
//       userData,
//       answerId: localStorage.getItem('lastAnswerId')
//     })
//
//     if (res.code === 0) {
//       aiReportContent.value = res.data.report
//       reportGenerated.value = true
//     }
//   } catch (error) {
//     throw error
//   }
// }