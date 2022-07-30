import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/views/index'

Vue.use(Router)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Index
    },
    {
        path: '/bpmn',
        component: ()=> import('@/views/bpmn/index')
    },
    {
        path: '/bpmn/designer',
        component: ()=> import('@/views/bpmn/designer')
    },
    {
        path: '/captcha',
        component: ()=> import('@/views/captcha/index')
    }
]


export default new Router({
    mode: 'history',
    routes: routes
})
