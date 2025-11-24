// 导入request.js请求工具
import request from '@/utils/request.js'

// TODO: 提供获取问卷数量接口的函数
export const questionnaireCountService = () => {
    return request.get('/questionnaire/count')
}

// TODO: 提供获取问卷列表接口的函数
export const questionnaireListService = () => {
    return request.get('/questionnaire/all') // 后端接口返回 [{id, title}, ...]
}