//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

import Home from "@/views/Home.vue"
import Planning from "@/views/Planning.vue"
import AIChat from "@/views/AIChat.vue"
import Resources from "@/views/Resources.vue"

//定义路由关系
const routes = [
    {
        path: '/login',
        component: LoginVue,
        // 通过query参数区分登录/注册状态
        // 例如：/login?type=register 表示进入注册表单
        props: route => ({ isRegister: route.query.type === 'register' })
    },
    {
        path: '/',
        component: Layout,
        // 重定向
        redirect: '/home',
        // 子路由
        children: [
            { path: '/home', component: Home },
            { path: '/planning', component: Planning },
            { path: '/ai-chat', component: AIChat },
            { path: '/resources', component: Resources },
        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router