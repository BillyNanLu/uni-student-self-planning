<script setup>

</script>

<template>
  <el-card class="exam-tracking-card">
    <h3 class="card-title">考试节点跟踪</h3>
    <div class="exam-item" v-for="exam in examList" :key="exam.id">
      <div class="exam-header">
        <h4 class="exam-name">{{ exam.name }}</h4>
        <span class="exam-time">时间：{{ exam.time }}</span>
        <span class="exam-countdown">倒计时：{{ exam.countdown }}</span>
      </div>
      <div class="exam-prepare">
        <p class="prepare-label">备考进度：{{ exam.prepareProgress }}%</p>
        <el-progress :percentage="exam.prepareProgress" stroke-width="6" />
      </div>
      <div class="exam-checklist">
        <p class="checklist-label">备考清单</p>
        <el-checkbox-group>
          <el-checkbox
              v-for="item in exam.checklist"
              :key="item.id"
              :label="item.id"
              :checked="item.completed"
              disabled
          >
            {{ item.name }}
          </el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
  </el-card>
</template>

<style scoped>

</style>