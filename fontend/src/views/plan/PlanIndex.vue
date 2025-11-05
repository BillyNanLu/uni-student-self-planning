<script setup>
import EvaluationEntry from '@/components/plan/EvaluationEntry.vue'
import PlanningRules from '@/components/plan/PlanningRules.vue'
import HotCareers from '@/components/plan/HotCareers.vue'
import ExamSchedule from '@/components/plan/ExamSchedule.vue'
</script>

<template>
  <div class="planning-page">
    <!-- 1. 测评入口模块（顶部核心引导） -->
    <EvaluationEntry />

    <!-- 2. 基础信息层 -->
    <div class="basic-info-section">
      <!-- 2.1 规划规则说明 -->
      <PlanningRules />

      <!-- 2.2 热门职业职业参考 -->
      <HotCareers />

      <!-- 2.3 考试安排 -->
      <ExamSchedule />
    </div>
  </div>
</template>

<style scoped>
.planning-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}
.basic-info-section {
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  gap: 60px; /* 模块间间距 */
}
</style>


<!--
考试安排数据后续可从数据库获取，通过 v-for 动态渲染（当前为静态示例）。
职业数据可对接后端，按热度 / 竞争比动态排序。
-->