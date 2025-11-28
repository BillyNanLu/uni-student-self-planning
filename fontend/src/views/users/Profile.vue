<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElUpload, ElDialog } from 'element-plus'
import {
  ElCard, ElButton, ElAvatar, ElForm,
  ElFormItem, ElInput, ElTable, ElTableColumn
} from 'element-plus'

const router = useRouter()

// 模拟用户数据
const userInfo = ref({
  username: 'BillyNan_Lu',
  nickname: '编程爱好者',
  avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  phone: '15012345678',
  email: 'lunan96789@gmail.com',
  major: '计算机科学与技术',
  grade: '2022级',
  registerTime: '2025-06-23 21:30:20',
  lastLogin: '2025-07-05 15:20'
})

// 编辑状态
const editStatus = ref({
  nickname: false,
  avatar: false,
  phone: false,
  email: false,
  major: false,
  grade: false
})

// 临时存储
const tempInfo = ref({ ...userInfo.value })

// 密码修改弹窗
const passwordDialog = ref(false)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 切换编辑状态
const toggleEdit = (field) => {
  editStatus.value[field] = !editStatus.value[field]
  if (!editStatus.value[field]) {
    tempInfo.value[field] = userInfo.value[field]
  }
}

// 保存修改
const saveChange = (field) => {
  userInfo.value[field] = tempInfo.value[field]
  editStatus.value[field] = false
  ElMessage.success(`${{
    nickname: '昵称',
    phone: '手机号',
    email: '邮箱',
    major: '专业',
    grade: '年级'
  }[field]}修改成功！`)
}

// 上传头像
const handleAvatarUpload = (uploadFile) => {
  userInfo.value.avatar = uploadFile.url || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
  editStatus.value.avatar = false
  ElMessage.success('头像上传成功！')
}

// 修改密码
const changePassword = () => {
  if (!passwordForm.value.oldPassword) {
    ElMessage.warning('请输入原密码')
    return
  }
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.warning('两次密码输入不一致')
    return
  }
  passwordDialog.value = false
  ElMessage.success('密码修改成功，请重新登录！')
  passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
}

// 返回主页
const goToHome = () => {
  router.push('/home')
}
</script>

<template>
  <div class="profile-container">
    <!-- 返回按钮 -->
    <div class="back-section">
      <el-button
          type="text"
          @click="goToHome"
          class="back-btn"
      >
        ← 返回主页
      </el-button>
    </div>

    <div class="main-content">
      <!-- 个人信息卡片 -->
      <el-card class="profile-card">
        <div class="profile-header">
          <h2 class="profile-title">个人信息管理</h2>
        </div>

        <div class="profile-body">
          <!-- 头像区域 -->
          <div class="avatar-section">
            <el-avatar :src="userInfo.avatar" class="user-avatar"></el-avatar>
            <el-button
                type="text"
                @click="toggleEdit('avatar')"
                class="edit-avatar-btn"
            >
              修改头像
            </el-button>

            <!-- 头像上传 -->
            <div v-if="editStatus.avatar" class="avatar-uploader">
              <el-upload
                  action="#"
                  :show-file-list="false"
                  :on-success="handleAvatarUpload"
                  accept="image/*"
              >
                <el-button type="primary" size="small">选择图片</el-button>
              </el-upload>
            </div>
          </div>

          <!-- 信息表格 -->
          <el-table
              :data="[{
              label: '用户名',
              value: userInfo.username,
              editable: false
            }, {
              label: '昵称',
              value: userInfo.nickname,
              editable: true,
              field: 'nickname'
            }, {
              label: '手机号',
              value: userInfo.phone,
              editable: true,
              field: 'phone'
            }, {
              label: '邮箱',
              value: userInfo.email,
              editable: true,
              field: 'email'
            }, {
              label: '专业',
              value: userInfo.major,
              editable: true,
              field: 'major'
            }, {
              label: '年级',
              value: userInfo.grade,
              editable: true,
              field: 'grade'
            }, {
              label: '密码',
              value: '●●●●●●●●',
              editable: true,
              field: 'password'
            }]"
              border
              class="info-table"
              :show-header="false"
              :cell-style="{ padding: '12px 16px' }"
              :row-style="{ borderBottom: '1px solid #f0f2f5' }"
          >
            <el-table-column
                prop="label"
                width="120"
                :cell-style="{ backgroundColor: '#f5f7fa', fontWeight: 500, color: '#606266' }"
            />
            <el-table-column
                prop="value"
                :cell-style="{ color: '#303133' }"
            >
              <template #default="scope">
                <template v-if="scope.row.editable && editStatus[scope.row.field]">
                  <el-input
                      v-model="tempInfo[scope.row.field]"
                      placeholder="请输入内容"
                      size="small"
                      class="edit-input"
                  />
                </template>
                <template v-else>
                  {{ scope.row.value }}
                </template>
              </template>
            </el-table-column>
            <el-table-column
                width="80"
            >
              <template #default="scope">
                <template v-if="scope.row.editable">
                  <template v-if="scope.row.field === 'password'">
                    <el-button
                        type="text"
                        @click="passwordDialog = true"
                        size="small"
                        class="edit-btn"
                    >
                      修改密码
                    </el-button>
                  </template>
                  <template v-else>
                    <template v-if="!editStatus[scope.row.field]">
                      <el-button
                          type="text"
                          @click="toggleEdit(scope.row.field)"
                          size="small"
                          class="edit-btn"
                      >
                        编辑
                      </el-button>
                    </template>
                    <template v-else>
                      <div class="action-group">
                        <el-button
                            type="text"
                            @click="saveChange(scope.row.field)"
                            size="small"
                            class="save-btn"
                        >
                          保存
                        </el-button>
                        <el-button
                            type="text"
                            @click="toggleEdit(scope.row.field)"
                            size="small"
                            class="cancel-btn"
                        >
                          取消
                        </el-button>
                      </div>
                    </template>
                  </template>
                </template>
              </template>
            </el-table-column>
          </el-table>

          <!-- 账户信息 -->
          <div class="account-info">
            <div class="account-item">
              <span class="account-label">注册时间：</span>
              <span class="account-value">{{ userInfo.registerTime }}</span>
            </div>
            <div class="account-item">
              <span class="account-label">最后登录：</span>
              <span class="account-value">{{ userInfo.lastLogin }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 密码修改弹窗 -->
    <el-dialog
        title="修改密码"
        v-model="passwordDialog"
        width="400px"
        :close-on-click-modal="false"
    >
      <el-form class="password-form">
        <el-form-item label="原密码">
          <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              placeholder="请输入原密码"
          />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input
              v-model="passwordForm.newPassword"
              type="password"
              placeholder="请输入新密码"
          />
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input
              v-model="passwordForm.confirmPassword"
              type="password"
              placeholder="请再次输入新密码"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordDialog = false">取消</el-button>
        <el-button type="primary" @click="changePassword">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
/* 整体容器 */
.profile-container {
  width: 100vw;
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 20px;
  box-sizing: border-box;
}

/* 返回按钮 */
.back-section {
  margin-bottom: 20px;
}
.back-btn {
  color: #409EFF;
  font-size: 14px;
  padding: 0;
}

/* 主内容区域 */
.main-content {
  max-width: 800px;
  margin: 0 auto;
}

/* 个人信息卡片 */
.profile-card {
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.profile-header {
  padding: 20px 24px;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
  color: white;
}
.profile-title {
  margin: 0;
  font-size: 18px;
  font-weight: 500;
}

.profile-body {
  padding: 24px;
}

/* 头像区域 */
.avatar-section {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  gap: 16px;
}
.user-avatar {
  width: 70px;
  height: 70px;
  border: 3px solid #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.edit-avatar-btn {
  color: #409EFF;
  font-size: 14px;
}
.avatar-uploader {
  margin-top: 8px;
}

/* 信息表格 */
.info-table {
  --el-table-row-hover-bg-color: #f9fafc;
  --el-table-border-color: #e5e7eb;
  margin-bottom: 20px;
}
.edit-input {
  width: 100%;
  max-width: 300px;
}
.edit-btn {
  color: #409EFF;
  padding: 0;
}
.save-btn {
  color: #67C23A;
  padding: 0;
}
.cancel-btn {
  color: #F56C6C;
  padding: 0;
}
.action-group {
  display: flex;
  gap: 8px;
}

/* 账户信息 */
.account-info {
  display: flex;
  gap: 24px;
  padding: 16px;
  background-color: #f5f7fa;
  border-radius: 8px;
  font-size: 14px;
}
.account-label {
  color: #606266;
}
.account-value {
  color: #303133;
}

/* 密码表单 */
.password-form {
  margin-top: 10px;
}
</style>