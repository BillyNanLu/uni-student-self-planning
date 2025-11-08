<script setup>
import { ref } from 'vue'
import { Document } from '@element-plus/icons-vue'
const props = defineProps({
  stageDetails: { type: Array, required: true }
})
const activeStage = ref([0]) // 默认展开第一个阶段
</script>

<template>
  <el-card class="stage-path-card">
    <h3 class="card-title">阶段学习路径</h3>
    <el-collapse v-model="activeStage">
      <el-collapse-item
          v-for="(stage, index) in stageDetails"
          :key="index"
          :title="stage.stage"
          :name="index"
      >
        <!-- 阶段任务 -->
        <div class="stage-tasks">
          <h4 class="section-title">核心任务</h4>
          <el-checkbox-group>
            <el-checkbox
                v-for="task in stage.tasks"
                :key="task.id"
                :label="task.id"
                :checked="task.completed"
                disabled
            >
              {{ task.name }}
            </el-checkbox>
          </el-checkbox-group>
        </div>

        <!-- 推荐资源 -->
        <div class="stage-resources">
          <h4 class="section-title">推荐资源</h4>
          <el-link
              v-for="resource in stage.resources"
              :key="resource.id"
              :href="resource.url"
              type="primary"
              :underline="false"
              class="resource-link"
          >
            <Document /> {{ resource.name }}
          </el-link>
        </div>
      </el-collapse-item>
    </el-collapse>
  </el-card>
</template>

<style scoped>

</style>