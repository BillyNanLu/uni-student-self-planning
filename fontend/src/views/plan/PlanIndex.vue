<script setup>
import EvaluationEntry from '@/components/plan/EvaluationEntry.vue'
import PlanningRules from '@/components/plan/PlanningRules.vue'
</script>

<template>
  <div class="planning-page">
    <!-- 1. 测评入口模块（顶部核心引导） -->
    <EvaluationEntry />

    <!-- 2. 基础信息层 -->
    <div class="basic-info-section">
      <PlanningRules />
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