<script setup>
import { ref, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  ElCard, ElInput, ElButton, ElScrollbar,
  ElAvatar, ElIcon
} from 'element-plus'
import {
  User, Refresh, ArrowRight, Cpu as BotIcon
} from '@element-plus/icons-vue'

// 消息列表
const messages = ref([
  {
    id: 1,
    content: '你好！我是你的规划助手，可以帮你分析学习计划、解答规划疑问~',
    sender: 'ai',
    time: new Date().toTimeString().slice(0, 5)
  }
])
// 输入框内容
const inputContent = ref('')
// 加载状态
const isLoading = ref(false)

// 发送消息
const sendMessage = async () => {
  if (!inputContent.value.trim()) {
    ElMessage.warning('请输入内容后再发送~')
    return
  }

  // 添加用户消息
  const userMsg = {
    id: Date.now(),
    content: inputContent.value,
    sender: 'user',
    time: new Date().toTimeString().slice(0, 5)
  }
  messages.value.push(userMsg)
  inputContent.value = ''

  // 模拟AI回复
  isLoading.value = true
  try {
    await new Promise(resolve => setTimeout(resolve, 1500))
    const aiMsg = {
      id: Date.now() + 1,
      content: '基于你的需求，我为你整理了以下建议：\n1. 先明确你的核心目标方向\n2. 结合自身能力制定阶段性计划\n3. 定期复盘调整学习节奏',
      sender: 'ai',
      time: new Date().toTimeString().slice(0, 5)
    }
    messages.value.push(aiMsg)
  } catch (err) {
    ElMessage.error('AI回复失败，请重试~')
  } finally {
    isLoading.value = false
  }
}

// 清空聊天记录
const clearChat = () => {
  ElMessageBox.confirm(
      '确定要清空聊天记录吗？',
      '提示',
      { type: 'warning' }
  ).then(() => {
    messages.value = [
      {
        id: 1,
        content: '你好！我是你的规划助手，可以帮你分析学习计划、解答规划疑问~',
        sender: 'ai',
        time: new Date().toTimeString().slice(0, 5)
      }
    ]
    ElMessage.success('聊天记录已清空')
  }).catch(() => {
    ElMessage.info('已取消清空')
  })
}

// 滚动到最新消息
const scrollToBottom = () => {
  const scrollContainer = document.querySelector('.chat-content .el-scrollbar__wrap')
  if (scrollContainer) {
    scrollContainer.scrollTop = scrollContainer.scrollHeight
  }
}

// 页面挂载后滚动到底部
onMounted(() => {
  scrollToBottom()
})

// 监听消息变化，自动滚动到底部
watch([messages, isLoading], () => {
  scrollToBottom()
})
</script>

<template>
  <div class="ai-chat-page">
    <!-- 页面标题栏 -->
    <div class="page-header">
      <h2 class="page-title">
        <el-icon><BotIcon /></el-icon>
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
                <el-icon><BotIcon /></el-icon>
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
                <el-icon><User /></el-icon>
              </el-avatar>
            </template>
          </div>

          <!-- 加载中状态 -->
          <div v-if="isLoading" class="loading-item">
            <el-avatar class="message-avatar ai-avatar">
              <el-icon><BotIcon /></el-icon>
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
            @keyup.enter="sendMessage"
            class="chat-input"
        />
        <el-button
            type="primary"
            @click="sendMessage"
            class="send-btn"
        >
          发送
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局布局：页面占满全屏，内容区+输入区+页脚 */
.ai-chat-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8f9fa;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
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

/* 页脚：固定在页面底部 */
.page-footer {
  padding: 16px 24px;
  border-top: 1px solid #EBEEF5;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: #909399;
}
.footer-left {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.footer-links {
  display: flex;
  gap: 16px;
}
.footer-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 4px;
}
.link {
  color: #409EFF;
  cursor: pointer;
}
.link:hover {
  text-decoration: underline;
}
</style>