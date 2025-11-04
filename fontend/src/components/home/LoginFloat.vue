<script setup>
import { ref, onMounted } from 'vue';
const floatTimer = ref(null);

import { useRouter } from 'vue-router';
const router = useRouter();

const handleLogin = () => {
  router.push('/login');
};
const handleRegister = () => {
  router.push('/login?type=register');
};

const startFloat = () => {
  floatTimer.value = setInterval(() => {
    const el = document.querySelector('.login-float');
    if (el) {
      el.style.transform = `translateY(${Math.sin(Date.now() / 500) * 5}px)`;
    }
  }, 300);
};
const stopFloat = () => {
  clearInterval(floatTimer.value);
  const el = document.querySelector('.login-float');
  if (el) el.style.transform = 'translateY(0)';
};
// 页面加载时启动悬浮动画
onMounted(() => startFloat());
</script>

<template>
  <div class="login-float" @mouseenter="stopFloat" @mouseleave="startFloat">
    <el-button type="primary" @click="handleLogin">登录</el-button>
    <el-button type="success" @click="handleRegister">注册</el-button>
    <p class="float-tip">登录后解锁个性化规划服务</p>
  </div>
</template>

<style scoped>
.login-float {
  position: fixed;
  right: 30px;
  bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 999;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.login-float button {
  width: 120px;
  margin-bottom: 12px;
}

.float-tip {
  font-size: 12px;
  color: #909399;
}
</style>