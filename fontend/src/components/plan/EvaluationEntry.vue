<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/token'
import useUserInfoStore from '@/stores/userInfo.js'
import { User, Guide, Document } from '@element-plus/icons-vue'

const router = useRouter()
const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()

// 登录状态
const isLogin = computed(() => !!tokenStore.token)
// 测评状态（假设userInfo中有evaluated字段）
const hasEvaluated = computed(() => userInfoStore.info.evaluated === 1)

// 未登录：跳转登录页（带redirect参数，登录后回到测评页）
const handleLogin = () => {
  router.push('/login?redirect=/planning/evaluate')
}

// 登录未测评：跳转测评页
const handleGoToEvaluate = () => {
  router.push('/planning/evaluate')
}

// 登录已测评：跳转测评结果页
const handleGoToResult = () => {
  router.push('/planning/evaluate/result')
}
</script>

<template>
  <div class="evaluation-entry">
    <div class="entry-container">
      <h2 class="entry-title">发展方向测评</h2>
      <p class="entry-desc">完成测评，获取专属考研/考公/就业规划建议</p>

      <!-- 按钮区域 -->
      <div class="entry-buttons">
        <el-button
            v-if="!isLogin"
            type="primary"
            size="large"
            @click="handleLogin"
        >
          <User /> 登录后测评
        </el-button>

        <template v-else>
          <!-- 登录未测评：显示按钮+小型提示框 -->
          <el-tooltip
              v-if="!hasEvaluated"
              content="完成测评可生成个性化规划"
              placement="bottom"
              effect="light"
          >
            <el-button
                type="primary"
                size="large"
                @click="handleGoToEvaluate"
            >
              <Guide /> 立刻做测评
            </el-button>
          </el-tooltip>

          <!-- 登录已测评：显示查看结果按钮 -->
          <el-button
              v-else
              type="success"
              size="large"
              @click="handleGoToResult"
          >
            <Document /> 查看/更新测评结果
          </el-button>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>
.evaluation-entry {
  background: linear-gradient(135deg, #f0f9ff 0%, #e6f7ff 100%);
  border-radius: 16px;
  padding: 40px 20px;
  text-align: center;
}
.entry-title {
  font-size: 28px;
  margin-bottom: 16px;
  color: #2c3e50;
}
.entry-desc {
  font-size: 16px;
  color: #606266;
  margin-bottom: 24px;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}
.entry-buttons {
  display: flex;
  justify-content: center;
}
.el-button {
  padding: 12px 24px;
  font-size: 16px;
}
</style>