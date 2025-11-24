import request from '@/utils/request.js'

// 根据问卷ID获取题目列表
export const questionsByQuestionnaireIdService = (id) => {
    return request.get(`/questions/byQuestionnaire/${id}`)
}