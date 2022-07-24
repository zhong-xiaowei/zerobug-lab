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
        path: '/bpmn/editor',
        component: ()=> import('@/views/bpmn/editor')
    }
]


export default new Router({
    mode: 'history',
    routes: routes
})
