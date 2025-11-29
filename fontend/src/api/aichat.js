// 导入request.js请求工具
import request from '@/utils/request.js'

// TODO: 提供调用获取聊天历史接口的函数
export const getChatHistoryService = (params) => {
    return request.get('/ai-chat/history', { params })
}

// TODO: 提供调用保存消息接口的函数
export const saveMessageService = (data) => {
    return request.post('/ai-chat/save', null, { params: data })
}

// TODO: 提供调用获取AI回复接口的函数
export const getAiResponseService = (data) => {
    return request.post('/ai-chat/response', null, { params: data })
}

// TODO: 提供调用清空聊天记录接口的函数
export const clearChatHistoryService = (params) => {
    return request.delete('/ai-chat/clear', { params })
}