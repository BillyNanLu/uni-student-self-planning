<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import DirectionMatch from '@/components/plan/DirectionMatch.vue'
import UserProfileTags from "@/components/plan/UserProfileTags.vue";
import AiReport from '@/components/plan/AiReport.vue'
import TimePlan from '@/components/plan/TimePlan.vue'
import ExamPrediction from '@/components/plan/ExamPrediction.vue'

// 导入AI生成接口
// import { generateAiReport } from '@/api/evaluate'

const router = useRouter()
const loading = ref(true)
// 初始化时就定义结构
const result = ref({
  directionResult: {
    matchRate: {},
    recommend: '',
    preferredDirection: "",
    confirmStatus: 0,
    finalDirection: ""
  },
  userProfile: {
    interests: [],
    abilities: [],
    selfEvaluation: []
  },
  aiReport: '',
  timeRule: {
    stage: '',
    dailyPlan: ''
  },
  examList: []
})

// AI报告状态管理
const reportGenerated = ref(false)
const aiReportContent = ref('')


const generateAiReport = async (userData) => {
  // 模拟API调用延迟
  return new Promise((resolve) => {
    setTimeout(() => {
      // 假的AI生成报告内容
      aiReportContent.value = `# 个性化学习规划报告

## 一、整体规划建议
基于你的兴趣（${userData.userProfile.interests.join('、')}）和能力（${userData.userProfile.abilities.join('、')}）特点，结合考研方向的需求，制定以下规划：

## 二、阶段目标
### 基础阶段（3-6月）
- 英语：每天背诵50个单词，完成考研词汇第一轮复习
- 数学：完成高数基础知识学习，配合习题巩固
- 专业课：通读教材，建立知识框架

### 强化阶段（7-9月）
- 英语：开始真题训练，重点突破阅读和写作
- 数学：强化题型训练，总结解题方法
- 专业课：深入学习重点章节，开始笔记整理

## 三、学习建议
1. 利用你的${userData.userProfile.selfEvaluation[0]}特点，合理安排高强度学习时段
2. 发挥${userData.userProfile.selfEvaluation[1]}的优势，确保计划执行到位
3. 结合编程开发兴趣，可以尝试用编程解决数学问题，提升学习效率

## 四、注意事项
- 保持规律作息，避免过度疲劳
- 定期回顾总结，及时调整学习计划
- 关注目标院校的招生政策变化`

      reportGenerated.value = true
      resolve()
    }, 1500) // 模拟1.5秒的AI生成时间
  })
}

// 检查方向是否一致的方法
const checkDirectionMatch = () => {
  const { recommend, preferredDirection } = result.value.directionResult
  if (recommend === preferredDirection) {
    result.value.directionResult.finalDirection = recommend
  }
}

// 监听方向变化
watch([
  () => result.value.directionResult.recommend,
  () => result.value.directionResult.preferredDirection
], () => {
  checkDirectionMatch()
}, { immediate: true })

// 页面加载时初始化数据
onMounted(async () => {
  try {
    // 临时模拟数据
    const mockData = {
      directionResult: {
        matchRate: {考研:80, 考公:50, 就业:30},
        recommend: '考研', // 假设AI推荐考研
        preferredDirection: "就业", // 假设用户偏好就业
        confirmStatus: 0,
        finalDirection: ""
      },
      userProfile: {
        interests: ['编程开发', '算法研究', '技术学习', '开源社区'],
        abilities: ['逻辑思维', '问题解决', '自主学习', '团队协作'],
        selfEvaluation: ['抗压能力强', '执行力高', '追求卓越', '时间管理']
      },
      timeRule: {
        stage: '基础阶段（3-6月）',
        dailyPlan: '3h英语（单词+阅读）+4h专业课（数据结构）'
      },
      examList: [{
        id:1,
        name:'考研初试',
        time:'2025年12月底',
        note:'重点复习数学和英语',
        countdown: '180天'
      }],
      aiReport: ''
    }

    result.value = mockData
    checkDirectionMatch()
    loading.value = false

  } catch (error) {
    ElMessage.error('数据加载失败，请重试')
    loading.value = false
  }
})

// 处理子组件的确认事件
const handleConfirmDirection = (data) => {
  result.value.directionResult.confirmStatus = data.status

  if (data.status === 3) {
    result.value.directionResult.finalDirection = ""
  } else {
    result.value.directionResult.finalDirection = data.finalDirection
  }

  console.log("用户确认方向：", data)
}

// 跳转规划进度中心（/planning/progress）
const goToProgress = () => {
  router.push('/planning/progress')
}

// 重新测评（返回测评页）
const reEvaluate = () => {
  router.push('/planning/evaluate')
}
</script>

<template>
  <div class="result-page">
    <!-- 加载状态 -->
    <el-loading v-if="loading" text="正在加载数据...">
      <div class="loading-container"></div>
    </el-loading>

    <!-- 生成结果 -->
    <div v-else class="result-container">
      <!-- 1. 方向匹配度展示 -->
      <DirectionMatch
          :matchRate="result.directionResult.matchRate"
          :recommend="result.directionResult.recommend"
          :preferredDirection="result.directionResult.preferredDirection"
          :confirmStatus="result.directionResult.confirmStatus"
          :finalDirection="result.directionResult.finalDirection"
          @confirmDirection="handleConfirmDirection"
      />

      <!-- 2. 用户画像标签 -->
      <UserProfileTags :tagsData="result.userProfile" />

      <!-- 3. AI规划报告 -->
      <AiReport
          :user-data="result"
          :is-generated="reportGenerated"
          :report-content="aiReportContent"
          @generate-report="generateAiReport"
      />

      <!-- 4. 考试日期预测 -->
      <ExamPrediction :examList="result.examList" />

    </div>
  </div>
</template>

<style scoped>
.result-container {
  display: flex;
  flex-direction: column;
  gap: 20px; /* 模块间间距 */
  padding: 0 20px 20px;
}
</style>