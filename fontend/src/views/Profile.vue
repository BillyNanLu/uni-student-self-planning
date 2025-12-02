<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { userInfoService, userUpdatePwdService, userUpdateAvatarService, userUpdateInfoService } from '@/api/user.js'
import { useTokenStore } from '@/stores/token.js'

const router = useRouter()
const tokenStore = useTokenStore()

// 表单引用
const basicFormRef = ref(null)
const passwordFormRef = ref(null)

// 用户信息
const user = ref({
  id: '',
  username: '',
  avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  name: '',
  email: '',
  phone: '',
  grade: '',
  major: '',
  role: 0,
  create_time: '',
  last_login: ''
})

// 表单数据
const editForm = ref({
  avatar: '',
  name: '',
  email: '',
  phone: '',
  grade: '',
  major: '',

  oldPassword: '',
  password: '',
  rePassword: ''
})

// 头像修改表单
const avatarForm = ref({
  newAvatar: '',
  previewAvatar: '',
  file: null
})

// 同步数据
const syncToForm = () => {
  Object.assign(editForm.value, {
    avatar: user.value.avatar,
    name: user.value.name,
    email: user.value.email,
    phone: user.value.phone,
    grade: user.value.grade,
    major: user.value.major
  })

  avatarForm.value.previewAvatar = user.value.avatar
  avatarForm.value.newAvatar = ''
  avatarForm.value.file = null
}

// 获取用户信息
const getUserInfo = async () => {
  if (!tokenStore.token) return router.push('/login')
  try {
    const res = await userInfoService()
    if (res.code === 0 && res.data) {
      Object.assign(user.value, {
        id: res.data.id,
        username: res.data.username,
        avatar: res.data.avatar || user.value.avatar,
        name: res.data.name || '',
        email: res.data.email || '',
        phone: res.data.phone || '',
        grade: res.data.grade || '',
        major: res.data.major || '',
        role: res.data.role || 0,
        create_time: res.data.createTime || '',
        last_login: res.data.lastLogin || ''
      })
      syncToForm()
    }
  } catch (err) {
    console.error(err)
    ElMessage.error('获取用户信息失败，请重新登录')
    tokenStore.removeToken()
    router.push('/login')
  }
}

if (tokenStore.token) getUserInfo()
else router.push('/login')

// 工具
const maskPhone = (phone) => phone ? phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2') : '未绑定'

// 表单规则（增加 required）
const rules = {
  name: [
    { required: true, message: '昵称不能为空', trigger: 'blur' },
    { min: 2, max: 12, message: '昵称长度需在 2-12 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入合法邮箱', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入合法手机号', trigger: 'blur' }
  ],
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 5, max: 16, message: '长度需 5-16 位', trigger: 'blur' },
    { pattern: /^(?=.*[a-zA-Z])(?=.*\d)/, message: '需包含字母和数字', trigger: 'blur' }
  ],
  rePassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: (r, v, cb) => v !== editForm.value.password ? cb(new Error('两次密码不一致')) : cb(), trigger: 'blur' }
  ]
}

// 头像选择
const handleAvatarChange = (file) => {
  if (!file.raw.type.startsWith('image/')) return ElMessage.error('只能上传图片文件！')
  if (file.raw.size / 1024 / 1024 > 2) return ElMessage.error('图片大小不能超过 2MB！')

  const reader = new FileReader()
  reader.onload = e => {
    avatarForm.value.previewAvatar = e.target.result
    avatarForm.value.newAvatar = e.target.result
    avatarForm.value.file = file.raw
  }
  reader.readAsDataURL(file.raw)
  return false
}

// 修改头像
const submitAvatar = async () => {
  if (!avatarForm.value.file) return ElMessage.warning('请先选择头像')
  const formData = new FormData()
  formData.append('avatar', avatarForm.value.file)
  const res = await userUpdateAvatarService(formData)
  const newAvatar = res.data.data
  user.value.avatar = newAvatar
  editForm.value.avatar = newAvatar
  ElMessage.success('头像修改成功')
}

// 修改资料
const submitBasic = async () => {
  if (!basicFormRef.value) return
  try {
    await basicFormRef.value.validate()
    const res = await userUpdateInfoService({
      id: user.value.id,
      username: user.value.username,
      name: editForm.value.name,
      email: editForm.value.email,
      phone: editForm.value.phone,
      grade: editForm.value.grade,
      major: editForm.value.major
    })
    if (res.code === 0) {
      Object.assign(user.value, {
        name: editForm.value.name,
        email: editForm.value.email,
        phone: editForm.value.phone,
        grade: editForm.value.grade,
        major: editForm.value.major
      })
      ElMessage.success(res.message || '资料修改成功')
    } else {
      ElMessage.error(res.message || '资料修改失败')
    }
  } catch (err) {
    console.error(err)
    ElMessage.error(err?.response?.data?.message || '修改失败')
  }
}

// 修改密码
const submitPassword = async () => {
  if (!passwordFormRef.value) return
  try {
    await passwordFormRef.value.validate()
    const res = await userUpdatePwdService({
      old_pwd: editForm.value.oldPassword,
      new_pwd: editForm.value.password,
      re_pwd: editForm.value.rePassword
    })
    if (res.code === 0) {
      ElMessage.success(res.message || '密码修改成功，请重新登录')
      editForm.value.oldPassword = ''
      editForm.value.password = ''
      editForm.value.rePassword = ''
      tokenStore.removeToken()
      router.push('/login')
    } else {
      ElMessage.error(res.message || '密码修改失败')
    }
  } catch (err) {
    console.error(err)
    ElMessage.error(err?.response?.data?.message || '密码修改失败')
  }
}

// 当前 Tab
const activeTab = ref('basic')
</script>

<template>
  <el-card class="profile-card">
    <div class="profile-main">

      <!-- 左侧头像与基本信息（保持不变） -->
      <div class="profile-left">
        <img :src="user.avatar" class="avatar" />

        <h3>{{ user.name || '未设置昵称' }}</h3>
        <p>@{{ user.username }}</p>
        <el-tag :type="user.role === 1 ? 'primary' : 'success'" size="small">
          {{ user.role === 1 ? '管理员' : '学生' }}
        </el-tag>
      </div>

      <!-- 右侧 Tab 信息 -->
      <div class="profile-right">
        <el-tabs v-model="activeTab" type="card">
          <!-- 基本信息 -->
          <el-tab-pane label="基本信息" name="basic">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="昵称">{{ user.name || '未设置' }}</el-descriptions-item>
              <el-descriptions-item label="用户名">{{ user.username || '未知' }}</el-descriptions-item>
              <el-descriptions-item label="邮箱">{{ user.email || '未绑定' }}</el-descriptions-item>
              <el-descriptions-item label="手机号">{{ maskPhone(user.phone) }}</el-descriptions-item>
              <el-descriptions-item label="年级">{{ user.grade || '未填写' }}</el-descriptions-item>
              <el-descriptions-item label="专业">{{ user.major || '未填写' }}</el-descriptions-item>
              <el-descriptions-item label="注册时间">{{ user.create_time || '未知' }}</el-descriptions-item>
              <el-descriptions-item label="最近登录">{{ user.last_login || '未知' }}</el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>

          <!-- 修改头像（新增Tab） -->
          <el-tab-pane label="修改头像" name="avatar">
            <div class="avatar-edit-section">
              <div class="avatar-preview-container">
                <div class="avatar-preview">
                  <img :src="avatarForm.previewAvatar" alt="头像预览" class="preview-img" />
                </div>
              </div>

              <div class="avatar-upload-section">
                <!-- 纯前端头像选择 -->
                <el-upload
                    class="avatar-uploader"
                    accept="image/*"
                    :show-file-list="false"
                    :auto-upload="false"
                    :on-change="handleAvatarChange"
                >
                  <el-button type="primary">选择图片</el-button>
                </el-upload>

                <div class="avatar-tips">
                  <p class="text-sm text-gray-500">支持JPG/PNG格式，建议尺寸1:1，大小不超过2MB</p>
                </div>

                <div class="avatar-actions mt-4">
                  <el-button
                      type="primary"
                      @click="submitAvatar"
                      :disabled="!avatarForm.newAvatar"
                  >
                    应用头像
                  </el-button>
                  <el-button
                      @click="() => { avatarForm.previewAvatar = user.avatar; avatarForm.newAvatar = ''; avatarForm.file = null }"
                      v-if="avatarForm.newAvatar"
                  >
                    取消
                  </el-button>
                </div>
              </div>
            </div>
          </el-tab-pane>

          <!-- 修改资料 -->
          <el-tab-pane label="修改资料" name="profile">
            <el-form
                ref="basicFormRef"
                :model="editForm"
                :rules="rules"
                label-width="100px"
                class="profile-form"
            >
              <el-form-item label="昵称" prop="name">
                <el-input v-model="editForm.name" placeholder="请输入昵称" />
              </el-form-item>

              <el-form-item label="邮箱" prop="email">
                <el-input v-model="editForm.email" placeholder="请输入邮箱" />
              </el-form-item>

              <el-form-item label="手机号" prop="phone">
                <el-input v-model="editForm.phone" placeholder="请输入手机号" />
              </el-form-item>

              <el-form-item label="年级">
                <el-input v-model="editForm.grade" placeholder="请输入年级" />
              </el-form-item>

              <el-form-item label="专业">
                <el-input v-model="editForm.major" placeholder="请输入专业" />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitBasic">保存修改</el-button>
                <el-button @click="syncToForm">取消</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>

          <!-- 修改密码 -->
          <el-tab-pane label="修改密码" name="password">
            <el-form
                ref="passwordFormRef"
                :model="editForm"
                :rules="rules"
                label-width="100px"
                class="profile-form"
            >
              <el-form-item label="原密码" prop="oldPassword">
                <el-input type="password" v-model="editForm.oldPassword" placeholder="请输入原密码" />
              </el-form-item>

              <el-form-item label="新密码" prop="password">
                <el-input type="password" v-model="editForm.password" placeholder="请输入新密码" />
              </el-form-item>

              <el-form-item label="确认密码" prop="rePassword">
                <el-input type="password" v-model="editForm.rePassword" placeholder="请确认新密码" />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitPassword">保存修改</el-button>
                <el-button @click="() => { editForm.oldPassword = ''; editForm.password = ''; editForm.rePassword = '' }">取消</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </el-card>
</template>

<style scoped>
.profile-card {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 12px;
}
.profile-main {
  display: flex;
  gap: 30px;
  flex-wrap: wrap;
}
.profile-left {
  flex: 0 0 200px;
  text-align: center;
  padding-top: 20px;
}
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 12px;
  object-fit: cover;
  margin-bottom: 10px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}
.profile-right {
  flex: 1;
  min-width: 300px;
}
.profile-form {
  padding: 20px 0;
}
.el-descriptions {
  --el-descriptions-item-padding: 12px 16px;
}

/* 头像修改区域样式 */
.avatar-edit-section {
  padding: 20px;
}
.avatar-preview-container {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}
.avatar-preview {
  width: 180px;
  height: 180px;
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f9fafb;
}
.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.avatar-upload-section {
  text-align: center;
}
.avatar-tips {
  margin-top: 10px;
}
.avatar-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}

@media (max-width: 768px) {
  .profile-main {
    flex-direction: column;
    align-items: center;
  }
}
</style>