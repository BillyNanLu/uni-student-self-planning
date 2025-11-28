<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

import ExamSchedule from '@/components/resources/ExamSchedule.vue'
import HotCareers from '@/components/resources/HotCareers.vue'
import FurtherStudyEmploymentResource from '@/components/resources/FurtherStudyEmploymentResource.vue'

</script>

<template>
  <FurtherStudyEmploymentResource />
  <HotCareers />
  <ExamSchedule />
</template>

<style scoped>

</style>