<script setup>
const props = defineProps({
  tagsData: {
    type: Object,
    required: true,
    default: () => ({
      interests: [],
      abilities: [],
      selfEvaluation: []
    })
  }
});
</script>

<template>
  <div class="user-profile-card">
    <h3 class="card-title">用户画像分析</h3>

    <div class="tag-group">
      <div class="tag-category">
        <span class="category-label">兴趣标签：</span>
        <el-tag
            v-for="tag in tagsData.interests"
            :key="`interest-${tag}`"
            type="primary"
            class="tag-item"
        >
          {{ tag }}
        </el-tag>
      </div>

      <div class="tag-category">
        <span class="category-label">能力标签：</span>
        <el-tag
            v-for="tag in tagsData.abilities"
            :key="`ability-${tag}`"
            type="success"
            class="tag-item"
        >
          {{ tag }}
        </el-tag>
      </div>

      <div class="tag-category">
        <span class="category-label">自我评价：</span>
        <el-tag
            v-for="tag in tagsData.selfEvaluation"
            :key="`eval-${tag}`"
            type="warning"
            class="tag-item"
        >
          {{ tag }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<style scoped>
.user-profile-card {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.card-title {
  font-size: 18px;
  color: #2c3e50;
  margin: 0 0 16px 0;
  font-weight: 600;
}

.tag-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.tag-category {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
}

.category-label {
  color: #606266;
  font-weight: 500;
  white-space: nowrap;
}

.tag-item {
  margin-bottom: 4px;
}
</style>