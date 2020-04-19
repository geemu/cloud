import Vue from 'vue';
import App from './App.vue';
import router from './router/index';
import store from './store/index';
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookie from 'vue-cookie';
import Settings from './settings';

Vue.use(VueCookie);
Vue.use(Element, {
    // 设置element-ui默认样式大小
    size: VueCookie.get('size') || 'medium'
});

Vue.config.productionTip = false;

// 挂在自定义设置为全局属性
Vue.prototype.$Settings = Settings;

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});