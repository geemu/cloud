import Vue from 'vue';
import App from './App.vue';
import router from './router/index';
import store from './store/index';
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookie from 'vue-cookie';
import httpClient from './utils/httpClient.js';
import Settings from './settings';

Vue.use(VueCookie);
Vue.use(Element, {
    size: VueCookie.get('size') || 'medium' // set element-ui default size
});

Vue.config.productionTip = false;

// 挂载全局
Vue.prototype.$http = httpClient;
Vue.prototype.$Settings = Settings;

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});