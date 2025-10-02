import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import router from './router'
import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import locale from 'element-plus/dist/locale/zh-cn'

import App from './App.vue'

// 创建 pinia 实例
const pinia = createPinia()
// 给 pinia 挂载持久化插件
pinia.use(createPersistedState())

createApp(App)
    .use(pinia)
    // .use(router)
    .use(ElementPlus, { locale })
    .mount('#app')
