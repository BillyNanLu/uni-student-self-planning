<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { Calendar, Clock, Document } from '@element-plus/icons-vue';
import { ElEmpty } from 'element-plus';

// 接收父组件传入的考试列表（从AI生成结果中获取）
const props = defineProps({
  examList: {
    type: Array,
    default: () => [] // 格式示例：[{id:1, name:'考研初试', time:'2025年12月底', note:'...'}, ...]
  }
});

const router = useRouter();

// 格式化考试时间显示（如“2025年12月底”→“2025-12 月底”）
const formatExamTime = (time) => {
  return time.replace('年', '-').replace('月', '');
};

// 根据考试名称判断方向（考研/考公/就业）
const getExamDirection = (examName) => {
  if (examName.includes('考研')) return '考研方向';
  if (examName.includes('国考') || examName.includes('省考')) return '考公方向';
  if (examName.includes('春招') || examName.includes('秋招')) return '就业方向';
  return '通用考试';
};

// 根据方向设置标签类型
const getExamTypeTag = (examName) => {
  const direction = getExamDirection(examName);
  const tagTypeMap = {
    '考研方向': 'danger',
    '考公方向': 'primary',
    '就业方向': 'success',
    '通用考试': 'info'
  };
  return tagTypeMap[direction];
};

// 跳转到对应备考资源页
const handleGoToResource = (examName) => {
  // 携带考试名称作为参数，便于资源页筛选内容
  router.push(`/resources?type=exam&name=${encodeURIComponent(examName)}`);
};

// 刷新考试列表（实际项目中可重新调用接口）
const handleRefresh = () => {
  // 模拟刷新逻辑
  console.log('刷新考试列表...');
};
</script>

<template>
  <el-card class="exam-prediction-card">
    <div class="card-header">
      <el-icon class="header-icon"><Calendar /></el-icon>
      <h3 class="card-title">关键考试节点预测</h3>
      <p class="card-subtitle">基于你的发展方向，推荐以下考试及备考建议</p>
    </div>

    <!-- 考试列表 -->
    <div class="exam-list">
      <el-timeline>
        <el-timeline-item
            v-for="(exam, index) in examList"
            :key="exam.id"
            :timestamp="formatExamTime(exam.time)"
            placement="top"
            :icon="Clock"
            color="#409EFF"
        >
          <el-card class="exam-item-card">
            <div class="exam-info">
              <h4 class="exam-name">{{ exam.name }}</h4>
              <el-tag :type="getExamTypeTag(exam.name)">{{ getExamDirection(exam.name) }}</el-tag>
            </div>
            <p class="exam-countdown" v-if="exam.countdown">
              <span class="countdown-text">倒计时：</span>
              <span class="countdown-value">{{ exam.countdown }}</span>
            </p>
            <p class="exam-note"><strong>备考建议：</strong>{{ exam.note }}</p>
            <el-button
                type="text"
                class="exam-resource-btn"
                @click="handleGoToResource(exam.name)"
            >
              <Document /> 查看相关备考资源
            </el-button>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>

    <!-- 无考试数据时的占位提示 -->
    <div v-if="examList.length === 0" class="empty-exam">
      <el-empty
          description="暂无匹配的考试信息"
          :image="ElEmpty.PRESENTED_IMAGE_SIMPLE"
      >
        <el-button type="primary" @click="handleRefresh">刷新考试列表</el-button>
      </el-empty>
    </div>
  </el-card>
</template>

<style scoped>
.exam-prediction-card {
  border-radius: 12px;
  border: none;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.card-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 0;
  border-bottom: 1px solid #f0f0f0;
}

.header-icon {
  font-size: 28px;
  color: #409EFF;
  margin-bottom: 12px;
}

.card-title {
  font-size: 20px;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.card-subtitle {
  font-size: 14px;
  color: #606266;
  margin: 0;
}

.exam-list {
  padding: 20px;
}

.el-timeline {
  padding-left: 20px;
}

.el-timeline-item {
  margin-bottom: 24px;
}

.exam-item-card {
  border: none;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.05);
  border-radius: 8px;
}

.exam-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.exam-name {
  font-size: 16px;
  color: #2c3e50;
  margin: 0;
}

.exam-countdown {
  margin: 8px 0;
  padding: 6px 10px;
  background-color: #f0f9ff;
  border-radius: 4px;
  font-size: 14px;
}

.countdown-text {
  color: #606266;
}

.countdown-value {
  color: #F56C6C;
  font-weight: 500;
}

.exam-note {
  margin: 12px 0;
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
}

.exam-resource-btn {
  padding: 0;
  color: #409EFF;
  font-size: 14px;
}

.empty-exam {
  padding: 40px 20px;
  text-align: center;
}
</style>