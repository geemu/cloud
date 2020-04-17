import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../views/home.vue')
    },
    {
        path: '/login',
        name: 'home',
        component: () => import('../views/login.vue')
    }
]

const router = new VueRouter({
    routes
})

export default router
