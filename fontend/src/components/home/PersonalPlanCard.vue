<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import useUserInfoStore from '@/stores/userInfo';
import { Document } from '@element-plus/icons-vue';

const router = useRouter();

const planInfo = ref({
  direction: '未知（请先完成测评）',
  schedule: '暂无',
  path: '暂无'
});

onMounted(() => {

});

const viewReport = () => {
  router.push('/planning/evaluate/result');
};
</script>

<template>
  <div class="personal-plan-card">
    <el-card class="plan-card">
      <div class="card-header">
        <el-icon class="card-icon"><Document /></el-icon>
        <h3 class="card-title">你的专属发展规划</h3>
      </div>
      <div class="card-content">
        <p class="plan-direction">推荐方向：{{ planInfo.direction }}</p>
        <el-button type="primary" @click="viewReport">查看完整AI报告</el-button>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.personal-plan-card {
  margin: 24px 0;
}
.plan-card {
  border: none;
}
.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}
.card-icon {
  font-size: 24px;
  color: #409EFF;
  margin-right: 12px;
}
.card-title {
  font-size: 20px;
  color: #2c3e50;
}
.card-content {
  font-size: 16px;
}
.plan-direction {
  color: #409EFF;
  margin-bottom: 8px;
  font-weight: 500;
}
.plan-schedule, .plan-path {
  margin-bottom: 8px;
}
</style>