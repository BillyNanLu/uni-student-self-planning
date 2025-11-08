<script setup>
const props = defineProps({
  totalProgress: { type: Number, required: true },
  currentStage: { type: String, required: true },
  stageList: { type: Array, required: true }
})

// 根据状态设置标签类型
const getStatusTagType = (status) => {
  const typeMap = { '进行中': 'primary', '未开始': 'info', '已完成': 'success' }
  return typeMap[status] || 'info'
}
</script>

<template>
  <el-card class="overview-card">
    <h3 class="card-title">规划总览</h3>
    <div class="total-progress">
      <p class="progress-label">总体完成进度</p>
      <el-progress
          :percentage="totalProgress"
          stroke-width="8"
          class="progress-bar"
      />
      <p class="progress-value">{{ totalProgress }}%</p>
    </div>
    <div class="stage-status">
      <p class="status-label">当前阶段：{{ currentStage }}</p>
      <el-descriptions column="1" border>
        <el-descriptions-item
            v-for="stage in stageList"
            :key="stage.name"
            :label="stage.name"
        >
          <div class="stage-info">
            <span class="stage-progress">{{ stage.progress }}%</span>
            <el-tag :type="getStatusTagType(stage.status)">{{ stage.status }}</el-tag>
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </el-card>
</template>

<style scoped>
.overview-card {
  grid-column: span 2; /* 跨两列 */
}
.total-progress {
  margin-bottom: 20px;
}
.progress-label {
  margin-bottom: 8px;
  color: #606266;
}
.progress-value {
  text-align: center;
  font-size: 18px;
  color: #409EFF;
  margin-top: 8px;
}
.stage-status {
  margin-top: 20px;
}
.status-label {
  margin-bottom: 12px;
  font-weight: 500;
}
.stage-info {
  display: flex;
  align-items: center;
  gap: 10px;
}
.stage-progress {
  color: #67C23A;
}
</style>