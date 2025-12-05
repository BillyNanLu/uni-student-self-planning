//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from "@/stores/userInfo.js";

//导入组件
import LoginVue from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'
import AdminLayout from '@/views/admin/AdminLayout.vue'

// 用户端页面组件
import Home from "@/views/Home.vue"
import Planning from "@/views/plan/Planning.vue"
import AIChat from "@/views/AIChat.vue"     // 需登录
import Resources from "@/views/Resources.vue"

import PlanIndex from "@/views/plan/PlanIndex.vue";
import PlanEvaluate from "@/views/plan/PlanEvaluate.vue";   // 需登录
import PlanEvaluateIndex from "@/views/plan/PlanEvaluateIndex.vue";
import PlanEvaluateResult from "@/views/plan/PlanEvaluateResult.vue";   // 需登录
import AiReportHistory from "@/views/plan/AIReportHistory.vue";   // 需登录

import Profile from "@/views/Profile.vue"   // 需登录

//管理员端页面组件
import AdminDashboard from "@/views/admin/AdminDashboard.vue"
import AdminQuestionnaireManagement from "@/views/admin/AdminQuestionnaireManagement.vue"
import AdminQuestionManage from "@/views/admin/AdminQuestionManage.vue"
import AdminCareerManagement from "@/views/admin/AdminCareerManagement.vue"
import AdminExamManagement from "@/views/admin/AdminExamManagement.vue"
import AdminMaterialManagement from "@/views/admin/AdminMaterialManagement.vue"
import AdminUserManagement from "@/views/admin/AdminUserManagement.vue"
import AdminPlanTemplateManagement from "@/views/admin/AdminPlanTemplateManagement.vue"
import AdminAiReportManagement from "@/views/admin/AdminAiReportManagement.vue"
import AdminAiChatManagement from "@/views/admin/AdminAiChatManagement.vue"


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
    // 用户端路由
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
                            {path: 'index', component: PlanEvaluateIndex},
                            {path: 'result', component: PlanEvaluateResult, meta: { requiresAuth: true }}
                        ]
                    },
                    { path: 'ai-report', component: AiReportHistory, meta: { requiresAuth: true } }
                ]
            },
            { path: '/ai-chat', component: AIChat, meta: { requiresAuth: true } },
            { path: '/resources', component: Resources, meta: { requiresAuth: false } },
            { path: '/profile', component: Profile, meta: { requiresAuth: true } },
        ]
    },

    // 管理端路由
    {
        path: '/admin',
        component: AdminLayout,
        redirect: '/admin/dashboard',
        meta: { requiresAuth: true, requiresAdmin: true }, // 需要登录且是管理员
        children: [
            { path: 'dashboard', component: AdminDashboard },
            { path: 'title', component: AdminQuestionnaireManagement },
            { path: 'question', component: AdminQuestionManage },
            { path: 'career', component: AdminCareerManagement },
            { path: 'exam', component: AdminExamManagement },
            { path: 'material', component: AdminMaterialManagement },
            { path: 'users', component: AdminUserManagement },
            { path: 'template', component: AdminPlanTemplateManagement },
            { path: 'report', component: AdminAiReportManagement },
            { path: 'chat', component: AdminAiChatManagement },
        ]
    },
    // 404页面
    {
        path: '/:pathMatch(.*)*',
        component: () => import('@/views/NotFound.vue'),
        meta: { hideLayout: true }
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

// 路由守卫：对需登录的页面做权限校验
router.beforeEach((to, from, next) => {
    const tokenStore = useTokenStore() // 获取Pinia中的tokenStore
    const userInfoStore = useUserInfoStore()
    const isLogin = !!tokenStore.token // 从Pinia中读取token判断登录状态
    const isAdmin = userInfoStore.info.role === 1 // 1=管理员

    if (to.meta.requiresAuth && !isLogin) {
        next('/login') // 需登录但未登录，跳登录页
    }
    // 需要管理员权限但不是管理员
    else if (to.meta.requiresAdmin && !isAdmin) {
        next('/home') // 或者跳转到403页面
    }
    else {
        next() // 公开页面或已登录，正常访问
    }
});

export default router