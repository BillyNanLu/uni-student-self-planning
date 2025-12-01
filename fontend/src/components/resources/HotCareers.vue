<script setup>
import { ref, onMounted } from 'vue'

import getCareerList from '@/api/resource'

// 激活的标签页
const activeTab = ref('postgraduate')

const postgraduateList = ref([])
const civilServantList = ref([])
const jobList = ref([])

const isCollapsed = ref(false) // 控制整体内容的收起/展开

// ------------------ 加载数据 ------------------
const loadCareerData = async () => {
  // 1 = 考研，2 = 考公，3 = 就业
  const res1 = await getCareerList.getCareerList(1)
  postgraduateList.value = res1.data

  const res2 = await getCareerList.getCareerList(2)
  civilServantList.value = res2.data

  const res3 = await getCareerList.getCareerList(3)
  jobList.value = res3.data
}

// 切换整体折叠状态
const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
}

onMounted(() => {
  loadCareerData()
})
</script>

<template>
  <div class="hot-careers">
    <!-- 可点击的标题，用文字符号表示折叠状态 -->
    <h3
        class="section-title"
        @click="toggleCollapse"
        style="cursor: pointer; display: flex; align-items: center; justify-content: space-between;"
    >
      <span>热门规划方向参考</span>
      <span style="font-size: 18px; font-weight: bold;">{{ isCollapsed ? '▲' : '▼' }}</span>
    </h3>

    <!-- 内容区域，根据折叠状态显示/隐藏 -->
    <div v-show="!isCollapsed">
      <el-tabs v-model="activeTab" type="card" class="careers-tabs">
        <!-- 考研 -->
        <el-tab-pane label="考研" name="postgraduate">
          <el-card class="career-card" v-for="item in postgraduateList" :key="item.id">
            <h4 class="career-name">{{ item.name }}</h4>
            <p class="career-detail">{{ item.detail }}</p>
            <p v-if="item.extraType === 'hot'" class="career-hot">
              热度：{{ item.extraField }}
            </p>
          </el-card>
        </el-tab-pane>

        <!-- 考公 -->
        <el-tab-pane label="考公" name="civilServant">
          <el-card class="career-card" v-for="item in civilServantList" :key="item.id">
            <h4 class="career-name">{{ item.name }}</h4>
            <p class="career-detail">{{ item.detail }}</p>
            <p v-if="item.extraType === 'competition'" class="career-competition">
              竞争比：{{ item.extraField }}
            </p>
          </el-card>
        </el-tab-pane>

        <!-- 就业 -->
        <el-tab-pane label="就业" name="job">
          <el-card class="career-card" v-for="item in jobList" :key="item.id">
            <h4 class="career-name">{{ item.name }}</h4>
            <p class="career-detail">{{ item.detail }}</p>
            <p v-if="item.extraType === 'salary'" class="career-salary">
              平均起薪：{{ item.extraField }}
            </p>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </div>
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