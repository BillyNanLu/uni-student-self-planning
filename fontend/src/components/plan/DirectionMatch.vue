<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

const router = useRouter();

const props = defineProps({
  matchRate: { type: Object, required: true }, // {考研:XX, 考公:XX, 就业:XX}
  recommend: { type: String, required: true }, // 系统推荐方向：考研
  preferredDirection: { type: String, required: true }, // 用户选择方向：考公
  confirmStatus: { type: Number, default: 0 }, // 0未确认、1采纳系统、2拒绝系统、3再想想
  finalDirection: { type: String, default: '' } // 用户最终确认的方向
});

const emit = defineEmits(['confirmDirection']);

// 转换英文key为中文显示
const directionMap = {
  kaoyan: '考研',
  kaogong: '考公',
  jiuye: '就业',
  '考研': '考研',
  '考公': '考公',
  '就业': '就业'
};

// 处理matchRate的key转换
const processedMatchRate = computed(() => {
  const result = {};
  Object.keys(props.matchRate).forEach(key => {
    const displayKey = directionMap[key] || key;
    result[displayKey] = props.matchRate[key];
  });
  return result;
});

// 显示用的方向名称
const systemDirection = computed(() => directionMap[props.recommend] || props.recommend);
const userDirection = computed(() => directionMap[props.preferredDirection] || props.preferredDirection);

// 不同方向对应不同进度条颜色
const getColor = (dir) => {
  const colorMap = {
    '考研': '#F56C6C',
    '考公': '#409EFF',
    '就业': '#67C23A'
  };
  return colorMap[dir] || '#909399';
};

// 获取进度条样式（高亮最终确认的方向）
const getProgressStyle = (dir) => {
  if (props.finalDirection === dir) {
    return {
      border: '2px solid #409EFF',
      borderRadius: '10px'
    };
  }
  return {};
};

// 用户确认方向选择
const confirmChoice = (status) => {
  let finalDir = '';
  let message = '';

  switch(status) {
    case 1: // 采纳系统推荐
      finalDir = systemDirection.value;
      message = `已采纳系统推荐方向：${finalDir}`;
      break;
    case 2: // 坚持自己选择
      finalDir = userDirection.value;
      message = `已确认你的选择方向：${finalDir}`;
      break;
    case 3: // 再想想
      finalDir = '';
      message = '你可以稍后再做决定';
      break;
  }

  ElMessage.success(message);
  emit('confirmDirection', {
    status,
    finalDirection: finalDir
  });
};

// 重新测评（返回测评页）
const reEvaluate = () => {
  router.push('/planning/evaluate')
}
</script>

<template>
  <div class="direction-match-container">
    <div class="header-section">
      <h3 class="section-title">发展方向匹配度</h3>

      <div class="action-buttons">
        <el-button @click="reEvaluate">重新测评</el-button>
      </div>
    </div>

    <!-- 匹配度进度条 -->
    <div class="match-chart">
      <div
          v-for="(rate, dir) in processedMatchRate"
          :key="dir"
          class="progress-container"
          :style="getProgressStyle(dir)"
      >
        <div class="progress-bar" :style="{ background: getColor(dir), width: rate + '%' }">
          <span class="dir-name">{{ dir }}</span>
          <span class="dir-score">{{ rate }}分</span>
        </div>
      </div>
    </div>

    <!-- 推荐方向信息 -->
    <div class="direction-tags">
      <el-tag type="info" size="small" class="system-tag">系统推荐方向：{{ systemDirection }}</el-tag>
      <el-tag type="warning" size="small" class="user-tag">你的选择方向：{{ userDirection }}</el-tag>
    </div>

    <!-- 方向对比结果：一致时显示匹配成功 -->
    <div class="direction-result" v-if="systemDirection === userDirection">
      <el-icon class="success-icon"><Check /></el-icon>
      <p class="success-text">
        非常棒！你选择的方向与系统推荐高度匹配！
        <br>
        用户最终确认的发展方向：<span class="final-dir">{{ systemDirection }}</span>
      </p>
      <hr>
    </div>

    <!-- 方向不一致时的操作区 -->
    <div class="direction-conflict" v-else>
      <el-divider content-position="left">方向选择建议</el-divider>
      <p class="conflict-text">
        你选择的<span class="user-dir">{{ userDirection }}</span>与系统推荐的<span class="system-dir">{{ systemDirection }}</span>不一致
      </p>
      <div class="action-buttons">
        <el-button
            type="primary"
            :class="{ 'is-disabled': props.confirmStatus === 1 }"
            @click="confirmChoice(1)"
        >
          <span v-if="props.confirmStatus === 1">已采纳系统推荐（{{ systemDirection }}）</span>
          <span v-else>采纳系统推荐（{{ systemDirection }}）</span>
        </el-button>

        <el-button
            type="success"
            :class="{ 'is-disabled': props.confirmStatus === 2 }"
            @click="confirmChoice(2)"
        >
          <span v-if="props.confirmStatus === 2">已坚持自己选择（{{ userDirection }}）</span>
          <span v-else>坚持自己选择（{{ userDirection }}）</span>
        </el-button>

        <el-button
            type="default"
            @click="confirmChoice(3)"
        >
          再想想
        </el-button>
      </div>

      <!-- 最终确认结果：不一致时显示 -->
      <div class="final-confirmation" v-if="props.finalDirection">
        <el-divider content-position="left">最终确认</el-divider>
        <p class="final-text">
          用户最终确认的发展方向：<span class="final-dir">{{ props.finalDirection }}</span>
        </p>
      </div>
      <div class="final-confirmation" v-else>
        <el-divider content-position="left">最终确认</el-divider>
        <p class="final-text">
          请选择最终发展方向
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.direction-match-container {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.header-section {
  display: flex;
  justify-content: space-between; /* 左右对齐 */
  width: 100%;
}

.section-title {
  font-size: 20px;
  color: #2c3e50;
  margin: 0 0 20px 0;
  font-weight: 600;
}

.match-chart {
  margin-bottom: 20px;
}

.progress-container {
  margin-bottom: 10px;
  height: 30px;
  background: #f0f2f5;
  border-radius: 15px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 15px;
  color: #fff;
  font-weight: 500;
  transition: all 0.3s ease;
}

.dir-name {
  font-size: 14px;
}

.dir-score {
  font-size: 14px;
}

.direction-tags {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.system-tag {
  background-color: #e6f7ff;
  color: #409EFF;
}

.user-tag {
  background-color: #fdf6ec;
  color: #E6A23C;
}

.direction-result {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px;
  background-color: #f0f9ff;
  border-radius: 8px;
  margin-bottom: 20px;
}

.success-icon {
  color: #67C23A;
  font-size: 20px;
}

.success-text {
  margin: 0;
  color: #67C23A;
  font-weight: 500;
}

.direction-conflict {
  padding: 16px;
  background-color: #fef0f0;
  border-radius: 8px;
  margin-bottom: 20px;
}

.conflict-text {
  color: #E6A23C;
  margin: 0 0 16px 0;
  line-height: 1.6;
}

.user-dir {
  color: #409EFF;
  font-weight: 600;
}

.system-dir {
  color: #F56C6C;
  font-weight: 600;
}

.action-buttons {
  display: flex;
  gap: 12px;
}

.el-button.is-disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.final-confirmation {
  padding: 16px;
  background-color: #f5fafe;
  border-radius: 8px;
}

.final-text {
  margin: 0;
  line-height: 1.6;
}

.final-dir {
  color: #409EFF;
  font-weight: 600;
  font-size: 16px;
}
</style>