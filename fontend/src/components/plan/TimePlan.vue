<script setup>
const props = defineProps({
  stage: { type: String, required: true }, // 基础阶段（3-6月）
  dailyPlan: { type: String, required: true } // 3h英语（单词+阅读）+4h专业课（数据结构）
})
</script>

<template>
  <el-card class="time-card">
    <h3 class="card-title">阶段性时间安排建议</h3>
    <div class="time-content">
      <p class="stage-info">当前阶段：{{ stage }}</p>
      <div class="daily-plan">
        <h4>每日学习建议：</h4>
        <p>{{ dailyPlan }}</p>
        <el-divider></el-divider>
        <p class="tip">提示：可在「规划进度中心」调整每日计划并跟踪完成情况</p>
      </div>
    </div>
  </el-card>
</template>

<style scoped>

</style>