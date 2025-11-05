<script setup>
import { ref, reactive } from 'vue'
import { ElFooter, ElButton, ElDialog, ElForm, ElFormItem, ElSelect, ElOption, ElInput } from 'element-plus'

// 意见反馈弹窗状态
const feedbackVisible = ref(false)
// 反馈表单数据
const feedbackForm = reactive({
  type: '',
  content: '',
  contact: ''
})

// 显示反馈弹窗
const handleShowFeedback = () => {
  feedbackVisible.value = true
}

// 提交反馈（实际需调用接口，此处模拟）
const handleSubmitFeedback = () => {
  if (!feedbackForm.type || !feedbackForm.content) {
    ElMessage.warning('请选择反馈类型并填写内容')
    return
  }
  // 模拟提交成功
  ElMessage.success('反馈提交成功，感谢您的支持！')
  feedbackVisible.value = false
  // 重置表单
  feedbackForm.type = ''
  feedbackForm.content = ''
  feedbackForm.contact = ''
}
</script>

<template>
  <el-footer class="layout-footer">
    <div class="container">
      <!-- 1. 平台归属与联系 -->
      <div class="footer-top">
        <div class="school-info">
          <p class="school-name">大学生自我规划平台</p>
<!--          <p class="project-desc">上海建桥学院 信息技术学院 软件工程专业 毕设项目</p>-->
        </div>
        <div class="contact-info">
          <el-button
              type="text"
              class="feedback-btn"
              @click="handleShowFeedback"
          >
            意见反馈
          </el-button>
        </div>
      </div>

      <!-- 2. 合规链接与版权 -->
      <div class="footer-bottom">
        <div class="links">
          <a href="/privacy" class="link-item">隐私政策</a>
          <a href="/agreement" class="link-item">用户协议</a>
        </div>
        <div class="copyright">
          © 2026 陆楠. 版权所有.
        </div>
      </div>
    </div>

    <!-- 意见反馈弹窗（点击按钮弹出） -->
    <el-dialog
        title="意见反馈"
        v-model="feedbackVisible"
        width="500px"
    >
      <el-form :model="feedbackForm" label-width="80px">
        <el-form-item label="反馈类型">
          <el-select v-model="feedbackForm.type" placeholder="请选择">
            <el-option label="功能建议" value="suggestion"></el-option>
            <el-option label="bug反馈" value="bug"></el-option>
            <el-option label="其他问题" value="other"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="反馈内容">
          <el-input
              v-model="feedbackForm.content"
              type="textarea"
              rows="4"
              placeholder="请描述您的问题或建议..."
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input
              v-model="feedbackForm.contact"
              placeholder="请输入邮箱或QQ，方便回复您"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="feedbackVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitFeedback">提交</el-button>
      </template>
    </el-dialog>
  </el-footer>
</template>

<style scoped>
.layout-footer {
  background-color: #fff;
  border-top: 1px solid #f2f2f2;
  padding: 24px 0;
  color: #999;
  font-size: 12px;
}

.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* 顶部：平台归属与联系 */
.footer-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 16px;
  border-bottom: 1px solid #f2f2f2;
}
.school-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.school-name {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}
.project-desc {
  color: #999;
}
.contact-info {
  display: flex;
  align-items: center;
  gap: 16px;
}
.teacher-contact {
  color: #999;
}
.feedback-btn {
  color: #409eff;
  font-size: 12px;
}

/* 底部：链接与版权 */
.footer-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.links {
  display: flex;
  gap: 24px;
}
.link-item {
  color: #999;
  text-decoration: none;
  transition: color 0.2s;
}
.link-item:hover {
  color: #409eff;
}
.copyright {
  color: #999;
}

/* 反馈弹窗样式 */
.el-dialog__title {
  font-size: 16px;
  font-weight: 600;
}
.el-form-item {
  margin-bottom: 16px;
}
</style>