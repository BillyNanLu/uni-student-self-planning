<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { ArrowLeft, HomeFilled, Refresh } from '@element-plus/icons-vue'

// 动态背景效果（可选）
const dots = ref([])

onMounted(() => {
  // 生成随机浮动点
  for (let i = 0; i < 30; i++) {
    dots.value.push({
      id: i,
      left: `${Math.random() * 100}%`,
      top: `${Math.random() * 100}%`,
      size: Math.random() * 8 + 2,
      delay: Math.random() * 10,
      duration: Math.random() * 20 + 10
    })
  }
})

// 刷新页面
const refreshPage = () => {
  window.location.reload()
}
</script>

<template>
  <div class="not-found-container">
    <!-- 背景装饰点 -->
    <div
        v-for="dot in dots"
        :key="dot.id"
        class="floating-dot"
        :style="{
        left: dot.left,
        top: dot.top,
        width: `${dot.size}px`,
        height: `${dot.size}px`,
        animationDelay: `${dot.delay}s`,
        animationDuration: `${dot.duration}s`
      }"
    ></div>

    <!-- 404 内容区 -->
    <div class="not-found-content">
      <div class="error-code">404</div>
      <div class="error-message">哎呀，页面走丢了...</div>
      <div class="error-desc">
        你访问的页面不存在或已被删除，请检查网址是否正确
      </div>

      <!-- 操作按钮 -->
      <div class="action-buttons">
        <RouterLink to="/" class="btn primary-btn">
          <HomeFilled class="icon" /> 返回首页
        </RouterLink>
        <button @click="refreshPage" class="btn secondary-btn">
          <Refresh class="icon" /> 刷新页面
        </button>
        <RouterLink to="/resources" class="btn text-btn">
          <ArrowLeft class="icon" /> 查看资源
        </RouterLink>
      </div>
    </div>

    <!-- 页脚 -->
    <div class="not-found-footer">
      © 2025 大学生自我规划平台 | 如有问题请联系客服
    </div>
  </div>
</template>

<style scoped>
.not-found-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  position: relative;
  overflow: hidden;
  padding: 20px;
}

/* 浮动装饰点 */
.floating-dot {
  position: absolute;
  background-color: rgba(64, 158, 255, 0.1);
  border-radius: 50%;
  animation: float linear infinite;
}

@keyframes float {
  0% {
    transform: translateY(0) translateX(0);
  }
  50% {
    transform: translateY(-50px) translateX(30px);
  }
  100% {
    transform: translateY(0) translateX(0);
  }
}

.not-found-content {
  text-align: center;
  z-index: 1;
  max-width: 600px;
}

.error-code {
  font-size: 12rem;
  font-weight: 700;
  background: linear-gradient(135deg, #409eff, #67c23a);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  line-height: 1;
  margin-bottom: 20px;
}

.error-message {
  font-size: 2rem;
  color: #303133;
  margin-bottom: 16px;
  font-weight: 600;
}

.error-desc {
  font-size: 1rem;
  color: #606266;
  margin-bottom: 40px;
  line-height: 1.6;
}

/* 按钮样式 */
.action-buttons {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 60px;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
}

.icon {
  margin-right: 8px;
  font-size: 1em;
}

.primary-btn {
  background-color: #409eff;
  color: white;
  border: none;
}

.primary-btn:hover {
  background-color: #66b1ff;
}

.secondary-btn {
  background-color: white;
  color: #409eff;
  border: 1px solid #409eff;
}

.secondary-btn:hover {
  background-color: #f0f7ff;
}

.text-btn {
  background-color: transparent;
  color: #606266;
  border: none;
}

.text-btn:hover {
  color: #409eff;
}

.not-found-footer {
  color: #909399;
  font-size: 0.9rem;
  position: absolute;
  bottom: 30px;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .error-code {
    font-size: 8rem;
  }

  .error-message {
    font-size: 1.5rem;
  }

  .action-buttons {
    flex-direction: column;
    width: 100%;
  }

  .btn {
    width: 100%;
  }
}
</style>