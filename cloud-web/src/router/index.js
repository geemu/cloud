import router from './routers.js'

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import(/* webpackChunkName: "about" */ '../views/login.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import(/* webpackChunkName: "about" */ '../views/login.vue')
    }
];

router.beforeEach((to, from, next) => {
    console.log("beforeEach:", to, "from:", from, "next:", next)
});

router.afterEach(() => {
    console.log("afterEach");
});