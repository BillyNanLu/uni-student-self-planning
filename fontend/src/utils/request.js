//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
import { useTokenStore } from "@/stores/token.js"
import router from '@/router'

//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({baseURL})

// 添加请求拦截器
instance.interceptors.request.use(
    config=>{
        // 请求前的回调
        // 添加token
        const tokenStore = useTokenStore();
        // 判断有没有token
        if (tokenStore.token)
            config.headers.Authorization = tokenStore.token
        return config;
    },
    err=>{
        // 请求错误的回调
        return Promise.reject(err);
    }
)

//添加响应拦截器
instance.interceptors.response.use(
    result => {
        // ⭐ 情况1：后端返回纯数字 / 字符串（没有 code）
        if (typeof result.data === 'number' || typeof result.data === 'string') {
            return { data: result.data }
        }

        // ⭐ 情况2：后端返回纯数组（没有 code）
        if (Array.isArray(result.data)) {
            return { data: result.data }
        }

        // ⭐ 情况3：后端返回正常业务结构 { code, data, msg }
        if (result.data.code === 0) {
            return result.data
        }

        // ❗ 其他情况：视为异常
        // ElMessage.error(result.data.msg ? result.data.msg : '服务异常')
        return Promise.reject(result.data)
    },
    err=>{
        // 判断响应状态码，如果是401，则证明未登录，提示请登录，并跳转到登录页面
        // if (err.response.status === 401) {
        //     ElMessage.error('请登录');
        //     router.push('/login')
        // } else {
        //     // 服务异常
        //     ElMessage.error('服务异常')
        // }
        // return Promise.reject(err); // 异步的状态转化成失败的状态
        if (err.response) {
            const status = err.response.status

            if (status === 401) {
                ElMessage.error('请登录')
                router.push('/login')
            } else {
                ElMessage.error(err.response.data?.msg || '服务异常')
            }
        } else {
            // 无响应（如请求失败/404/跨域/后端未启动）
            ElMessage.error('无法连接服务器，请检查接口地址或后端是否启动')
        }
    }
)

export default instance;