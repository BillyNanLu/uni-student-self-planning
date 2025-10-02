# Fontend

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VS Code](https://code.visualstudio.com/) + [Vue (Official)](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Recommended Browser Setup

- Chromium-based browsers (Chrome, Edge, Brave, etc.):
  - [Vue.js devtools](https://chromewebstore.google.com/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd) 
  - [Turn on Custom Object Formatter in Chrome DevTools](http://bit.ly/object-formatters)
- Firefox:
  - [Vue.js devtools](https://addons.mozilla.org/en-US/firefox/addon/vue-js-devtools/)
  - [Turn on Custom Object Formatter in Firefox DevTools](https://fxdx.dev/firefox-devtools-custom-object-formatters/)

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

---

## Environment Configuration

### element-plus

```shell
npm install element-plus --save
```

### axios
```shell
npm install axios
```

### sass(.scss)
```shell
npm install sass -D
```

### router
```shell
npm install vue-router@4
```

### pinia
```shell
npm install pinia
```

### pinia-persistedstate-plugin
```shell
npm install pinia-persistedstate-plugin
```

### quill(quill text editor)
```shell
npm install @vueup/vue-quill@latest --save
```

### main.js
```javascript
import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
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
    .use(router)
    .use(ElementPlus, { locale })
    .mount('#app')
```