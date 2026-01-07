<script setup>
import { ref, onMounted, watch } from 'vue'
import { marked } from 'marked'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Cpu, User, Refresh, ArrowRight } from '@element-plus/icons-vue'

import {
  getChatHistoryService,
  saveMessageService,
  getAiResponseService,
  clearChatHistoryService
} from '@/api/aichat.js'

import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore()

// 用户信息
const userId = ref(userInfoStore.info?.id || null)
const avatar = ref(userInfoStore.info?.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png')

// ----------- 会话ID（每次进入页面自动生成一个新的） -----------
function createSessionId() {
  return 'session_' + Date.now() + '_' + Math.random().toString(36).substring(2, 10)
}
const sessionId = ref(createSessionId())

// 聊天数据
const messages = ref([])
const inputContent = ref('')
const isLoading = ref(false)
const isSending = ref(false)

// ----------- 初始化欢迎语 -----------
function setWelcomeMessage() {
  messages.value = [{
    id: Date.now(),
    sender: "ai",
    content: "你好！我是你的规划助手，可以帮助你分析学习计划、职业方向、报考建议等~",
    renderedContent: "你好！我是你的规划助手，可以帮助你分析学习计划、职业方向、报考建议等~",
    time: formatTime(new Date())
  }]
}

// ----------- 时间格式化 -----------
function formatTime(val) {
  const d = new Date(val)
  if (isNaN(d.getTime())) return ''

  const pad = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} `
      + `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`
}

// ----------- 加载历史消息 -----------
const loadHistory = async () => {
  console.log(userId.value)
  console.log(sessionId.value)
  if (!userId.value) {
    setWelcomeMessage()
    return
  }

  try {
    const res = await getChatHistoryService({
      userId: userId.value
    })

    if (res.code === 0 && Array.isArray(res.data) && res.data.length > 0) {
      messages.value = res.data.map(m => ({
        id: m.id,
        sender: m.role === 0 ? 'user' : 'ai',
        content: m.content,
        renderedContent: m.role === 1 ? marked(m.content) : m.content,
        time: formatTime(m.createTime)
      }))
    } else {
      setWelcomeMessage()
    }
  } catch (e) {
    console.error("加载聊天记录失败:", e)
    setWelcomeMessage()
  }
}

// ----------- 自动滚动到底部 -----------
function scrollToBottom() {
  setTimeout(() => {
    const el = document.querySelector('.chat-content .el-scrollbar__wrap')
    if (el) el.scrollTop = el.scrollHeight
  }, 20)
}

// ----------- 发送消息（用户 → AI） -----------
const sendMessage = async () => {
  if (isSending.value) return
  if (!inputContent.value.trim()) {
    ElMessage.warning("请输入内容")
    return
  }

  isSending.value = true

  const text = inputContent.value.trim()

  // 添加用户消息
  const userMsg = {
    id: Date.now(),
    sender: "user",
    content: text,
    renderedContent: text,
    time: formatTime(new Date())
  }
  messages.value.push(userMsg)

  inputContent.value = ""
  isLoading.value = true

  try {
    // 请求 AI 回复
    const res = await getAiResponseService({
      userId: userId.value,
      sessionId: sessionId.value,
      content: text
    })

    const aiText = res?.data || "抱歉，我暂时无法回答，请稍后再试~"

    // 显示 AI 回复
    messages.value.push({
      id: Date.now() + 1,
      sender: "ai",
      content: aiText,
      renderedContent: marked(aiText),
      time: formatTime(new Date())
    })

  } catch (e) {
    console.error(e)
    ElMessage.error(e)
  } finally {
    isSending.value = false
    isLoading.value = false
  }
}

// ----------- 清空聊天（仅前端清屏 + 新会话）-----------
const clearChat = async () => {
  ElMessageBox.confirm(
      "确认清空当前会话的聊天记录？",
      "提示",
      { type: "warning" }
  ).then(() => {

    // 1️⃣ 本地消息列表清空
    messages.value = []

    // 2️⃣ 生成新的 sessionId
    sessionId.value = createSessionId()

    // 3️⃣ 重新显示欢迎语
    setWelcomeMessage()

    ElMessage.success("聊天已清空（已开启新会话）")
  })
}


// ----------- 生命周期：进入页面就加载记录 -----------
onMounted(() => loadHistory())

watch([messages, isLoading], () => scrollToBottom())

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
                <p class="bubble-content" v-html="msg.renderedContent"></p>
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