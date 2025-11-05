//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'

//导入组件
import LoginVue from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

import Home from "@/views/Home.vue"
import Planning from "@/views/plan/Planning.vue"
import AIChat from "@/views/AIChat.vue"     // 需登录
import Resources from "@/views/Resources.vue"

import PlanIndex from "@/views/plan/PlanIndex.vue";
import PlanEvaluate from "@/views/plan/PlanEvaluate.vue";   // 需登录
import PlanEvaluateIndex from "@/views/plan/PlanEvaluateIndex.vue";
import PlanProgress from "@/views/plan/PlanProgress.vue";   // 需登录

import Profile from "@/views/users/Profile.vue"   // 需登录

//定义路由关系
const routes = [
    {
        path: '/login',
        component: LoginVue,
        // 通过query参数区分登录/注册状态
        // 例如：/login?type=register 表示进入注册表单
        props: route => ({ isRegister: route.query.type === 'register' }),
        meta: {
            hideLayout: true
        }
    },
    {
        path: '/',
        component: Layout,
        // 重定向
        redirect: '/home',
        // 子路由
        children: [
            { path: '/home', component: Home },
            {
                path: '/planning',
                component: Planning,
                redirect: '/planning/index',
                children: [
                    { path: 'index', component: PlanIndex},
                    {
                        path: 'evaluate',
                        component: PlanEvaluate,
                        redirect: '/planning/evaluate/index',
                        meta: { requiresAuth: true},
                        children: [
                            {path: 'index', component: PlanEvaluateIndex}
                        ]
                    },
                    { path: 'progress', component: PlanProgress, meta: { requiresAuth: true } }
                ]
            },
            { path: '/ai-chat', component: AIChat, meta: { requiresAuth: true } },
            { path: '/resources', component: Resources },
        ]
    },
    // 需登录的页面（通过路由守卫限制）
    { path: '/profile', component: Profile, meta: { requiresAuth: true } }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

// 路由守卫：对需登录的页面做权限校验
router.beforeEach((to, from, next) => {
    const tokenStore = useTokenStore() // 获取Pinia中的tokenStore
    const isLogin = !!tokenStore.token // 从Pinia中读取token判断登录状态
    if (to.meta.requiresAuth && !isLogin) {
        next('/login') // 需登录但未登录，跳登录页
    } else {
        next() // 公开页面或已登录，正常访问
    }
});

export default router