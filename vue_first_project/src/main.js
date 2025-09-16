
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import ElementPlus from 'element-plus'

// 如果您正在使用CDN引入，请删除下面一行。
import * as ElementPlusIconsVue from '@element-plus/icons-vue'




const app = createApp(App);
app.use(router); // 注册路由


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus, {
    locale: zhCn,
})
app.use(router)
app.use(ElementPlus)
app.mount('#app')

