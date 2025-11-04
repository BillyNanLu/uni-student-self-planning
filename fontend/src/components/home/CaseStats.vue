<script setup>
import defaultAvatar from '@/assets/default.png';
</script>

<template>
  <div class="case-section">
    <h3 class="section-title">学长学姐的选择</h3>
    <el-row :gutter="20" class="case-cards">
      <el-col :xs="24" :sm="12" :md="8" v-for="item in caseList" :key="item.id">
        <el-card class="case-card">
          <div class="case-avatar" :style="{ backgroundImage: `url(${item.avatar})` }"></div>
          <h4 class="case-name">{{ item.name }}</h4>
          <p class="case-direction">{{ item.direction }}</p>
          <p class="case-result">{{ item.result }}</p>
        </el-card>
      </el-col>
    </el-row>
    <div class="data-stats">
      <el-statistic
          title="已有规划用户"
          :value="12580"
          suffix="+"
          prefix="&#128101;"
          :value-style="{ color: '#409EFF' }"
      />
      <el-statistic
          title="成功上岸率"
          :value="78"
          suffix="%"
          prefix="📈"
          :value-style="{ color: '#67C23A' }"
      />
      <el-statistic
          title="资源库数量"
          :value="5000"
          suffix="+"
          prefix="📚"
          :value-style="{ color: '#E6A23C' }"
      />
    </div>
  </div>
</template>

<style scoped>
.case-section {
  padding: 40px 0;
  background-color: var(--light-bg);
}

.case-card {
  border: none;
  text-align: center;
}

.case-avatar {
  width: 100px;
  height: 100px;
  margin: 0 auto 20px;
  border-radius: 50%;
  background-size: cover;
  background-position: center;
  border: 4px solid #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.case-name {
  font-size: 18px;
  margin-bottom: 10px;
  color: #2c3e50;
}

.case-direction {
  color: var(--primary-color);
  margin-bottom: 10px;
  font-weight: 500;
}

.case-result {
  color: #67C23A;
  font-weight: 500;
}

.data-stats {
  display: flex;
  justify-content: center;
  gap: 60px;
  margin-top: 40px;
  flex-wrap: wrap;
}
</style>