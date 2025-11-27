<script setup>
import { ref } from 'vue'
import { Download } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  // 用于AI生成的用户数据
  userData: {
    type: Object,
    required: true
  },
  // 是否已经生成报告（用于状态恢复）
  isGenerated: {
    type: Boolean,
    default: false
  },
  // 已生成的报告内容
  reportContent: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['generateReport', 'reportGenerated'])

// 本地状态
const loading = ref(false)
const report = ref(props.reportContent)
const showReport = ref(props.isGenerated)

// 触发AI生成报告
const handleGenerateReport = async () => {
  loading.value = true
  try {
    // 通知父组件开始生成报告
    await emit('generateReport', props.userData)
    // 父组件生成完成后会通过props更新reportContent
    showReport.value = true
    ElMessage.success('AI规划报告生成成功！')
  } catch (error) {
    ElMessage.error('报告生成失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <el-card class="report-card">
    <h3 class="card-title">AI个性化规划报告</h3>

    <!-- 未生成状态：显示生成按钮 -->
    <div class="generate-section" v-if="!showReport">
      <p class="generate-desc">
        基于你的兴趣、能力和发展方向，AI将为你生成个性化规划建议
      </p>
      <el-button
          type="primary"
          size="large"
          @click="handleGenerateReport"
          :loading="loading"
          class="generate-btn"
      >
        生成AI个性化规划报告
      </el-button>
    </div>

    <!-- 已生成状态：显示报告内容 -->
    <div v-else class="report-content">
      <p v-html="report.replace(/\n/g, '<br>')"></p>
      <el-button type="text" class="download-btn">
        <Download /> 下载报告（PDF格式）
      </el-button>
    </div>
  </el-card>
</template>

<style scoped>
.card-title {
  font-size: 18px;
  color: #303133;
  margin: 0 0 16px 0;
  font-weight: 600;
  padding: 20px 20px 0;
}

.generate-section {
  padding: 20px;
  text-align: center;
}

.generate-desc {
  color: #606266;
  margin-bottom: 20px;
  line-height: 1.6;
}

.generate-btn {
  padding: 12px 30px;
  font-size: 16px;
}

.report-content {
  padding: 0 20px 20px;
  line-height: 1.8;
  color: #303133;
}

.download-btn {
  margin-top: 20px;
  color: #409EFF;
}
</style>