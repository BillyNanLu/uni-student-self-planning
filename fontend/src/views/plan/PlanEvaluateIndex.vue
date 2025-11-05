<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 步骤配置
const steps = ['基础信息', '兴趣倾向', '能力自评', '职业倾向']
const currentStep = ref(0) // 当前步骤（0-3）

// 表单数据
const formData = reactive({
  grade: '', // 年级
  major: '', // 专业
  interest: [], // 兴趣倾向答案（数组索引对应题目索引）
  ability: [], // 能力自评答案
  career: [] // 职业倾向答案
})

// 题目数据（后续可从数据库获取，当前为静态案例）
const interestQuestions = [
  {
    content: '你更愿意花时间深入研究以下哪个领域？',
    options: [
      { label: 'A. 算法优化与人工智能原理', value: 'interest_1_A' },
      { label: 'B. 医学病理与临床案例', value: 'interest_1_B' },
      { label: 'C. 法律条文与判例分析', value: 'interest_1_C' },
      { label: 'D. 以上都不感兴趣', value: 'interest_1_D' }
    ]
  },
  {
    content: '你是否愿意为一个研究课题持续投入6个月以上的时间？',
    options: [
      { label: 'A. 非常愿意', value: 'interest_2_A' },
      { label: 'B. 愿意', value: 'interest_2_B' },
      { label: 'C. 不确定', value: 'interest_2_C' },
      { label: 'D. 不愿意', value: 'interest_2_D' }
    ]
  }
]

const abilityQuestions = [
  {
    content: '你每天能保持高效学习的时长大约是？',
    options: [
      { label: 'A. 6小时以上', value: 'ability_1_A' },
      { label: 'B. 4-6小时', value: 'ability_1_B' },
      { label: 'C. 2-4小时', value: 'ability_1_C' },
      { label: 'D. 2小时以内', value: 'ability_1_D' }
    ]
  },
  {
    content: '面对"多任务并行"的压力，你的应对能力是？',
    options: [
      { label: 'A. 游刃有余', value: 'ability_2_A' },
      { label: 'B. 可以应对', value: 'ability_2_B' },
      { label: 'C. 勉强应对', value: 'ability_2_C' },
      { label: 'D. 无法应对', value: 'ability_2_D' }
    ]
  }
]

const careerQuestions = [
  {
    content: '你未来3-5年的核心职业目标是？',
    options: [
      { label: 'A. 成为某领域专家', value: 'career_1_A' },
      { label: 'B. 进入体制内', value: 'career_1_B' },
      { label: 'C. 职场晋升', value: 'career_1_C' },
      { label: 'D. 实现财务自由/时间自由', value: 'career_1_D' }
    ]
  },
  {
    content: '你对"职业带来的社会价值"的重视程度是？',
    options: [
      { label: 'A. 非常重视', value: 'career_2_A' },
      { label: 'B. 重视', value: 'career_2_B' },
      { label: 'C. 一般', value: 'career_2_C' },
      { label: 'D. 不重视', value: 'career_2_D' }
    ]
  }
]

// 表单引用
const evaluateForm = ref(null)

// 上一步
const prevStep = () => {
  currentStep.value--
}

// 下一步（验证当前步骤表单）
const nextStep = async () => {
  try {
    // 根据当前步骤验证对应的字段
    let fieldsToValidate = []
    switch(currentStep.value) {
      case 0:
        fieldsToValidate = ['grade', 'major']
        break
      case 1:
        fieldsToValidate = interestQuestions.map((_, index) => `interest.${index}`)
        break
      case 2:
        fieldsToValidate = abilityQuestions.map((_, index) => `ability.${index}`)
        break
      case 3:
        fieldsToValidate = careerQuestions.map((_, index) => `career.${index}`)
        break
    }

    // 验证所有必要字段
    for (const field of fieldsToValidate) {
      await evaluateForm.value.validateField(field)
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
        <p class="page-desc">完成以下问卷，获取专属考研/考公/就业规划建议（共4部分，约5分钟）</p>

        <!-- 进度条 -->
        <el-progress
            :percentage="currentStep * 25"
            stroke-width="6"
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
        <!-- 第一部分：基础信息 -->
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
                placeholder="如：计算机科学与技术、临床医学"
            />
          </el-form-item>
        </div>

        <!-- 第二部分：兴趣倾向（动态渲染题目） -->
        <div v-if="currentStep === 1">
          <el-form-item
              v-for="(question, qIndex) in interestQuestions"
              :key="qIndex"
              :label="`Q${qIndex + 1}. ${question.content}`"
              :prop="`interest.${qIndex}`"
              :rules="[{ required: true, message: '请选择答案', trigger: 'change' }]"
          >
            <el-radio-group v-model="formData.interest[qIndex]">
              <el-radio
                  v-for="(option, oIndex) in question.options"
                  :key="oIndex"
                  :label="option.value"
              >
                {{ option.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </div>

        <!-- 第三部分：能力自评 -->
        <div v-if="currentStep === 2">
          <el-form-item
              v-for="(question, qIndex) in abilityQuestions"
              :key="qIndex"
              :label="`Q${qIndex + 1}. ${question.content}`"
              :prop="`ability.${qIndex}`"
              :rules="[{ required: true, message: '请选择答案', trigger: 'change' }]"
          >
            <el-radio-group v-model="formData.ability[qIndex]">
              <el-radio
                  v-for="(option, oIndex) in question.options"
                  :key="oIndex"
                  :label="option.value"
              >
                {{ option.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </div>

        <!-- 第四部分：职业倾向 -->
        <div v-if="currentStep === 3">
          <el-form-item
              v-for="(question, qIndex) in careerQuestions"
              :key="qIndex"
              :label="`Q${qIndex + 1}. ${question.content}`"
              :prop="`career.${qIndex}`"
              :rules="[{ required: true, message: '请选择答案', trigger: 'change' }]"
          >
            <el-radio-group v-model="formData.career[qIndex]">
              <el-radio
                  v-for="(option, oIndex) in question.options"
                  :key="oIndex"
                  :label="option.value"
              >
                {{ option.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
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
            v-if="currentStep < 3"
        >
          下一步
        </el-button>
        <el-button
            type="success"
            @click="submitForm"
            v-if="currentStep === 3"
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