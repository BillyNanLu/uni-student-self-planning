<script setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
// 引入Element组件与图标
import {
  ElHeader, ElContainer, ElForm, ElFormItem, ElInput,
  ElButton, ElCheckbox, ElLink, ElMessage, ElIcon
} from 'element-plus'
import {
  School, HomeFilled, User, Lock, Phone, Message as MessageIcon
} from '@element-plus/icons-vue'
// 引入接口服务
import { userRegisterService, userLoginService } from '@/api/user.js'

const router = useRouter()
const route = useRoute()  // 获取当前路由实例

// 1. 核心状态与参数（覆盖登录/注册全场景）
const isRegister = ref(route.query.type === 'register') // 切换登录/注册表单
const formRef = ref(null)     // 表单引用
const rememberMe = ref(false) // 记住登录状态

// 监听路由参数变化（当手动修改URL或通过路由跳转时同步状态）
watch(
    () => route.query.type,  // 监听路由中type参数的变化
    (newType) => {
      isRegister.value = newType === 'register'
      // 路由变化时重置表单
      formRef.value?.resetFields()
      rememberMe.value = false
    }
)

// 表单数据（统一管理登录/注册字段）
const formData = ref({
  username: '',    // 用户名（登录/注册通用）
  password: '',    // 密码（登录/注册通用）
  rePassword: '',  // 确认密码（仅注册）
  name: '',        // 昵称（仅注册）
  phone: '',       // 手机号（仅注册）
  email: ''        // 邮箱（仅注册）
})

// 新增：路由跳转控制函数（用于按钮点击切换）
const goToLogin = () => {
  router.push({
    path: '/login',
    query: {}  // 清空type参数，显示登录表单
  })
}

const goToRegister = () => {
  router.push({
    path: '/login',
    query: { type: 'register' }  // 添加type=register参数，显示注册表单
  })
}

// 2. 表单校验规则（严格匹配字段要求）
const formRules = ref({
  // 用户名规则（登录/注册通用）
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 5, max: 16, message: '用户名长度需在 5-16 个字符之间', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '仅支持字母、数字和下划线', trigger: 'blur' }
  ],
  // 密码规则（登录/注册通用）
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度需在 5-16 个字符之间', trigger: 'blur' },
    { pattern: /^(?=.*[a-zA-Z])(?=.*\d)/, message: '需包含字母和数字', trigger: 'blur' }
  ],
  // 确认密码规则（仅注册）
  rePassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  // 昵称规则（仅注册，选填）
  name: [
    { min: 2, max: 12, message: '昵称长度需在 2-12 个字符之间', trigger: 'blur' }
  ],
  // 手机号规则（仅注册）
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的11位手机号', trigger: 'blur' }
  ],
  // 邮箱规则（仅注册）
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱（如：student@xxx.com）', trigger: 'blur' }
  ]
})

// 3. 核心交互函数
// 表单提交（登录/注册统一处理）
const handleSubmit = async () => {
  try {
    // 表单校验
    await formRef.value.validate()
    // 区分登录/注册接口
    if (isRegister.value) {
      await handleRegister()
    } else {
      await handleLogin()
    }
  } catch (error) {
    // 校验失败不做额外处理（Element会自动提示）
    return
  }
}

// 登录逻辑
const handleLogin = async () => {
  try {
    const res = await userLoginService({
      username: formData.value.username,
      password: formData.value.password,
      rememberMe: rememberMe.value
    })
    if (res.code === 0) {
      ElMessage.success('登录成功！即将跳转至主页～')
      // 登录成功后跳转主页（可根据需求添加Token存储）
      setTimeout(() => router.push('/'), 1500)
    } else {
      ElMessage.error(res.message || '登录失败，请重试')
    }
  } catch (error) {
    ElMessage.error('网络异常，请检查网络连接')
  }
}

// 注册逻辑
const handleRegister = async () => {
  try {
    const res = await userRegisterService({
      username: formData.value.username,
      password: formData.value.password,
      name: formData.value.name,
      phone: formData.value.phone,
      email: formData.value.email
    })
    if (res.code === 0) {
      ElMessage.success('注册成功！请登录～')
      // 注册成功后切换到登录表单并清空数据
      isRegister.value = false
      resetForm()
    } else {
      ElMessage.error(res.message || '注册失败，请重试')
    }
  } catch (error) {
    ElMessage.error('网络异常，请检查网络连接')
  }
}

// 切换登录/注册表单
const toggleForm = () => {
  isRegister.value = !isRegister.value
  resetForm() // 切换时清空表单
}

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields()
  rememberMe.value = false
}
</script>

<template>
  <ElContainer class="login-layout">
    <!-- 1. 顶部导航栏（与主页Layout完全一致） -->
    <ElHeader class="layout-header" fixed>
      <div class="header-container">
        <!-- 平台Logo与名称 -->
        <div class="logo">
          <ElIcon class="logo-icon"><School /></ElIcon>
          <span class="logo-text">大学生自我规划平台</span>
        </div>
        <!-- 导航操作：仅保留「返回主页」按钮 -->
        <div class="nav-actions">
          <ElButton
              type="text"
              :icon="HomeFilled"
              @click="router.push('/')"
              class="back-home-btn"
          >
            返回主页
          </ElButton>
        </div>
      </div>
    </ElHeader>

    <!-- 2. 中间表单区（居中布局） -->
    <div class="login-container">
      <div class="login-card">
        <!-- 表单标题 -->
        <div class="form-header">
          <h2 class="title">{{ isRegister ? '新用户注册' : '账号登录' }}</h2>
          <p class="desc">
            {{ isRegister
              ? '注册后可享受个性化规划、AI咨询等专属服务'
              : '欢迎回来，继续你的规划之旅'
            }}
          </p>
        </div>

        <!-- 核心表单 -->
        <ElForm
            ref="formRef"
            :model="formData"
            :rules="formRules"
            class="login-form"
            label-position="top"
            size="large"
        >
          <!-- 用户名（登录/注册通用） -->
          <ElFormItem label="用户名（用于注册，暂不支持修改）" prop="username">
            <ElInput
                v-model="formData.username"
                :prefix-icon="User"
                placeholder="请输入用户名（5-16位，支持字母/数字/下划线）"
                clearable
            />
          </ElFormItem>

          <!-- 密码（登录/注册通用） -->
          <ElFormItem label="密码" prop="password">
            <ElInput
                v-model="formData.password"
                :prefix-icon="Lock"
                type="password"
                placeholder="请输入密码（5-16位，需包含字母和数字）"
                clearable
            />
          </ElFormItem>

          <!-- 注册专属字段（条件渲染） -->
          <template v-if="isRegister">
            <ElFormItem label="确认密码" prop="rePassword">
              <ElInput
                  v-model="formData.rePassword"
                  :prefix-icon="Lock"
                  type="password"
                  placeholder="请再次输入密码"
                  clearable
              />
            </ElFormItem>

            <ElFormItem label="昵称（选填）" prop="name">
              <ElInput
                  v-model="formData.name"
                  :prefix-icon="User"
                  placeholder="请输入昵称（2-12位，将显示在个人中心）"
                  clearable
              />
            </ElFormItem>

            <ElFormItem label="手机号" prop="phone">
              <ElInput
                  v-model="formData.phone"
                  :prefix-icon="Phone"
                  placeholder="请输入有效的11位手机号"
                  clearable
                  maxlength="11"
              />
            </ElFormItem>

            <ElFormItem label="邮箱" prop="email">
              <ElInput
                  v-model="formData.email"
                  :prefix-icon="MessageIcon"
                  placeholder="请输入常用邮箱（用于账号找回）"
                  clearable
              />
            </ElFormItem>
          </template>

          <!-- 登录专属字段（条件渲染） -->
          <template v-else>
            <ElFormItem class="remember-item">
              <ElCheckbox v-model="rememberMe">记住登录状态（7天内免登录）</ElCheckbox>
              <ElLink type="primary" :underline="false" class="forgot-link">
                忘记密码？
              </ElLink>
            </ElFormItem>
          </template>

          <!-- 提交按钮 -->
          <ElFormItem class="submit-item">
            <ElButton
                type="primary"
                class="submit-btn"
                @click="handleSubmit"
                :loading="false"
            >
              {{ isRegister ? '注册并加入平台' : '登录平台' }}
            </ElButton>
          </ElFormItem>

          <!-- 切换表单链接 -->
          <ElFormItem class="switch-item">
            <ElLink
                type="info"
                :underline="false"
                @click="isRegister ? goToLogin() : goToRegister()"
                class="switch-link"
            >
              {{ isRegister
                ? '已有账号？点击登录'
                : '还没有账号？立即注册'
              }}
            </ElLink>
          </ElFormItem>
        </ElForm>
      </div>
    </div>
  </ElContainer>
</template>

<style scoped lang="scss">
// 1. 全局布局样式
.login-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa; // 与主页背景色一致
}

// 2. 顶部导航栏样式（与主页Layout统一）
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
}

// 3. 表单容器样式（居中布局）
.login-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 20px;
  margin-top: 10px; // 避开顶部导航栏
}

.login-card {
  width: 100%;
  max-width: 480px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 40px;
}

// 4. 表单内部样式
.form-header {
  text-align: center;
  margin-bottom: 32px;
}

.title {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.desc {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.login-form {
  width: 100%;
}

.el-form-item {
  margin-bottom: 24px !important;
}

.el-input {
  border-radius: 6px !important;
  border-color: #dcdfe6 !important;
  transition: all 0.2s ease;

  &:focus {
    border-color: #409eff !important;
    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2) !important;
  }
}

// 登录专属：记住密码与忘记密码
.remember-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px !important;
}

.forgot-link {
  font-size: 14px;
}

// 提交按钮
.submit-item {
  margin-bottom: 16px !important;
}

.submit-btn {
  width: 100%;
  height: 46px;
  border-radius: 6px !important;
  font-size: 16px !important;
  background-color: #409eff !important;
  border-color: #409eff !important;
}

.submit-btn:hover {
  background-color: #66b1ff !important;
  border-color: #66b1ff !important;
}

// 切换表单链接
.switch-item {
  text-align: center;
  margin-bottom: 0 !important;
}

.switch-link {
  font-size: 14px;
  color: #409eff !important;
}
</style>