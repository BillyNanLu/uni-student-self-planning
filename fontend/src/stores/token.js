// 定义store
import { defineStore } from 'pinia'
import { ref } from 'vue'

/*
 * @Description: token store
 * 第一个参数：名字，唯一性
 * 第二个参数：函数，函数的内部可以定义状态的所有内容
 *
 * 返回值：函数
 */

export const useTokenStore = defineStore('token', () => {
    // 定义状态的内容
    // 1. 响应式变量
    const token = ref('')
    // 2. 定义方法，修改token的值
    const setToken = (newToken) => {
        token.value = newToken
    }

    // 3. 定义方法，移除token的值
    const removeToken = () => {
        token.value = ''
    }

    return {
        token, removeToken, setToken
    }
}, {
    persist: true // 持久化存储
})