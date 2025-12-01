<script setup>
import { ref, onMounted, computed } from 'vue'
import { Notebook, Promotion, Ticket, OfficeBuilding } from '@element-plus/icons-vue'
import { getExamListService } from '@/api/resource.js'

const examList = ref([])
const showAll = ref(false) // 控制是否显示全部
const isCollapsed = ref(false) // 控制整体内容的收起/展开

// direction 映射 icon + color
const directionMap = {
  '考研': { icon: Notebook, color: '#F56C6C' },
  '考公': { icon: Promotion, color: '#409EFF' },
  '就业': { icon: OfficeBuilding, color: '#E6A23C' },
  '全方向': { icon: Ticket, color: '#67C23A' }
}

// 计算属性：根据showAll状态返回要显示的列表
const displayExamList = computed(() => {
  if (showAll.value || examList.value.length <= 5) {
    return examList.value
  }
  return examList.value.slice(0, 5)
})

// 初始化
const loadExams = async () => {
  const res = await getExamListService()
  if (res.code === 0 && Array.isArray(res.data)) {
    examList.value = res.data.map(item => ({
      id: item.id,
      name: item.name,
      time: item.date,
      direction: item.directionName,
      note: item.description,
      url: item.link,
      icon: directionMap[item.directionName]?.icon || Notebook,
      color: directionMap[item.directionName]?.color || '#909399'
    }))
  }
}

// 切换显示状态
const toggleShowAll = () => {
  showAll.value = !showAll.value
}

// 切换整体折叠状态
const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
}

onMounted(() => {
  loadExams()
})

</script>

<template>
  <div class="exam-schedule">
    <!-- 可点击的标题，用文字符号表示折叠状态 -->
    <h3
        class="section-title"
        @click="toggleCollapse"
        style="cursor: pointer; display: flex; align-items: center; justify-content: space-between;"
    >
      <span>未来一年考试安排（2025-2026）</span>
      <span style="font-size: 18px; ">{{ isCollapsed ? '▲' : '▼' }}</span>
    </h3>

    <!-- 内容区域，根据折叠状态显示/隐藏 -->
    <div v-show="!isCollapsed">
      <el-timeline>
        <el-timeline-item
            v-for="(exam, index) in displayExamList"
            :key="exam.id"
            :timestamp="exam.time"
            :placement="index % 2 === 0 ? 'top' : 'bottom'"
            :icon="exam.icon"
            :color="exam.color"
        >
          <el-card>
            <h4 class="exam-title">{{ exam.name }}</h4>
            <p class="exam-direction">适用方向：{{ exam.direction }}</p>
            <a
                class="exam-link"
                :href="exam.url"
                target="_blank"
                rel="noopener noreferrer"
            >
              参考网址：{{ exam.url }}
            </a>
            <p class="exam-note">备注：{{ exam.note }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <!-- 加载更多按钮 -->
      <div v-if="examList.length > 5" class="load-more-container">
        <el-button
            type="primary"
            @click="toggleShowAll"
            size="default"
            class="load-more-btn"
        >
          {{ showAll ? '收起' : '加载更多' }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.exam-schedule {
  padding: 20px 0;
}
.el-timeline {
  margin-top: 20px;
  padding-left: 20px;
}
.el-timeline-item {
  margin-bottom: 30px;
}
.exam-title {
  font-size: 18px;
  margin-bottom: 8px;
  color: #2c3e50;
}
.exam-direction {
  color: #409EFF;
  margin-bottom: 8px;
  font-size: 14px;
}
.exam-link {
  color: #67C23A;
  margin-bottom: 8px;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer; /* 鼠标悬停显示手型 */
  transition: all 0.2s;
}
.exam-link:hover {
  color: #529E28;
  text-decoration: underline; /* 悬停显示下划线 */
}
.exam-note {
  color: #606266;
  font-size: 14px;
}

/* 加载更多按钮样式 */
.load-more-container {
  text-align: center;
  margin-top: 30px;
}
.load-more-btn {
  background-color: #67C23A;
  border-color: #67C23A;
}
.load-more-btn:hover {
  background-color: #529E28;
  border-color: #529E28;
}
</style>