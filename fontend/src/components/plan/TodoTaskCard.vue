<script setup>
import { ref } from 'vue'
const props = defineProps({
  taskList: { type: Array, required: true }
})
const emit = defineEmits(['toggleTask', 'addTask'])
const newTaskContent = ref('')

// 添加新任务
const handleAddTask = () => {
  if (!newTaskContent.value.trim()) return
  emit('addTask', newTaskContent.value)
  newTaskContent.value = '' // 清空输入框
}
</script>

<template>
  <el-card class="todo-task-card">
    <h3 class="card-title">近期待办任务</h3>
    <!-- 添加任务输入框 -->
    <div class="add-task">
      <el-input
          v-model="newTaskContent"
          placeholder="添加今日任务..."
          class="task-input"
          @keyup.enter="handleAddTask"
      />
      <el-button type="primary" @click="handleAddTask">添加</el-button>
    </div>
    <!-- 任务列表 -->
    <div class="task-list">
      <el-timeline>
        <el-timeline-item
            v-for="task in taskList"
            :key="task.id"
            :timestamp="task.date"
            placement="top"
        >
          <div class="task-item" :class="{ completed: task.completed }">
            <el-checkbox
                :checked="task.completed"
                @change="() => $emit('toggleTask', task.id)"
                class="task-checkbox"
            />
            <span class="task-content">{{ task.content }}</span>
          </div>
        </el-timeline-item>
      </el-timeline>
    </div>
  </el-card>
</template>

<style scoped>

</style>