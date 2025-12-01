<script setup>
import { ref, onMounted, watch} from 'vue'
// 引入Element Plus组件
import { ElCard, ElTag, ElIcon } from 'element-plus'
// 引入图标
import { Notebook, Briefcase, Promotion, Link, Check } from '@element-plus/icons-vue'

import { getFurtherResourceList } from "@/api/resource.js"

const resourceData = ref({
  postgraduate: [],
  civilServant: [],
  employment: []
})

// 场景对应 direction_id
const sceneMap = {
  postgraduate: 1,   // 考研
  civilServant: 2,   // 考公
  employment: 3      // 就业
}

// 场景切换
const activeScene = ref('postgraduate')


// 新增：修复报错的getTagType方法，根据资源类型匹配标签样式
const getTagType = (type) => {
  // 定义类型与Element Plus标签type的映射关系
  const typeMap = {
    '官方渠道': 'primary',    // 蓝色：官方类资源
    '免费资源': 'success',    // 绿色：免费类资源
    '权威指南': 'info',       // 浅蓝色：指南类资源
    '权威资源': 'info',       // 浅蓝色：权威类资源
    '官方解读': 'primary',    // 蓝色：官方解读类资源
    '实用工具': 'warning',    // 橙色：工具类资源
    '数据参考': 'purple',     // 紫色：数据类资源
    '支持资源': 'success'     // 绿色：支持类资源
  }
  // 找不到匹配类型时返回默认值
  return typeMap[type] || 'default'
}

// 加载资源的公共方法
const loadResources = async (scene) => {
  const directionId = sceneMap[scene]
  const res = await getFurtherResourceList(directionId)

  // 后端字段是 description，但前端用 desc → 统一一下
  resourceData.value[scene] = res.data.map(item => ({
    id: item.id,
    title: item.title,
    link: item.link,
    desc: item.description,
    type: item.type
  }))
}

// 初始化加载考研
onMounted(() => {
  loadResources('postgraduate')
})

// 场景变化 → 自动加载数据
watch(activeScene, (newScene) => {
  loadResources(newScene)
})

</script>

<template>
  <div class="resource-library-container">

    <div class="scene-switch">
      <el-button
          :class="activeScene === 'postgraduate' ? 'scene-active' : ''"
          @click="activeScene = 'postgraduate'"
      >
        <el-icon><Notebook /></el-icon>
        考研资源
      </el-button>
      <el-button
          :class="activeScene === 'civilServant' ? 'scene-active' : ''"
          @click="activeScene = 'civilServant'"
      >
        <el-icon><Promotion /></el-icon>
        考公资源
      </el-button>
      <el-button
          :class="activeScene === 'employment' ? 'scene-active' : ''"
          @click="activeScene = 'employment'"
      >
        <el-icon><Briefcase /></el-icon>
        就业资源
      </el-button>
    </div>


    <div class="resource-card-list">
      <el-card
          v-for="item in resourceData[activeScene]"
          :key="item.id"
          class="resource-card"
      >
        <div class="card-header">
          <h3 class="card-title">{{ item.title }}</h3>
          <ElTag size="small" :type="getTagType(item.type)">
            <el-icon size="12"><Check /></el-icon>
            {{ item.type }}
          </ElTag>
        </div>
        <p class="card-desc">{{ item.desc }}</p>
        <a
            :href="item.link"
            target="_blank"
            class="resource-link"
        >
          访问资源
          <el-icon><Link /></el-icon>
        </a>
      </el-card>
    </div>


    <div class="resource-tips">
      <h4>资源使用建议：</h4>
      <p>1. 优先选择“官方渠道”资源，确保信息真实性；2. 真题资源建议结合考试大纲使用，重点标注高频考点；3. 如有优质资源推荐，可通过底部“反馈”按钮提交。</p>
    </div>
  </div>
</template>

<style scoped>
.resource-library-container {
  padding: 10px;
}

.scene-switch {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.scene-active {
  background-color: #409EFF;
  color: #fff !important;
}

.resource-card-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.resource-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  transition: all 0.3s;
}

.resource-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  margin: 0;
}

.card-desc {
  font-size: 14px;
  color: #606266;
  margin-bottom: 20px;
  flex: 1;
}

.resource-link {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  color: #409EFF;
  text-decoration: none;
  font-size: 14px;
}

.resource-link:hover {
  text-decoration: underline;
}

.resource-tips {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
  font-size: 14px;
}

.resource-tips h4 {
  margin: 0 0 10px;
  font-size: 15px;
  color: #2c3e50;
}
</style>