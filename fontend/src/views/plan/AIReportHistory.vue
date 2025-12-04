<script setup>
import { ref, onMounted, computed } from 'vue'
import useUserInfoStore from '@/stores/userInfo'
import { ElMessage } from 'element-plus'

// 用户信息（从Pinia获取）
const userInfoStore = useUserInfoStore()
const userName = computed(() => userInfoStore.info.username || userInfoStore.info.name)
const userAvatar = computed(() => userInfoStore.info.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png') // 默认头像
const currentMajor = computed(() => userInfoStore.info.major || '专业') // 当前方向

// 页面加载时获取进度数据
onMounted(async () => {
  try {

  } catch (error) {
    ElMessage.error('AI规划报告获取失败，请重试')
  }
})


</script>

<template>
  <div class="progress-page">
    <!-- 顶部导航与用户信息 -->
    <div class="page-header">
      <h2 class="page-title">我的AI规划报告</h2>
      <div class="user-info">
        <el-avatar :src="userAvatar" class="user-avatar" />
        <span class="user-name">{{ userName }}</span>
        <el-tag type="info" class="major-tag">专业：{{ currentMajor }}</el-tag>
      </div>
    </div>


  </div>
</template>

<style scoped>
.progress-page {
  padding: 20px;
  margin: 0 auto;
  background-color: #f9fafc;
  min-height: calc(100vh - 60px);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.page-title {
  font-size: 24px;
  color: #2c3e50;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 40px;
  height: 40px;
}

.user-name {
  font-size: 16px;
  color: #2c3e50;
}

.major-tag {
  font-size: 12px;
}

.progress-content {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 两列布局 */
  gap: 20px;
}

/* 响应式适配：小屏幕时改为单列 */
@media (max-width: 768px) {
  .progress-content {
    grid-template-columns: 1fr;
  }
}
</style>