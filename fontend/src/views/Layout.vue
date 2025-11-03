<script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import FooterVue from './Footer.vue'
  // 引入Element Plus组件和图标
  import {
    ElHeader, ElMenu, ElMenuItem, ElButton, ElDropdown, ElDropdownMenu,
    ElDropdownItem, ElAvatar, ElIcon
  } from 'element-plus'
  import {
    School, HomeFilled, Calendar, Message, Notebook, ArrowDownBold
  } from '@element-plus/icons-vue'

  const router = useRouter()

  // 状态管理：可后续接入Pinia存储用户状态
  const isLogin = ref(false) // 登录状态（初始未登录，实际需从缓存/接口获取）
  const userName = ref('') // 用户名（登录后赋值）
  const userAvatar = ref('https://cube.elemecdn.com/3/7c/3ea6beec64369e258433772d95361jpeg.jpeg') // 默认头像
  const activeMenu = ref('home') // 当前激活的导航菜单

  // 导航菜单切换：跳转到对应页面
  const handleMenuSelect = (index) => {
    activeMenu.value = index
    switch(index) {
      case 'home':
        router.push('/home')
        break
      case 'planning':
        router.push('/planning') // 规划模块主页（后续开发）
        break
      case 'ai-chat':
        router.push('/ai-chat') // AI对话页面（后续开发）
        break
      case 'resources':
        router.push('/resources') // 资源中心页面（后续开发）
        break
    }
  }

  // 跳转登录/注册页面（复用已实现的页面）
  const handleGoToLogin = () => router.push('/login')
  const handleGoToRegister = () => router.push('/login?type=register')

  // 已登录状态下的操作
  const handleGoToProfile = () => router.push('/profile') // 个人中心（后续开发）
  const handleGoToPlanProgress = () => router.push('/planning/progress') // 规划进度（后续开发）
  const handleLogout = () => {
    // 退出登录逻辑：清除缓存、重置状态
    isLogin.value = false
    userName.value = ''
    router.push('/login')
  }

</script>

<template>
  <el-container class="full-height">
    <!-- 顶部导航栏：固定在页面顶部，滚动时保持显示 -->
    <el-header class="layout-header" fixed>
      <div class="container">
        <!-- 1. 平台名称 -->
        <div class="logo">
          <el-icon class="logo-icon"><School /></el-icon>
          <span class="logo-text">大学生自我规划平台</span>
        </div>

        <!-- 2. 四大功能导航（PC端横向显示，移动端折叠为菜单） -->
        <el-menu
            :default-active="activeMenu"
            class="nav-menu"
            mode="horizontal"
            @select="handleMenuSelect"
        >
          <el-menu-item index="home">
            <el-icon><HomeFilled /></el-icon>
            <span>主页</span>
          </el-menu-item>
          <el-menu-item index="planning">
            <el-icon><Calendar /></el-icon>
            <span>规划</span>
          </el-menu-item>
          <el-menu-item index="ai-chat">
            <el-icon><Message /></el-icon>
            <span>AI对话</span>
          </el-menu-item>
          <el-menu-item index="resources">
            <el-icon><Notebook /></el-icon>
            <span>资源</span>
          </el-menu-item>
        </el-menu>

        <!-- 3. 登录/注册入口（登录后显示用户信息） -->
        <div class="user-actions">
          <!-- 未登录状态 -->
          <template v-if="!isLogin">
            <el-button type="text" @click="handleGoToLogin">登录</el-button>
            <el-button type="primary" @click="handleGoToRegister">注册</el-button>
          </template>

          <!-- 已登录状态：下拉菜单显示用户信息 -->
          <template v-else>
            <el-dropdown trigger="hover" placement="bottom-end">
              <div class="user-info">
                <el-avatar :src="userAvatar" class="user-avatar" />
                <span class="user-name">{{ userName }}</span>
                <el-icon class="dropdown-icon"><ArrowDownBold /></el-icon>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleGoToProfile">个人中心</el-dropdown-item>
                  <el-dropdown-item @click="handleGoToPlanProgress">规划进度</el-dropdown-item>
                  <el-dropdown-item @click="handleLogout" type="danger">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </div>
      </div>
    </el-header>

    <!-- 2. 中间内容区（路由页面会显示在这里） -->
    <el-main class="main-content">
      <router-view /> <!-- 这里会渲染子页面（如首页、规划页等） -->
    </el-main>

    <!-- 3. 底部页脚 -->
    <FooterVue />
  </el-container>
</template>

<style scoped>
  .layout-header {
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    padding: 0;
    z-index: 100; /* 确保导航栏在最上层，不被内容遮挡 */
  }

  .container {
    width: 1200px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 64px; /* 导航栏固定高度 */
  }

  /* 平台名称样式 */
  .logo {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  .logo-icon {
    color: #409eff; /* Element Plus主题色，贴合技术栈 */
    font-size: 24px;
  }
  .logo-text {
    font-size: 18px;
    font-weight: 600;
    color: #333;
  }

  /* 导航菜单样式 */
  .nav-menu {
    flex: 1;
    margin: 0 40px;
  }
  .nav-menu .el-menu-item {
    font-size: 14px;
    color: #666;
    margin: 0 12px;
  }
  .nav-menu .el-menu-item.is-active {
    color: #409eff; /* 激活状态用主题色 */
    font-weight: 500;
  }

  /* 用户操作区样式 */
  .user-actions {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 4px 8px;
    border-radius: 4px;
    transition: background-color 0.2s;
  }
  .user-info:hover {
    background-color: #f5f7fa;
  }
  .user-avatar {
    width: 36px;
    height: 36px;
  }
  .user-name {
    font-size: 14px;
    color: #333;
  }
  .dropdown-icon {
    font-size: 14px;
    color: #999;
  }

  /* 确保容器占满屏幕，让Footer在底部 */
  .el-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }
</style>