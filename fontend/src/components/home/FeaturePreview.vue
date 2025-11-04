<script setup>
import { ref, computed } from 'vue';
import useUserInfoStore from '@/stores/userInfo';
import { useTokenStore } from '@/stores/token';
import { useRouter } from 'vue-router';
import { Guide, ChatDotRound, Collection } from '@element-plus/icons-vue';

const router = useRouter();
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();

// 计算登录状态
const isLogin = computed(() => !!tokenStore.token);

const featureList = [
  {
    id: 1,
    title: '发展方向测评',
    desc: '完成目标问卷与自评，明确你的考研、考公或就业适配度',
    icon: Guide,
  },
  {
    id: 2,
    title: 'AI规划对话',
    desc: '与AI实时交流，获取个性化时间安排、学习路径建议',
    icon: ChatDotRound,
  },
  {
    id: 3,
    title: '资源库查询',
    desc: '考研真题、考公资料、就业面经，分类检索一键获取',
    icon: Collection,
  },
];

// 根据ID获取按钮文本
const getButtonText = (id) => {
  if (!isLogin.value)
    return id === 2 ? '登录查看详情' : '查看详情';
  else
    return '查看详情';
};

// 根据ID处理不同的跳转
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
  <div class="feature-section">
    <h3 class="section-title">核心功能预览</h3>
    <el-row :gutter="30" class="feature-cards">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in featureList" :key="item.id">
        <el-card class="feature-card">
          <div class="feature-icon">
            <el-icon :size="48"><component :is="item.icon" /></el-icon>
          </div>
          <h4 class="card-title">{{ item.title }}</h4>
          <p class="card-desc">{{ item.desc }}</p>
          <el-button type="primary" @click="handleClick(item.id)">{{ getButtonText(item.id) }}</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.feature-section {
  padding: 40px 0;
  background-color: #fff;
}

.section-title {
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
  color: #2c3e50;
  position: relative;
  &::after {
    content: "";
    position: absolute;
    bottom: -10px;
    left: 50%;
    transform: translateX(-50%);
    width: 60px;
    height: 3px;
    background-color: var(--primary-color);
  }
}

.feature-card {
  border: none;
  text-align: center;
  overflow: hidden;
}

.feature-icon {
  margin: 20px 0;
  color: var(--primary-color);
}

.card-title {
  font-size: 20px;
  margin-bottom: 15px;
  color: #2c3e50;
}

.card-desc {
  color: #606266;
  margin-bottom: 20px;
}
</style>