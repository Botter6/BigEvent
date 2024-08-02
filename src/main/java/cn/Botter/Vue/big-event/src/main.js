import './assets/main.scss'
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'
import App from './App.vue'
import router from '@/router'
import {createPinia} from 'pinia'
import {createPersistedState} from "pinia-persistedstate-plugin";
// createApp(App).mount('#app')

const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);
app.use(router)
app.use(pinia)
app.use(ElementPlus,{locale});
app.mount('#app');
