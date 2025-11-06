<script setup>
import { Download } from '@element-plus/icons-vue'
const props = defineProps({
  report: { type: String, required: true } // AI生成的文本报告
})
</script>

<template>
  <el-card class="report-card">
    <h3 class="card-title">AI个性化规划报告</h3>
    <div class="report-content">
      <!-- 将换行符\n转换为<br> -->
      <p v-html="report.replace(/\n/g, '<br>')"></p>
    </div>
    <el-button type="text" class="download-btn">
      <Download /> 下载报告（PDF格式）
    </el-button>
  </el-card>
</template>

<style scoped>

</style>