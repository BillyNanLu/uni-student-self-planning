<script setup>
import { ref } from 'vue'
// 引入Element Plus组件
import { ElCard, ElTag, ElIcon } from 'element-plus'
// 引入图标
import { Notebook, Briefcase, Promotion, Link, Check } from '@element-plus/icons-vue'

// 资源数据（按场景分类）
const resourceData = ref({
  postgraduate: [
    {
      id: 1,
      title: '官方真题渠道汇总',
      link: 'https://yz.chsi.com.cn/',
      desc: '优先从目标院校官网获取免费真题，研招网提供跨校真题汇总',
      type: '官方渠道'
    },
    {
      id: 2,
      title: '公共课刷题平台',
      link: 'https://www.fenbi.com/',
      desc: '粉笔考研APP提供公共课及热门专业课真题免费刷题，含解析',
      type: '免费资源'
    },
    {
      id: 3,
      title: '院校选择工具',
      link: 'https://yz.chsi.com.cn/sch/',
      desc: '官方院校及专业查询平台，可按地区、专业筛选，含招生数据',
      type: '官方渠道'
    },
    {
      id: 4,
      title: '专业课资源获取指南',
      link: 'https://m.koolearn.com/kaoyan/20251004/1878927.html',
      desc: '新东方在线出品，含真题获取6大渠道及避坑指南',
      type: '权威指南'
    }
  ],
  civilServant: [
    {
      id: 1,
      title: '国考官方信息发布平台',
      link: 'http://www.scs.gov.cn/',
      desc: '国家公务员局官网，发布招考公告、职位表、成绩查询等核心信息',
      type: '官方渠道'
    },
    {
      id: 2,
      title: '公考真题刷题平台',
      link: 'https://www.offcn.com/gongkao/',
      desc: '中公教育真题库，含历年国考、省考真题及解析，支持分模块练习',
      type: '权威资源'
    },
    {
      id: 3,
      title: '岗位选择查询系统',
      link: 'http://www.scs.gov.cn/pp/gkweb/core/web/ui/business/home/gkhome.html',
      desc: '官方职位查询工具，可按专业、学历、地区精准筛选目标岗位',
      type: '官方渠道'
    },
    {
      id: 4,
      title: '考公政策解读专栏',
      link: 'http://www.gov.cn/zhengce/zhuanti/2024gwy/',
      desc: '中国政府网官方专栏，解读报考政策、应届生身份等关键要求',
      type: '官方解读'
    }
  ],
  employment: [
    {
      id: 1,
      title: '全国校招信息平台',
      link: 'https://job.mohrss.gov.cn/',
      desc: '国家大学生就业服务平台，汇总全国企业校招、宣讲会信息',
      type: '官方渠道'
    },
    {
      id: 2,
      title: '简历优化工具',
      link: 'https://cv.zhaopin.com/',
      desc: '智联招聘简历优化工具，含专业模板及针对性优化建议',
      type: '实用工具'
    },
    {
      id: 3,
      title: '行业薪资查询',
      link: 'https://www.jobui.com/salary/',
      desc: '职友集薪资查询，可获取具体岗位薪资范围及地区差异数据',
      type: '数据参考'
    },
    {
      id: 4,
      title: '大学生创业支持平台',
      link: 'https://www.chinacyber.com/',
      desc: '提供创业政策、项目孵化、资金申请等全链条创业支持服务',
      type: '支持资源'
    }
  ]
})

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