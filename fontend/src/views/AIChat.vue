<script setup>
import { ref, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Cpu, User, Refresh, ArrowRight } from '@element-plus/icons-vue'

// API 服务
import {
  getChatHistoryService,
  saveMessageService,
  getAiResponseService,
  clearChatHistoryService
} from '@/api/aichat.js'

import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore();

const avatar = ref(userInfoStore.info?.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png')
// 头像加载失败处理
const handleAvatarError = (e) => {
  // 加载失败时使用默认图片
  e.target.src = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
}

// 会话ID
const sessionId = ref(localStorage.getItem('ai_chat_session_id') || createSessionId())
const userId = ref(userInfoStore.info?.id || null) // 添加空值保护

// 防止重复发送的锁
const isSending = ref(false)

// 创建一个新的会话ID
function createSessionId() {
  const sid = 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9)
  localStorage.setItem("ai_chat_session_id", sid)
  return sid
}

// 消息列表
const messages = ref([])
// 输入框
const inputContent = ref('')
// loading
const isLoading = ref(false)

// ------------------ 初始化聊天记录 ------------------
const initChatHistory = async () => {
  if (!userId.value) {
    setWelcomeMessage()
    return
  }

  try {
    const res = await getChatHistoryService({
      userId: userId.value,
      sessionId: sessionId.value
    })

    if (res.code === 0) {
      if (Array.isArray(res.data) && res.data.length > 0) {
        messages.value = res.data.map(item => ({
          id: item.id,
          content: item.content,
          sender: item.role === 0 ? 'user' : 'ai',
          time: formatTime(item.create_time)
        }))
      } else {
        setWelcomeMessage()
      }
    } else {
      setWelcomeMessage()
    }
  } catch (e) {
    console.error('加载聊天记录失败:', e)
    setWelcomeMessage()
    ElMessage.error("无法连接服务器，请确认后端是否启动")
  }
}

function setWelcomeMessage() {
  messages.value = [{
    id: Date.now(),
    content: '你好！我是你的规划助手，可以帮你分析学习计划、解答规划疑问~',
    sender: 'ai',
    time: new Date().toTimeString().slice(0, 5)
  }]
}

// 格式化时间
const formatTime = (val) => {
  try {
    const d = new Date(val)
    return d.toTimeString().slice(0, 5)
  } catch (e) {
    return new Date().toTimeString().slice(0, 5)
  }
}

// ------------------ 保存消息（统一后端字段） ------------------
const saveMessage = async (content, role) => {
  try {
    const res = await saveMessageService({
      userId: userId.value,
      sessionId: sessionId.value,
      role,
      content
    })
    if (res.code === 0) {
      return res.data
    }
  } catch (e) {
    console.error("保存消息失败:", e)
    // 不显示错误提示，避免打扰用户体验
  }
  return null
}

// ------------------ 请求 AI 回复 ------------------
const askAI = async (content) => {
  try {
    const res = await getAiResponseService({
      userId: userId.value,
      sessionId: sessionId.value,
      content
    })

    if (res.code === 0) {
      return res.data.content || "抱歉，我没有理解你的问题。"
    } else {
      return res.message || '抱歉，我暂时无法回答，请稍后再试~'
    }
  } catch (e) {
    console.error("AI服务请求失败:", e)
    return "抱歉，我暂时无法回答，请稍后再试~"
  }
}

// ------------------ 发送消息 ------------------
const sendMessage = async () => {
  if (isSending.value) return
  isSending.value = true

  try {
    if (!inputContent.value.trim()) {
      ElMessage.warning("请输入内容")
      return
    }

    const text = inputContent.value.trim()

    // 前端只展示，不保存
    messages.value.push({
      id: Date.now(),
      content: text,
      sender: "user",
      time: formatTime(new Date())
    })

    inputContent.value = ""
    isLoading.value = true

    // 只调用后端，让后端保存消息
    const res = await getAiResponseService({
      userId: userId.value,
      sessionId: sessionId.value,
      content: text
    })

    const aiText = res.data

    // 前端展示AI消息
    messages.value.push({
      id: Date.now() + 1,
      content: aiText,
      sender: "ai",
      time: formatTime(new Date())
    })

  } catch (e) {
    console.error(e)
    ElMessage.error("发送失败")
  } finally {
    isLoading.value = false
    isSending.value = false
  }
}

// ------------------ 清空聊天记录 ------------------
const clearChat = async () => {
  ElMessageBox.confirm(
      '确定要清空聊天记录吗？',
      '提示',
      { type: 'warning' }
  ).then(async () => {
    try {
      if (userId.value) {
        await clearChatHistoryService({
          userId: userId.value,
          sessionId: sessionId.value
        })
      }

      sessionId.value = createSessionId()
      setWelcomeMessage()
      ElMessage.success("聊天记录已清空")
    } catch (e) {
      console.error('清空聊天记录失败:', e)
      ElMessage.error("清空失败")
    }
  })
}

// 滚动到底部
const scrollToBottom = () => {
  setTimeout(() => {
    const el = document.querySelector('.chat-content .el-scrollbar__wrap')
    if (el) el.scrollTop = el.scrollHeight
  }, 50)
}

onMounted(() => {
  initChatHistory().then(() => scrollToBottom())
})

watch([messages, isLoading], () => {
  scrollToBottom()
})

// 在控制台执行，检查滚动相关属性
console.log('body overflow:', getComputedStyle(document.body).overflow);
console.log('html overflow:', getComputedStyle(document.documentElement).overflow);
console.log('body height:', getComputedStyle(document.body).height);
</script>

<template>
  <div class="ai-chat-container"> <!-- 修改容器类名 -->
    <!-- 页面标题栏 -->
    <div class="page-header">
      <h2 class="page-title">
        <el-icon><Cpu /></el-icon>
        AI规划助手
      </h2>
      <el-button
          type="text"
          @click="clearChat"
          class="clear-btn"
      >
        <el-icon><Refresh /></el-icon>
        清空聊天
      </el-button>
    </div>

    <!-- 聊天区域（内容区+输入区） -->
    <div class="chat-wrapper">
      <!-- 聊天内容区 -->
      <el-scrollbar class="chat-content">
        <div class="message-list">
          <!-- 消息列表 -->
          <div
              v-for="msg in messages"
              :key="msg.id"
              :class="['message-item', msg.sender === 'ai' ? 'ai-message' : 'user-message']"
          >
            <!-- AI消息 -->
            <template v-if="msg.sender === 'ai'">
              <el-avatar class="message-avatar ai-avatar">
                <el-icon><Cpu /></el-icon>
              </el-avatar>
              <div class="message-bubble ai-bubble">
                <p class="bubble-content" v-html="msg.content.replace(/\n/g, '<br>')"></p>
                <span class="message-time">{{ msg.time }}</span>
              </div>
            </template>

            <!-- 用户消息 -->
            <template v-else>
              <div class="message-bubble user-bubble">
                <p class="bubble-content" v-html="msg.content.replace(/\n/g, '<br>')"></p>
                <span class="message-time">{{ msg.time }}</span>
              </div>
              <el-avatar class="message-avatar user-avatar">
                <img :src="avatar" alt="用户头像" @error="handleAvatarError" />
              </el-avatar>
            </template>
          </div>

          <!-- 加载中状态 -->
          <div v-if="isLoading" class="loading-item">
            <el-avatar class="message-avatar ai-avatar">
              <el-icon><Cpu /></el-icon>
            </el-avatar>
            <div class="message-bubble ai-bubble loading-bubble">
              <div class="loading-dots">
                <span class="dot"></span>
                <span class="dot"></span>
                <span class="dot"></span>
              </div>
            </div>
          </div>
        </div>
      </el-scrollbar>

      <!-- 输入区（固定在底部） -->
      <div class="input-area">
        <el-input
            v-model="inputContent"
            type="textarea"
            placeholder="输入你的问题，比如“如何制定考研复习计划？”"
            :rows="2"
            resize="none"
            @keyup.enter.exact="sendMessage"
            class="chat-input"
            :disabled="!userId || isSending"
        />
        <el-button
            type="primary"
            @click="sendMessage"
            class="send-btn"
            :disabled="isLoading || isSending || !userId || !inputContent.trim()"
        >
          <el-icon v-if="isLoading"><ArrowRight /></el-icon>
          <span>{{ isLoading ? '发送中...' : '发送' }}</span>
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 修改容器样式，去掉100vh，使用max-height */
.ai-chat-container {
  display: flex;
  flex-direction: column;
  background-color: #f8f9fa;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  min-height: 600px;
  max-height: calc(100vh - 40px); /* 关键修改：限制最大高度 */
  width: 100%;
}

/* 标题栏 */
.page-header {
  padding: 16px 24px;
  border-bottom: 1px solid #EBEEF5;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.page-title {
  font-size: 20px;
  color: #2c3e50;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0;
}
.clear-btn {
  color: #606266;
  transition: color 0.2s;
}
.clear-btn:hover {
  color: #409EFF;
}

/* 聊天区域：占满中间高度，输入区固定在底部 */
.chat-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 24px;
  overflow: hidden;
}

/* 聊天内容区：占满中间，可滚动 */
.chat-content {
  flex: 1;
  margin-bottom: 20px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  padding: 20px;
  /* 关键修改：设置最小高度+最大高度，强制开启滚动 */
  min-height: 300px;
  max-height: 600px;
  overflow-y: auto; /* 强制垂直滚动 */
}
.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 消息项样式 */
.message-item {
  display: flex;
  gap: 12px;
  max-width: 80%;
}
.ai-message {
  align-self: flex-start;
}
.user-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}

/* 头像样式 */
.message-avatar {
  width: 40px;
  height: 40px;
  flex-shrink: 0;
}
.ai-avatar {
  background-color: #409EFF !important;
  color: #fff !important;
}
.user-avatar {
  background-color: #67C23A !important;
  color: #fff !important;
}

/* 消息气泡样式 */
.message-bubble {
  padding: 12px 16px;
  border-radius: 10px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  line-height: 1.6;
}
.ai-bubble {
  background-color: #f5f7fa;
  color: #333;
  border-bottom-left-radius: 4px;
}
.user-bubble {
  background-color: #67C23A;
  color: #fff;
  border-bottom-right-radius: 4px;
}
.bubble-content {
  margin: 0 0 6px;
  font-size: 14px;
}
.message-time {
  font-size: 12px;
  color: #909399;
  display: block;
  text-align: right;
}
.user-bubble .message-time {
  color: rgba(255, 255, 255, 0.8);
}

/* 加载中样式 */
.loading-item {
  align-self: flex-start;
  display: flex;
  gap: 12px;
}
.loading-bubble {
  padding: 12px 16px;
}
.loading-dots {
  display: flex;
  gap: 6px;
}
.loading-dots .dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #409EFF;
  animation: blink 1.4s infinite both;
}
.loading-dots .dot:nth-child(2) {
  animation-delay: 0.2s;
}
.loading-dots .dot:nth-child(3) {
  animation-delay: 0.4s;
}
@keyframes blink {
  0% { opacity: 0.3; transform: scale(0.8); }
  50% { opacity: 1; transform: scale(1); }
  100% { opacity: 0.3; transform: scale(0.8); }
}

/* 输入区：固定在底部，宽度自适应 */
.input-area {
  display: flex;
  gap: 12px;
  align-items: flex-end;
  background-color: #fff;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}
.chat-input {
  flex: 1;
  border-radius: 8px;
}
.send-btn {
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 14px;
}
</style>