<script setup>
import { ref, computed } from 'vue';
import useUserInfoStore from '@/stores/userInfo';
import { useTokenStore } from '@/stores/token';
import { useRouter } from 'vue-router';

const router = useRouter();
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();

// 计算登录状态
const isLogin = computed(() => !!tokenStore.token);

const bannerList = [
  {
    id: 1,
    title: '一站式大学生发展规划平台',
    desc: '考研、考公、就业，你的每一条路都有专属规划',
  },
  {
    id: 2,
    title: 'AI生成个性化发展报告',
    desc: '基于你的兴趣与能力，生成可落地的时间安排与学习路径',
  },
  {
    id: 3,
    title: '海量资源库一键查询',
    desc: '考研真题、考公资料、就业面经，你需要的都在这里',
  },
];

// 根据ID获取按钮文本
const getButtonText = (id) => {
  if (!isLogin.value)
    return id === 2 ? '立即登录体验' : '立刻体验';
  else
    return '立刻体验';
};

const handleClick = (id) => {
  switch(id) {
    case 1:
      router.push('/planning');
      break;
    case 2:
      if (!isLogin.value)
        router.push('/login');
      else
        router.push('/ai-chat');
      break;
    case 3:
      router.push('/resources');
      break;
    default:
      router.push('/');
  }
};
</script>

<template>
  <el-carousel :interval="5000" type="fade" height="320px" indicator-position="outside">
    <el-carousel-item v-for="item in bannerList" :key="item.id">
      <div class="banner-item">
        <div class="banner-content">
          <h2 class="banner-title">{{ item.title }}</h2>
          <p class="banner-desc">{{ item.desc }}</p>
          <el-button type="primary" size="large" @click="handleClick(item.id)">{{ getButtonText(item.id) }}</el-button>
        </div>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<style scoped>
.banner-item {
  background-size: cover;
  background-position: center;
  /* 为每个 Banner 项设置不同背景图，增强差异化 */
  &:nth-child(1) { background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)), url("@/assets/login_bg.jpg"); }
  &:nth-child(2) { background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)), url("@/assets/login_bg.jpg"); }
  &:nth-child(3) { background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)), url("@/assets/login_bg.jpg"); }
}

.banner-content {
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
  padding: 60px 20px;
}

.banner-title {
  font-size: 32px;
  margin-bottom: 20px;
  color: #2c3e50;
}

.banner-desc {
  font-size: 18px;
  margin-bottom: 30px;
  color: #606266;
}
</style>