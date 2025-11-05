<script setup>
import { ref } from 'vue'
import { Fries } from '@element-plus/icons-vue'

// 激活的标签页
const activeTab = ref('postgraduate')

// 考研方向数据
const postgraduateList = [
  {
    id: 1,
    name: '计算机科学与技术',
    detail: '适合逻辑思维强、对编程/人工智能感兴趣的学生，就业方向包括算法工程师、后端开发',
    hot: '★★★★★'
  },
  {
    id: 2,
    name: '临床医学',
    detail: '需具备扎实的医学基础，培养方向为医生、医学研究员，就业多在医院及科研机构',
    hot: '★★★★☆'
  },
  {
    id: 3,
    name: '法律（非法学）',
    detail: '跨专业报考热门，适合对法律行业感兴趣的学生，就业方向为律师、法务、法官',
    hot: '★★★★☆'
  }
]

// 考公方向数据
const civilServantList = [
  {
    id: 1,
    name: '国考-税务局',
    detail: '招录人数多，专业限制宽（经济学、法学、管理学等），工作稳定性强',
    competition: '约80:1'
  },
  {
    id: 2,
    name: '省考-市场监管局',
    detail: '负责市场监管执法，适合沟通能力强的学生，招录专业多为法学、工商管理',
    competition: '约50:1'
  }
]

// 就业方向数据
const jobList = [
  {
    id: 1,
    name: '人工智能工程师',
    detail: '需掌握机器学习、深度学习技能，热门领域包括自动驾驶、智能推荐',
    salary: '15k-25k/月'
  },
  {
    id: 2,
    name: '新能源系统工程师',
    detail: '聚焦光伏、储能领域，适合能源、电气专业学生，政策支持力度大',
    salary: '12k-20k/月'
  },
  {
    id: 3,
    name: '教育科技产品经理',
    detail: '连接教育与科技，需懂用户需求与产品设计，适合教育学/计算机交叉背景',
    salary: '10k-18k/月'
  }
]
</script>

<template>
  <div class="hot-careers">
    <h3 class="section-title">热门规划方向参考</h3>
    <el-tabs v-model="activeTab" type="card" class="careers-tabs">
      <!-- 考研方向 -->
      <el-tab-pane label="考研" name="postgraduate">
        <el-card class="career-card" v-for="item in postgraduateList" :key="item.id">
          <h4 class="career-name">{{ item.name }}</h4>
          <p class="career-detail">{{ item.detail }}</p>
          <p class="career-hot">热度：{{ item.hot }} <el-icon><Fries /></el-icon></p>
        </el-card>
      </el-tab-pane>

      <!-- 考公方向 -->
      <el-tab-pane label="考公" name="civilServant">
        <el-card class="career-card" v-for="item in civilServantList" :key="item.id">
          <h4 class="career-name">{{ item.name }}</h4>
          <p class="career-detail">{{ item.detail }}</p>
          <p class="career-hot">竞争比：{{ item.competition }}</p>
        </el-card>
      </el-tab-pane>

      <!-- 就业方向 -->
      <el-tab-pane label="就业" name="job">
        <el-card class="career-card" v-for="item in jobList" :key="item.id">
          <h4 class="career-name">{{ item.name }}</h4>
          <p class="career-detail">{{ item.detail }}</p>
          <p class="career-salary">平均起薪：{{ item.salary }}</p>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
.hot-careers {
  padding: 20px 0;
}
.careers-tabs {
  margin-top: 20px;
}
.career-card {
  margin-bottom: 20px;
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}
.career-name {
  font-size: 18px;
  margin-bottom: 10px;
  color: #2c3e50;
}
.career-detail {
  color: #606266;
  margin-bottom: 10px;
  font-size: 14px;
}
.career-hot, .career-salary, .career-competition {
  color: #409EFF;
  font-size: 14px;
}
</style>