<script setup>
const props = defineProps({
  matchRate: { type: Object, required: true }, // {考研:80, 考公:50, 就业:30}
  recommend: { type: String, required: true } // 考研
})

// 不同方向对应不同进度条颜色
const getColor = (dir) => {
  const colorMap = { 考研: '#F56C6C', 考公: '#409EFF', 就业: '#67C23A' };
  return colorMap[dir] || '#909399';
}
</script>

<template>
  <el-card class="match-card">
    <h3 class="card-title">发展方向匹配度</h3>
    <div class="match-chart">
      <el-progress
          v-for="(rate, dir) in matchRate"
          :key="dir"
          :text-inside="true"
          :stroke-width="20"
          :percentage="rate"
          :color="getColor(dir)"
          class="progress-item"
      >
        <span class="dir-name">{{ dir }}</span>
      </el-progress>
    </div>
    <div class="recommend-tag">
      <el-tag type="success" size="large">推荐方向：{{ recommend }}</el-tag>
    </div>
  </el-card>
</template>

<style scoped>

</style>