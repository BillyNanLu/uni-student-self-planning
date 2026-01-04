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

// 空状态提示文本
const emptyText = "暂无相关标签";
</script>

<template>
  <div class="user-profile-card">
    <!-- 标题栏增加装饰线，提升层次感 -->
    <div class="card-header">
      <h3 class="card-title">用户画像分析</h3>
      <p class="card-subtitle">基于你最近一次问卷中所提交的非文本题答案</p>
    </div>

    <div class="tag-group">
      <!-- 兴趣标签：主色调优化 -->
      <div class="tag-category">
        <span class="category-label">兴趣标签</span>
        <div class="tag-list">
          <el-tag
              v-for="tag in tagsData.interests"
              :key="`interest-${tag}`"
              class="tag-item interest-tag"
              effect="plain"
          >
            {{ tag }}
          </el-tag>
          <!-- 空状态处理 -->
          <span v-if="tagsData.interests.length === 0" class="empty-tag">
            {{ emptyText }}
          </span>
        </div>
      </div>

      <!-- 能力标签：渐变背景 -->
      <div class="tag-category">
        <span class="category-label">能力标签</span>
        <div class="tag-list">
          <el-tag
              v-for="tag in tagsData.abilities"
              :key="`ability-${tag}`"
              class="tag-item ability-tag"
              effect="plain"
          >
            {{ tag }}
          </el-tag>
          <span v-if="tagsData.abilities.length === 0" class="empty-tag">
            {{ emptyText }}
          </span>
        </div>
      </div>

      <!-- 自我评价：暖色调 + 图标点缀 -->
      <div class="tag-category">
        <span class="category-label">自我评价</span>
        <div class="tag-list">
          <el-tag
              v-for="tag in tagsData.selfEvaluation"
              :key="`eval-${tag}`"
              class="tag-item eval-tag"
              effect="plain"
          >
            <i class="el-icon-user"></i>
            <span class="tag-text">{{ tag }}</span>
          </el-tag>
          <span v-if="tagsData.selfEvaluation.length === 0" class="empty-tag">
            {{ emptyText }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 卡片整体优化：更柔和的阴影和圆角 */
.user-profile-card {
  padding: 24px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.3s ease;
}

/* 卡片hover动效 */
.user-profile-card:hover {
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.08);
}

/* 标题栏优化：增加层次感 */
.card-header {
  margin-bottom: 24px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f5f7fa;
}

.card-title {
  font-size: 20px;
  color: #1d2129;
  margin: 0 0 4px 0;
  font-weight: 600;
}

.card-subtitle {
  font-size: 14px;
  color: #86909c;
  margin: 0;
}

/* 标签组间距优化 */
.tag-group {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 分类容器：垂直居中更协调 */
.tag-category {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 分类标签：精致化设计 */
.category-label {
  color: #4e5969;
  font-weight: 500;
  white-space: nowrap;
  width: 88px;
  font-size: 15px;
  position: relative;
}

/* 标签名左侧小装饰点 */
.category-label::before {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 4px;
  border-radius: 50%;
}

/* 每个分类标签的装饰点颜色区分 */
.tag-category:nth-child(1) .category-label::before {
  background-color: #409eff; /* 兴趣标签-蓝色 */
}
.tag-category:nth-child(2) .category-label::before {
  background-color: #52c41a; /* 能力标签-绿色 */
}
.tag-category:nth-child(3) .category-label::before {
  background-color: #faad14; /* 自我评价-橙色 */
}

/* 标签列表：更宽松的间距 */
.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  flex: 1; /* 占满剩余宽度 */
}

/* 通用标签样式：圆角、阴影、hover动效 */
.tag-item {
  border-radius: 20px !important; /* 圆角更圆润 */
  padding: 6px 16px !important;
  font-size: 14px !important;
  transition: all 0.2s ease;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.03);
}

/* 兴趣标签：淡蓝色调 */
.interest-tag {
  color: #409eff !important;
  border-color: #e6f4ff !important;
  background-color: #f0f7ff !important;
}
.interest-tag:hover {
  background-color: #e6f4ff !important;
  border-color: #409eff !important;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(64, 158, 255, 0.15);
}

/* 能力标签：淡绿色调 + 渐变 */
.ability-tag {
  color: #52c41a !important;
  border-color: #f0f9e8 !important;
  background: linear-gradient(135deg, #f0f9e8 0%, #e6f7d9 100%) !important;
}
.ability-tag:hover {
  background: linear-gradient(135deg, #e6f7d9 0%, #d9f2c7 100%) !important;
  border-color: #52c41a !important;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(82, 196, 26, 0.15);
}

/* 自我评价标签：淡橙色 + 图标间距 */
.eval-tag {
  color: #faad14 !important;
  border-color: #fff7e6 !important;
  background-color: #fff7e6 !important;
  display: flex !important;
  align-items: center !important;
  gap: 6px !important;
}
.eval-tag:hover {
  background-color: #fff1d6 !important;
  border-color: #faad14 !important;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(250, 173, 20, 0.15);
}

/* 空状态样式：灰色、斜体，不突兀 */
.empty-tag {
  color: #86909c;
  font-size: 14px;
  font-style: italic;
  padding: 6px 16px;
  border: 1px dashed #e5e6eb;
  border-radius: 20px;
  background-color: #fafafa;
}
</style>