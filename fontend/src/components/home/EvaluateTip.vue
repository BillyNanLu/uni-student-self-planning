<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import useUserInfoStore from '@/stores/userInfo';

const router = useRouter();
const userInfoStore = useUserInfoStore();
const showEvaluateTip = ref(false);

// 页面加载时判断是否已完成测评
onMounted(() => {
  // 假设userInfoStore中有evaluateStatus字段标记测评状态（0=未完成，1=已完成）
  // if (userInfoStore.info.evaluateStatus === 0) {
  //   showEvaluateTip.value = true;
  // }

  // 模拟测评未完成
  if (1) {
    showEvaluateTip.value = true;
  }
});

const handleClose = () => {
  showEvaluateTip.value = false;
  // 可将“稍后再说”的选择存储到本地，避免重复弹窗
  localStorage.setItem('evaluateTipClosed', 'true');
};

const goToEvaluate = () => {
  router.push('/planning/evaluate');
  showEvaluateTip.value = false;
};
</script>

<template>
  <el-dialog
      v-model="showEvaluateTip"
      title="完成测评，开启你的专属规划"
      width="500px"
      :before-close="handleClose"
  >
    <div class="evaluate-tip-content">
      <p>你还未完成发展方向测评，完成后可获取：</p>
      <ul>
        <li>✅ 个性化时间安排建议（如考研复习周期、考公备考节点）</li>
        <li>✅ 适配你能力的学习路径（如“基础-强化-冲刺”阶段划分）</li>
        <li>✅ AI生成的专属发展报告</li>
      </ul>
      <div class="dialog-buttons">
        <el-button @click="handleClose">稍后再说</el-button>
        <el-button type="primary" @click="goToEvaluate">立即测评</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<style scoped>
.evaluate-tip-content {
  font-size: 16px;
}
.evaluate-tip-content ul {
  margin: 16px 0;
  padding-left: 20px;
}
.evaluate-tip-content li {
  margin-bottom: 8px;
}
.dialog-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
}
</style>