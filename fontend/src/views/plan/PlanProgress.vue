<script setup>
import { ref, onMounted, computed } from 'vue'
import useUserInfoStore from '@/stores/userInfo'
import { ElMessage } from 'element-plus'

// 导入子组件
import OverviewCard from '@/components/plan/OverviewCard.vue'
import StagePathCard from '@/components/plan/StagePathCard.vue'
import ExamTrackingCard from '@/components/plan/ExamTrackingCard.vue'
import TodoTaskCard from '@/components/plan/TodoTaskCard.vue'

// 导入接口（后续对接后端）
// import { getPlanningProgressService } from '@/api/progress'

// 用户信息（从Pinia获取）
const userInfoStore = useUserInfoStore()
const userName = computed(() => userInfoStore.info.username || '用户')
const userAvatar = ref('https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png') // 默认头像

// 规划核心数据（模拟，后续从接口获取）
const totalProgress = ref(30) // 总进度百分比
const currentDirection = ref('考研 - 计算机科学与技术') // 当前方向
const currentStage = ref('基础阶段（3-6月）') // 当前阶段

// 阶段列表（用于进度总览）
const stageList = ref([
  { name: '基础阶段', progress: 70, status: '进行中' },
  { name: '强化阶段', progress: 0, status: '未开始' },
  { name: '冲刺阶段', progress: 0, status: '未开始' }
])

// 阶段学习路径详情（按阶段拆分任务）
const stageDetails = ref([
  {
    stage: '基础阶段（3-6月）',
    tasks: [
      { id: 1, name: '完成《数据结构》教材一轮复习', completed: true },
      { id: 2, name: '背诵考研英语5500单词（第一遍）', completed: true },
      { id: 3, name: '刷完高数前5章习题', completed: false },
      { id: 4, name: '整理计算机组成原理笔记', completed: false }
    ],
    resources: [
      { id: 1, name: '数据结构基础课视频', url: '/resources/1' },
      { id: 2, name: '考研英语单词APP推荐', url: '/resources/2' }
    ]
  },
  {
    stage: '强化阶段（7-9月）',
    tasks: [
      { id: 5, name: '专业课真题刷第一遍', completed: false },
      { id: 6, name: '英语阅读真题（2010-2020）', completed: false }
    ],
    resources: [
      { id: 3, name: '计算机考研真题集', url: '/resources/3' }
    ]
  }
])

// 考试节点跟踪数据（带倒计时和备考进度）
const examList = ref([
  {
    id: 1,
    name: '考研初试',
    time: '2025年12月28日',
    countdown: '270天',
    prepareProgress: 20, // 备考进度
    checklist: [
      { id: 1, name: '完成3轮专业课复习', completed: false },
      { id: 2, name: '政治肖四肖八刷题', completed: false }
    ]
  },
  {
    id: 2,
    name: '英语六级',
    time: '2025年12月14日',
    countdown: '256天',
    prepareProgress: 40,
    checklist: [
      { id: 3, name: '刷完10套真题', completed: true },
      { id: 4, name: '背诵作文模板', completed: false }
    ]
  }
])

// 待办任务列表（近期任务）
const taskList = ref([
  { id: 1, content: '复习高数第6章', date: '2025-03-20', completed: false },
  { id: 2, content: '背50个英语单词', date: '2025-03-20', completed: true },
  { id: 3, content: '看数据结构视频第8节', date: '2025-03-21', completed: false }
])

// 页面加载时获取进度数据
onMounted(async () => {
  try {
    // 实际项目中从接口获取数据
    // const res = await getPlanningProgressService()
    // if (res.code === 0) {
    //   totalProgress.value = res.data.totalProgress
    //   currentStage.value = res.data.currentStage
    //   // ... 其他数据赋值
    // }
  } catch (error) {
    ElMessage.error('获取规划进度失败，请重试')
  }
})

// 切换任务完成状态
const toggleTaskStatus = (taskId) => {
  const task = taskList.value.find(t => t.id === taskId)
  if (task) task.completed = !task.completed
  // 实际项目中需调用接口同步到后端
}

// 添加新任务
const addNewTask = (taskContent) => {
  const newTask = {
    id: Date.now(), // 临时用时间戳当ID
    content: taskContent,
    date: new Date().toISOString().split('T')[0], // 今天日期
    completed: false
  }
  taskList.value.unshift(newTask) // 添加到列表头部
  // 实际项目中需调用接口保存到后端
}

</script>

<template>
  <div class="progress-page">
    <!-- 顶部导航与用户信息 -->
    <div class="page-header">
      <h2 class="page-title">我的规划进度中心</h2>
      <div class="user-info">
        <el-avatar :src="userAvatar" class="user-avatar" />
        <span class="user-name">{{ userName }}</span>
        <el-tag type="info" class="direction-tag">{{ currentDirection }}</el-tag>
      </div>
    </div>

    <!-- 核心内容区 -->
    <div class="progress-content">
      <!-- 1. 规划概览（进度总览） -->
      <OverviewCard
          :totalProgress="totalProgress"
          :currentStage="currentStage"
          :stageList="stageList"
      />

      <!-- 2. 阶段学习路径 -->
      <StagePathCard :stageDetails="stageDetails" />

      <!-- 3. 考试节点跟踪 -->
      <ExamTrackingCard :examList="examList" />

      <!-- 4. 待办任务提醒 -->
      <TodoTaskCard
          :taskList="taskList"
          @toggleTask="toggleTaskStatus"
          @addTask="addNewTask"
      />
    </div>
  </div>
</template>

<style scoped>
.progress-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f9fafc;
  min-height: calc(100vh - 60px);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.page-title {
  font-size: 24px;
  color: #2c3e50;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 40px;
  height: 40px;
}

.user-name {
  font-size: 16px;
  color: #2c3e50;
}

.direction-tag {
  font-size: 12px;
}

.progress-content {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 两列布局 */
  gap: 20px;
}

/* 响应式适配：小屏幕时改为单列 */
@media (max-width: 768px) {
  .progress-content {
    grid-template-columns: 1fr;
  }
}
</style>