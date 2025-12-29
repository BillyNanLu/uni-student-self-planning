<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ArrowUp } from '@element-plus/icons-vue'

import ExamSchedule from '@/components/resources/ExamSchedule.vue'
import HotCareers from '@/components/resources/HotCareers.vue'
import FurtherStudyEmploymentResource from '@/components/resources/FurtherStudyEmploymentResource.vue'

// 控制回到顶部按钮的显示状态
const showBackToTop = ref(false)

// 监听滚动事件
const handleScroll = () => {
  // 当滚动距离超过300px时显示按钮
  showBackToTop.value = window.scrollY > 300
}

// 回到顶部函数
const backToTop = () => {
  // 使用平滑滚动效果
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}

// 组件挂载时添加滚动监听
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

// 组件卸载时移除监听，防止内存泄漏
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <FurtherStudyEmploymentResource />
  <HotCareers />
  <div class="module-divider"></div>
  <ExamSchedule />

  <!-- 回到顶部按钮 -->
  <div
      class="back-to-top"
      @click="backToTop"
      v-show="showBackToTop"
  >
    <el-icon><ArrowUp /></el-icon>
  </div>
</template>

<style scoped>
/* 模块分隔线 */
.module-divider {
  height: 1px;
  background: linear-gradient(90deg, transparent, #e5e7eb, transparent);
  margin: 40px 0; /* 上下留白，区分模块 */
}

.back-to-top {
  position: fixed;
  right: 30px;
  bottom: 30px;
  width: 40px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  background-color: #409eff;
  color: white;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  z-index: 999;
}

.back-to-top:hover {
  background-color: #66b1ff;
}
</style>