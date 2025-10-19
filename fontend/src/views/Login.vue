<script setup>
import {User, UserFilled, Phone, Message, Lock} from '@element-plus/icons-vue'
import {ref} from 'vue'
import {ElMessage, ElForm} from 'element-plus'

// 默认显示登录，符合平台使用场景
const isRegister = ref(false)
const registerData = ref({
  username: '',
  password: '',
  rePassword: '',
  name: '',
  phone: '',
  email: ''
})
const formRef = ref(null)
// 记住我绑定值
const rememberMe = ref(false)

// 密码校验函数（不变）
const checkPassword = (rule, value, callback) => {
  if (value === '')
    callback(new Error('请再次确认密码'))
  else if (value !== registerData.value.password)
    callback(new Error('两次输入密码不一致!'))
  else
    callback()
}

// 表单校验规则（不变）
const rules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 5, max: 16, message: '用户名长度在 5 到 16 个字符', trigger: 'blur'}
  ],
  name: [
    {min: 5, max: 16, message: '昵称长度在 5 到 16 个字符', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {pattern: /^1[3-9]\d{9}$/, message: '请输入有效的11位手机号', trigger: 'blur'}
  ],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {type: 'email', message: '请输入有效的邮箱地址（如：student@xxx.com）', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, max: 16, message: '密码长度在 5 到 16 个字符', trigger: 'blur'}
  ],
  rePassword: [
    {validator: checkPassword, trigger: 'blur'}
  ]
}

// 接口调用逻辑
import { userRegisterService, userLoginService } from '@/api/user.js'

const register = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  let result = await userRegisterService(registerData.value)
  if (result.code === 0) {
    ElMessage.success('注册成功！欢迎加入大学生自我规划平台～')
    isRegister.value = true
    registerData.value = {...registerData.value, password: '', rePassword: '', name: '', phone: '', email: ''}
  } else {
    ElMessage.error(result.data || '注册失败，请重试')
  }
}

const login = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  let result = await userLoginService(registerData.value)
  if (result.code === 0) {
    ElMessage.success('登录成功！开启你的规划之旅～')
    // 跳转至平台首页
  } else {
    ElMessage.error(result.data || '用户名或密码错误')
  }
}

const clearRegisterData = () => {
  registerData.value = {
    username: '',
    name: '',
    phone: '',
    email: '',
    password: '',
    rePassword: ''
  }
  formRef.value?.resetFields()
  rememberMe.value = false
}
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 左侧品牌区 -->
      <div class="login-brand">
        <div class="brand-logo">
          <span class="logo-text">USSP</span>
        </div>
        <h2 class="brand-title">大学生自我规划平台</h2>
        <p class="brand-desc">在这里，清晰你的目标，规划你的未来</p>
        <div class="brand-features">
          <div class="feature-item">📚 学业规划</div>
          <div class="feature-item">💼 职业探索</div>
          <div class="feature-item">🎯 目标管理</div>
        </div>
      </div>

      <!-- 右侧表单区 -->
      <div class="login-form-wrapper">
        <!-- 注册表单 -->
        <el-form
            ref="formRef"
            size="large"
            autocomplete="off"
            v-if="isRegister"
            :model="registerData"
            :rules="rules"
            class="login-form"
        >
          <el-form-item class="form-header">
            <h3>新用户注册</h3>
          </el-form-item>

          <el-form-item prop="username" class="form-item">
            <div class="form-tip">用户名用于登录，创建后暂不支持修改</div>
            <el-input
                :prefix-icon="User"
                placeholder="请设置用户名（5-16位）"
                v-model="registerData.username"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="name" class="form-item">
            <el-input
                :prefix-icon="UserFilled"
                placeholder="请输入昵称（选填，5-16位）"
                v-model="registerData.name"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="phone" class="form-item">
            <el-input
                :prefix-icon="Phone"
                placeholder="请输入手机号（用于账号安全）"
                v-model="registerData.phone"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="email" class="form-item">
            <el-input
                :prefix-icon="Message"
                placeholder="请输入邮箱（如：student@xxx.com）"
                v-model="registerData.email"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="password" class="form-item">
            <el-input
                :prefix-icon="Lock"
                type="password"
                placeholder="请设置密码（5-16位）"
                v-model="registerData.password"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="rePassword" class="form-item">
            <el-input
                :prefix-icon="Lock"
                type="password"
                placeholder="请再次输入密码"
                v-model="registerData.rePassword"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item class="form-item form-btn-group">
            <el-button
                class="form-btn"
                type="primary"
                auto-insert-space
                @click="register"
            >
              注册并加入平台
            </el-button>
          </el-form-item>

          <el-form-item class="form-switch">
            <el-link
                type="info"
                :underline="false"
                @click="isRegister = false; clearRegisterData()"
            >
              ← 已有账号？直接登录
            </el-link>
          </el-form-item>
        </el-form>

        <!-- 登录表单 -->
        <el-form
            ref="formRef"
            size="large"
            autocomplete="off"
            v-else
            :model="registerData"
            :rules="rules"
            class="login-form"
        >
          <el-form-item class="form-header">
            <h3>账号登录</h3>
            <p class="header-tip">欢迎回来，继续你的规划进度</p>
          </el-form-item>

          <el-form-item prop="username" class="form-item">
            <el-input
                :prefix-icon="User"
                placeholder="请输入用户名"
                v-model="registerData.username"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item prop="password" class="form-item">
            <el-input
                name="password"
                :prefix-icon="Lock"
                type="password"
                placeholder="请输入密码"
                v-model="registerData.password"
                class="form-input"
            ></el-input>
          </el-form-item>

          <el-form-item class="form-item form-option-group">
            <div class="option-left">
              <el-checkbox v-model="rememberMe" class="remember-checkbox">记住登录状态</el-checkbox>
            </div>
            <div class="option-right">
              <el-link type="primary" :underline="false" class="forgot-link">忘记密码？</el-link>
            </div>
          </el-form-item>

          <el-form-item class="form-item form-btn-group">
            <el-button
                class="form-btn"
                type="primary"
                auto-insert-space
                @click="login"
            >
              登录平台
            </el-button>
          </el-form-item>

          <el-form-item class="form-switch">
            <el-link
                type="info"
                :underline="false"
                @click="isRegister = true; clearRegisterData()"
            >
              还没有账号？立即注册 →
            </el-link>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

// 主题色「青春橙」色系
$primary-color: #ff7a45; // 主色：活力橙
$light-primary: #fff5f0; // 浅色：背景辅助
$dark-primary: #e05a28; // 深色：hover/强调

.login-container {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #fff8f5 0%, #fff5f0 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 950px;
  background-color: #fff;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(255, 122, 69, 0.1);
  overflow: hidden;
  display: flex;
  flex-direction: row;

  @media (max-width: 768px) {
    flex-direction: column;
    max-width: 420px;
  }
}

// 左侧品牌区：增加平台特色展示
.login-brand {
  width: 42%;
  background: linear-gradient(180deg, $primary-color 0%, $dark-primary 100%);
  color: #fff;
  padding: 50px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;

  @media (max-width: 768px) {
    width: 100%;
    padding: 35px 20px;
  }

  .brand-logo {
    width: 80px;
    height: 80px;
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;

    .logo-text {
      font-size: 24px;
      font-weight: 700;
    }
  }

  .brand-title {
    font-size: 26px;
    margin-bottom: 12px;
    font-weight: 600;
  }

  .brand-desc {
    font-size: 15px;
    opacity: 0.9;
    line-height: 1.6;
    margin-bottom: 30px;
  }

  // 平台特色项：直观展示功能价值
  .brand-features {
    display: flex;
    gap: 15px;
    flex-wrap: wrap;
    justify-content: center;

    .feature-item {
      font-size: 13px;
      background-color: rgba(255, 255, 255, 0.15);
      padding: 6px 12px;
      border-radius: 20px;
    }
  }
}

.login-form-wrapper {
  width: 58%;
  padding: 50px 40px;
  display: flex;
  justify-content: center;
  align-items: center;

  @media (max-width: 768px) {
    width: 100%;
    padding: 35px 20px;
  }
}

.login-form {
  width: 100%;
  max-width: 340px;
}

.form-header {
  margin-bottom: 35px !important;
  text-align: center;

  h3 {
    font-size: 24px;
    color: #2d3748;
    font-weight: 600;
    margin-bottom: 8px;
  }

  .header-tip {
    font-size: 13px;
    color: #718096;
  }
}

.form-item {
  margin-bottom: 20px !important;
}

.form-input {
  border-radius: 8px !important;
  border-color: #e2e8f0 !important;
  transition: all 0.3s ease;

  &:focus {
    border-color: $primary-color !important;
    box-shadow: 0 0 0 2px rgba(56, 178, 172, 0.15) !important;
  }
}

.form-tip {
  font-size: 12px;
  color: #718096;
  margin-bottom: 6px;
}

.form-option-group {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 26px !important;

  .option-left {
    .remember-checkbox {
      font-size: 13px;
      color: #4a5568;
    }
  }

  .option-right {
    .forgot-link {
      font-size: 13px;
      color: $primary-color !important;
    }
  }
}

.form-btn-group {
  margin-bottom: 18px !important;
}

.form-btn {
  width: 100%;
  height: 44px;
  border-radius: 8px !important;
  background-color: $primary-color !important;
  border-color: $primary-color !important;
  font-size: 16px !important;
  transition: all 0.3s ease;

  &:hover {
    background-color: $dark-primary !important;
    border-color: $dark-primary !important;
  }
}

.form-switch {
  text-align: center;
  margin-bottom: 0 !important;

  el-link {
    font-size: 14px;
    color: $primary-color !important;
  }
}
</style>