<script setup>
import { User, Search, Calendar } from '@element-plus/icons-vue'

const rulesList = [
  {
    id: 1,
    title: '知己：明确自身定位',
    desc: '通过测评分析你的兴趣、能力与适配方向（考研/考公/就业匹配度）',
    icon: User
  },
  {
    id: 2,
    title: '知彼：了解外部环境',
    desc: '结合热门职业需求与考试节点，匹配适合你的发展路径',
    icon: Search
  },
  {
    id: 3,
    title: '行动：制定落地计划',
    icon: Calendar,
    desc: '生成阶段性时间安排，包含学习目标、资源推荐与考试提醒'
  }
]
</script>

<template>
  <div class="planning-rules">
    <h3 class="section-title">规划核心规则</h3>
    <div class="rules-cards">
      <el-card class="rule-card" v-for="rule in rulesList" :key="rule.id">
        <div class="rule-icon">
          <el-icon :size="36"><component :is="rule.icon" /></el-icon>
        </div>
        <h4 class="rule-title">{{ rule.title }}</h4>
        <p class="rule-desc">{{ rule.desc }}</p>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.planning-rules {
  padding: 20px 0;
}
.section-title {
  text-align: center;
  font-size: 24px;
  color: #2c3e50;
  margin-bottom: 30px;
  position: relative;
  &::after {
    content: "";
    position: absolute;
    bottom: -10px;
    left: 50%;
    transform: translateX(-50%);
    width: 60px;
    height: 3px;
    background-color: #409EFF;
  }
}
.rules-cards {
  display: flex;
  gap: 30px;
  flex-wrap: wrap;
  justify-content: center;
}
.rule-card {
  width: 300px;
  border: none;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  text-align: center;
  padding: 20px;
}
.rule-icon {
  color: #409EFF;
  margin-bottom: 16px;
}
.rule-title {
  font-size: 18px;
  margin-bottom: 12px;
  color: #2c3e50;
}
.rule-desc {
  color: #606266;
  font-size: 14px;
}
</style>