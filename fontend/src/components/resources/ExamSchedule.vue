<script setup>
import { Notebook, Promotion, Ticket } from '@element-plus/icons-vue'

const examList = [
  {
    id: 1,
    name: '2026年考研初试',
    time: '2025年12月底',
    direction: '考研',
    note: '具体日期以教育部通知为准，科目包括政治、英语、专业课',
    icon: Notebook,
    color: '#F56C6C' // 红色：考研
  },
  {
    id: 2,
    name: '2026年国考报名',
    time: '2025年10月中旬',
    direction: '考公',
    note: '笔试时间约11月底，报名网址：国家公务员局官网',
    icon: Promotion,
    color: '#409EFF' // 蓝色：考公
  },
  {
    id: 3,
    name: '英语六级考试',
    time: '2025年12月/2026年6月',
    direction: '全方向',
    note: '报名提前3个月，需通过学校教务系统报名',
    icon: Ticket,
    color: '#67C23A' // 绿色：通用
  },
  {
    id: 4,
    name: '2026年春招（互联网）',
    time: '2026年2-3月',
    direction: '就业',
    note: '大厂校招高峰期，建议提前准备简历与笔试',
    icon: Ticket,
    color: '#E6A23C' // 橙色：就业
  },
  {
    id: 5,
    name: '计算机二级考试',
    time: '2026年3月/6月',
    direction: '就业/考研',
    note: '适合IT相关方向，证书可丰富简历',
    icon: Notebook,
    color: '#67C23A'
  }
]
</script>

<template>
  <div class="exam-schedule">
    <h3 class="section-title">未来一年考试安排（2025-2026）</h3>
    <el-timeline>
      <el-timeline-item
          v-for="(exam, index) in examList"
          :key="exam.id"
          :timestamp="exam.time"
          :placement="index % 2 === 0 ? 'top' : 'bottom'"
          :icon="exam.icon"
          :color="exam.color"
      >
        <el-card>
          <h4 class="exam-title">{{ exam.name }}</h4>
          <p class="exam-direction">适用方向：{{ exam.direction }}</p>
          <p class="exam-note">备注：{{ exam.note }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
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
.exam-note {
  color: #606266;
  font-size: 14px;
}
</style>