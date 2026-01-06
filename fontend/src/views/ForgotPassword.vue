<script setup lang="ts">
import { ref, watch, reactive, computed, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { sendCaptchaApi, resetPasswordApi } from "@/api/forgotpassword"

// 补充引入需要的Element Plus组件
import {
  ElButton, ElContainer, ElHeader, ElIcon,
  ElForm, ElMessage, ElCard, ElFormItem, ElInput,
  ElRow, ElCol
} from 'element-plus'
import { User, School } from '@element-plus/icons-vue'
import {send} from "vite";

const router = useRouter()
const route = useRoute() // 获取当前路由实例

// 修复：恢复表单引用并指定正确类型，移除注释
const forgetPwdFormRef = ref<InstanceType<typeof ElForm>>()

// 表单数据
const forgetPwdForm = reactive({
  username: '', // 用户名
  email: '', // 邮箱
  code: '', // 验证码
  password: '', // 新密码
  rePassword: '' // 确认新密码
})

// 验证码倒计时
const codeCountdown = ref(0)
// 修复：指定countdownTimer的类型为number | null，解决TypeScript类型提示问题
let countdownTimer: number | null = null

// 判断是否可以获取验证码（用户名和邮箱都已输入）
const canGetCode = computed(() => {
  return forgetPwdForm.username && forgetPwdForm.email
})

// 表单校验规则
const rules = reactive({
  // 用户名规则
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  // 邮箱规则
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  // 验证码规则
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 6, max: 6, message: '验证码长度为6位', trigger: 'blur' }
  ],
  // 密码规则（使用你提供的规则）
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度需在 5-16 个字符之间', trigger: 'blur' },
    { pattern: /^(?=.*[a-zA-Z])(?=.*\d)/, message: '需包含字母和数字', trigger: 'blur' }
  ],
  // 确认密码规则（使用你提供的规则）
  rePassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value !== forgetPwdForm.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})

// 获取验证码方法
const getVerificationCode = async () => {
  if (!canGetCode.value) return

  try {
    const response = await sendCaptchaApi({
      username: forgetPwdForm.username,
      email: forgetPwdForm.email
    })

    console.log(response)

    if (response.data == "非平台注册用户") {
      ElMessage.error('此用户不存在，请检查用户名和邮箱')
    } else {
      ElMessage.success('验证码已发送，请查收邮箱')
      // 启动倒计时
      codeCountdown.value = 60
      countdownTimer = window.setInterval(() => {
        codeCountdown.value--
        if (codeCountdown.value <= 0) {
          window.clearInterval(countdownTimer!)
          countdownTimer = null
        }
      }, 1000)
    }

  } catch (err: any) {
    ElMessage.error(err?.response?.data?.msg || '验证码发送失败')
  }
}

// 提交表单方法
const submitForm = () => {
  if (!forgetPwdFormRef.value) return

  forgetPwdFormRef.value.validate(async (valid) => {
    if (!valid) return

    try {
      await resetPasswordApi({
        username: forgetPwdForm.username,
        email: forgetPwdForm.email,
        code: forgetPwdForm.code,
        password: forgetPwdForm.password
      })

      ElMessage.success('密码重置成功，即将跳转登录页')

      setTimeout(() => {
        router.push('/login')
      }, 1500)

      // 重置表单
      forgetPwdFormRef.value?.resetFields()
      codeCountdown.value = 0

    } catch (err: any) {
      ElMessage.error(err?.response?.data?.msg || '验证码错误或已过期')
    }
  })
}

// 组件卸载时清除倒计时定时器
onUnmounted(() => {
  if (countdownTimer) {
    window.clearInterval(countdownTimer)
  }
})
</script>

<template>
  <ElContainer class="login-layout">
    <!-- 1. 顶部导航栏-->
    <ElHeader class="layout-header" fixed>
      <div class="header-container">
        <!-- 平台Logo与名称 -->
        <div class="logo">
          <ElIcon class="logo-icon"><School /></ElIcon>
          <span class="logo-text">大学生自我规划平台</span>
        </div>
        <!-- 导航操作：仅保留「返回登陆」按钮 -->
        <div class="nav-actions">
          <ElButton
              type="text"
              :icon="User"
              @click="router.push('/login')"
              class="back-home-btn"
          >
            返回登录
          </ElButton>
        </div>
      </div>
    </ElHeader>

    <!-- 忘记密码表单区域（优化布局和样式） -->
    <div class="forget-password-container">
      <!-- 忘记密码表单卡片（美化卡片样式） -->
      <ElCard class="forget-password-card">
        <div class="card-header">
          <h2 class="title">忘记密码</h2>
          <p class="subtitle">请填写相关信息重置你的密码</p>
        </div>
        <ElForm
            ref="forgetPwdFormRef"
            :model="forgetPwdForm"
            :rules="rules"
            label-width="90px"
            class="forget-password-form"
        >
          <!-- 用户名输入框 -->
          <ElFormItem label="用户名" prop="username">
            <ElInput
                v-model="forgetPwdForm.username"
                placeholder="请输入注册时的用户名"
                clearable
                size="medium"
            />
          </ElFormItem>

          <!-- 邮箱输入框 -->
          <ElFormItem label="邮箱" prop="email">
            <ElInput
                v-model="forgetPwdForm.email"
                type="email"
                placeholder="请输入注册时的邮箱"
                clearable
                size="medium"
            />
          </ElFormItem>

          <!-- 验证码输入框 + 获取验证码按钮 -->
          <ElFormItem label="验证码" prop="code">
            <ElRow :gutter="12">
              <ElCol :span="16">
                <ElInput
                    v-model="forgetPwdForm.code"
                    placeholder="请输入6位邮箱验证码"
                    clearable
                    size="medium"
                />
              </ElCol>
              <ElCol :span="8">
                <ElButton
                    type="primary"
                    :disabled="!canGetCode || codeCountdown > 0"
                    @click="getVerificationCode"
                    size="medium"
                    class="code-btn"
                >
                  {{ codeCountdown > 0 ? `${codeCountdown}s后重新获取` : '获取验证码' }}
                </ElButton>
              </ElCol>
            </ElRow>
          </ElFormItem>

          <!-- 新密码输入框 -->
          <ElFormItem label="新密码" prop="password">
            <ElInput
                v-model="forgetPwdForm.password"
                type="password"
                placeholder="请输入密码（5-16位，需包含字母和数字）"
                show-password
                clearable
                size="medium"
            />
          </ElFormItem>

          <!-- 确认新密码输入框 -->
          <ElFormItem label="确认密码" prop="rePassword">
            <ElInput
                v-model="forgetPwdForm.rePassword"
                type="password"
                placeholder="请再次输入新密码"
                show-password
                clearable
                size="medium"
            />
          </ElFormItem>

          <!-- 提交按钮 -->
          <ElFormItem class="submit-btn-item">
            <ElButton
                type="primary"
                @click="submitForm"
                class="submit-btn"
                size="large"
            >
              重置密码
            </ElButton>
          </ElFormItem>
        </ElForm>
      </ElCard>
    </div>
  </ElContainer>
</template>

<style scoped lang="scss">
// 1. 全局布局样式（保留原有，优化冲突）
.login-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa; // 与主页背景色一致
}

// 2. 顶部导航栏样式（完全保留原样式，不修改）
.layout-header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0;
  z-index: 100;
  height: 64px;
}

.header-container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 16px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo-icon {
  color: #409eff; // 主页主题色
  font-size: 24px;
}

.logo-text {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.back-home-btn {
  color: #409eff;
  font-size: 14px;
  &:hover {
    color: #66b1ff; // hover效果优化
    background-color: #f0f7ff;
  }
}

// 3. 忘记密码容器样式（修复高度冲突，适配头部）
.forget-password-container {
  flex: 1; // 填充剩余空间，适配头部高度
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px; // 增加上下内边距，优化移动端显示
  // 移除重复的背景色，继承全局的#f5f7fa
}

// 4. 表单卡片样式（美化，增加圆角、阴影、内边距）
.forget-password-card {
  width: 100%;
  max-width: 520px; // 稍微加宽，提升体验
  border-radius: 12px; // 圆角美化
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); // 柔和阴影
  border: none; // 移除默认边框
  overflow: hidden; // 配合圆角

  .card-header {
    text-align: center;
    padding-bottom: 16px;
    margin-bottom: 16px;
    border-bottom: 1px solid #f0f0f0; // 分隔线美化

    .title {
      font-size: 22px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
    }

    .subtitle {
      font-size: 14px;
      color: #909399;
      margin: 0;
    }
  }
}

// 5. 表单样式（优化间距）
.forget-password-form {
  padding: 0 16px; // 左右内边距，适配卡片

  :deep(.el-form-item) {
    margin-bottom: 20px; // 增加表单项间距，提升可读性
  }

  // 验证码按钮样式优化
  .code-btn {
    width: 100%; // 按钮充满列宽
    height: 40px; // 与输入框高度一致
  }
}

// 6. 提交按钮样式（美化）
.submit-btn-item {
  display: flex;
  justify-content: center;
  margin-top: 8px;

  .submit-btn {
    width: 100%;
    max-width: 240px; // 稍微加宽按钮
    border-radius: 8px; // 按钮圆角
    background-color: #409eff;
    border: none;

    &:hover {
      background-color: #66b1ff;
    }
  }
}

// 7. 适配移动端（响应式优化）
@media (max-width: 576px) {
  .forget-password-card {
    max-width: 100%;
    padding: 0 10px;
  }

  .forget-password-form {
    padding: 0 8px;
  }

  .layout-header .header-container {
    width: 100%;
  }
}
</style>