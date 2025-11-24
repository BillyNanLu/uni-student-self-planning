<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { questionnaireCountService, questionnaireListService } from '@/api/questionnaire.js'
import { questionsByQuestionnaireIdService } from "@/api/question.js";

const router = useRouter()

// 步骤配置
const steps = ref(['基础信息'])
const currentStep = ref(0) // 当前步骤
const questionnaireCount = ref(1) // 问卷数量，默认至少是1（基础信息）

const questions = ref([])
const answers = reactive({})  // 暂时存答案

// 表单数据
const formData = reactive({
  grade: '', // 年级
  major: '', // 专业
  answers: [] // 动态题目答案，按问卷索引存储数组
})


// 调用函数，获取问卷数量
const getQuestionnaireCount = async () => {
  try {
    const res = await questionnaireCountService()
    console.log('后端返回:', res)

    // 兼容纯数字返回（关键）
    const count = Number(res.data)

    // 如果解析失败，让 count 至少为 0
    questionnaireCount.value = (isNaN(count) ? 0 : count) + 1 // +1 代表基础信息部分
  } catch (error) {
    console.error('获取问卷数量失败', error)
    questionnaireCount.value = 1 // 异常时兜底，保证至少有1个步骤
  }
}

// 问卷列表（动态获取）
const questionnaires = ref([])

// 获取问卷列表（包含 title）
const getQuestionnaires = async () => {
  try {
    const res = await questionnaireListService()
    // res.data 假设返回 [{id: 1, title: '兴趣倾向'}, {id:2, title:'能力自评'}, ...]
    questionnaires.value = res.data || []

    // 动态生成步骤标题（基础信息 + 问卷标题）
    steps.value = ['基础信息', ...questionnaires.value.map(q => q.title)]

    // 初始化 answers 数组，每个问卷一个空数组
    formData.answers = questionnaires.value.map(() => [])
  } catch (error) {
    console.error('获取问卷列表失败', error)
    ElMessage.error('加载问卷失败')
  }
}

onMounted(() => {
  getQuestionnaireCount()
  getQuestionnaires()
})

// 表单引用
const evaluateForm = ref(null)

// 上一步
const prevStep = () => {
  currentStep.value--
  // 滚动到顶部
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

// 下一步（验证当前步骤表单）
const nextStep = async () => {
  try {
    if (currentStep.value === 0) {
      // 只验证基础信息
      await evaluateForm.value.validateField('grade')
      await evaluateForm.value.validateField('major')
    }

    // 验证通过，进入下一步
    currentStep.value++
    // 滚动到顶部
    window.scrollTo({ top: 0, behavior: 'smooth' })
  } catch (error) {
    // 验证失败，不执行任何操作（Element Plus 会自动显示错误信息）
    console.log('表单验证失败:', error)
  }
}

watch(currentStep, async (newStep) => {
  try {
    // 第 0 步是基础信息，不加载问卷
    if (newStep === 0) return

    // ⚠️ 问卷列表可能还没加载完
    if (!questionnaires.value || questionnaires.value.length === 0) return

    const item = questionnaires.value[newStep - 1]
    if (!item?.id) return  // 防止 undefined

    // 请求题目列表
    const res = await questionsByQuestionnaireIdService(item.id)
    const data = res?.data || []

    // 确保 options 不为 null，并初始化答案
    data.forEach(q => {
      if (!q.options) q.options = [] // 避免渲染报错

      // 初始化答案
      if (q.type === 1) answers[q.id] = ''   // 单选
      if (q.type === 2) answers[q.id] = []   // 多选
      if (q.type === 3) answers[q.id] = ''   // 文本
    })

    // 赋值到页面
    questions.value = data

  } catch (err) {
    console.error('加载题目失败:', err)
    ElMessage.error('加载题目失败，请检查网络或刷新页面')
  }
})

// 提交测评
const submitForm = async () => {
  try {
    await evaluateForm.value.validate()
    // 1. 提交数据到后端（示例）
    // const res = await evaluateSubmitService(formData)
    // 2. 假设提交成功，标记测评状态并跳转结果页
    ElMessage.success('测评提交成功！正在生成规划建议...')
    setTimeout(() => {
      router.push('/planning/evaluate/result')
    }, 1500)
  } catch (error) {
    // 表单验证失败
    ElMessage.error('请完成所有必填题目')
    return
  }
}


</script>

<template>
  <div class="evaluate-page">
    <div class="evaluate-container">
      <!-- 标题与进度 -->
      <div class="evaluate-header">
        <h2 class="page-title">发展方向测评</h2>
        <p class="page-desc">完成以下问卷，获取专属考研/考公/就业规划建议（共{{questionnaireCount}}部分，不会花太久时间）</p>

        <!-- 进度条 -->
        <el-progress
            :percentage="questionnaireCount > 1 ? Math.round(currentStep * (100 / (questionnaireCount - 1))) : 0"
            :stroke-width="6"
            class="progress-bar"
        />
        <div class="step-labels">
          <span
              v-for="(step, index) in steps"
              :key="index"
              :class="{ active: currentStep === index }"
          >
            {{ step }}
          </span>
        </div>
      </div>

      <!-- 答题区域（分步显示） -->
      <el-form
          ref="evaluateForm"
          :model="formData"
          class="evaluate-form"
      >
        <!-- 基础信息 -->
        <div v-if="currentStep === 0">
          <el-form-item
              label="你的年级"
              prop="grade"
              :rules="[{ required: true, message: '请选择年级', trigger: 'change' }]"
          >
            <el-select v-model="formData.grade" placeholder="请选择">
              <el-option label="大一" value="freshman" />
              <el-option label="大二" value="sophomore" />
              <el-option label="大三" value="junior" />
              <el-option label="大四" value="senior" />
            </el-select>
          </el-form-item>

          <el-form-item
              label="你的专业"
              prop="major"
              :rules="[{ required: true, message: '请输入专业', trigger: 'blur' }]"
          >
            <el-input
                v-model="formData.major"
                placeholder="如：计算机科学与技术、机械设计制造及其自动化‌、临床医学、法学、国际贸易等"
            />
          </el-form-item>
        </div>

        <!-- 动态问卷题目 -->
        <div v-else>
          <div v-if="questions.length > 0">

            <div
                v-for="(q, index) in questions"
                :key="q.id"
                class="question-item"
                style="margin-bottom: 20px;"
            >

              <!-- 题目 -->
              <p class="question-title">{{ index + 1 }}. {{ q.content }}</p>

              <!-- 单选题 -->
              <el-radio-group
                  v-if="q.type === 1"
                  v-model="answers[q.id]"
              >
                <el-radio
                    v-for="opt in (q.options || [])"
                    :key="opt.key"
                    :label="opt.key"
                >
                  {{ opt.label }}
                </el-radio>
              </el-radio-group>

              <!-- 多选题 -->
              <el-checkbox-group
                  v-if="q.type === 2"
                  v-model="answers[q.id]"
              >
                <el-checkbox
                    v-for="opt in (q.options || [])"
                    :key="opt.key"
                    :label="opt.key"
                >
                  {{ opt.label }}
                </el-checkbox>
              </el-checkbox-group>

              <!-- 文本题 -->
              <el-input
                  v-if="q.type === 3"
                  type="textarea"
                  v-model="answers[q.id]"
                  placeholder="请输入..."
              />

            </div>

          </div>

          <p v-else style="color:#999;">正在加载题目...</p>
        </div>


      </el-form>

      <!-- 操作按钮 -->
      <div class="form-actions">
        <el-button
            @click="prevStep"
            v-if="currentStep > 0"
        >
          上一步
        </el-button>
        <el-button
            type="primary"
            @click="nextStep"
            :disabled="questionnaires.length === 0"
            v-if="currentStep < questionnaireCount - 1"
        >
          下一步
        </el-button>
        <el-button
            type="success"
            @click="submitForm"
            v-if="currentStep === questionnaireCount - 1"
        >
          提交测评
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.evaluate-page {
  background-color: #f9fafc;
  min-height: calc(100vh - 60px); /* 减去导航栏高度 */
  padding: 30px 0;
}
.evaluate-container {
  max-width: 900px;
  margin: 0 auto;
  background-color: #fff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}
.evaluate-header {
  text-align: center;
  margin-bottom: 30px;
}
.page-title {
  font-size: 24px;
  color: #2c3e50;
  margin-bottom: 10px;
}
.page-desc {
  color: #606266;
  margin-bottom: 20px;
}
.progress-bar {
  margin-bottom: 10px;
}
.step-labels {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #909399;
}
.step-labels .active {
  color: #409EFF;
  font-weight: 500;
}
.evaluate-form {
  margin-bottom: 30px;
}
.el-form-item {
  margin-bottom: 20px;
}
.el-form-item__label {
  font-weight: 500;
}
.form-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 40px;
}
.el-button {
  padding: 10px 24px;
}
</style>