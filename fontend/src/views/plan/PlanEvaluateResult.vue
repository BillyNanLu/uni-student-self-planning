<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import DirectionMatch from '@/components/plan/DirectionMatch.vue'
import TimePlan from '@/components/plan/TimePlan.vue'
import ExamPrediction from '@/components/plan/ExamPrediction.vue'
import AiReport from '@/components/plan/AiReport.vue'

// 导入AI生成接口
import { generateAiReport } from '@/api/evaluate'

const router = useRouter()
const loading = ref(true)
const result = ref({}) // 存储AI生成的完整结果


// 页面加载时调用AI生成接口
// onMounted(async () => {
//   try {
//     // 传入用户答题ID（后端通过ID获取答题数据+匹配规则）
//     const res = await generateAiReport({ answerId: localStorage.getItem('lastAnswerId') })
//     if (res.code === 0) {
//       result.value = res.data // 接收后端返回的AI生成结果
//       loading.value = false
//     }
//   } catch (error) {
//     ElMessage.error('规划生成失败，请重试')
//     loading.value = false
//   }
// })

// 在onMounted中临时替换为模拟数据
onMounted(async () => {
  // 临时模拟数据
  result.value = {
    directionResult: {
      matchRate: {考研:80, 考公:50, 就业:30},
      recommend: '考研'
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
    aiReport: '这是一份模拟的AI规划报告...\n包含换行内容'
  }
  loading.value = false
})

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
    <el-loading v-if="loading" text="正在生成个性化规划建议...">
      <div class="loading-container"></div>
    </el-loading>

    <!-- 生成结果 -->
    <div v-else class="result-container">
      <!-- 1. 方向匹配度展示 -->
      <DirectionMatch :matchRate="result.directionResult.matchRate" :recommend="result.directionResult.recommend" />

      <!-- 2. 时间安排建议 -->
      <TimePlan :stage="result.timeRule.stage" :dailyPlan="result.timeRule.dailyPlan" />

      <!-- 3. 考试日期预测 -->
      <ExamPrediction :examList="result.examList" />

      <!-- 4. AI规划报告（文本化） -->
      <AiReport :report="result.aiReport" />

      <!-- 5. 行动按钮 -->
      <div class="action-buttons">
        <el-button type="primary" @click="goToProgress">查看我的规划进度</el-button>
        <el-button @click="reEvaluate">重新测评</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>