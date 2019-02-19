import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout';

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
 **/
export const constantRouterMap = [
  {path: '/login', component: () => import('@/views/login/login'), hidden: true},
  {path: '/index', component: () => import('@/views/index/index'), hidden: true},
  {
    path: '/user/info',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/user/info',
        name: '个人信息',
        component: () => import('@/views/userinfo/index'),
        meta: {title: '个人信息', icon: 'user'}
      }]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '主页', icon: 'home'}
    }]
  },
  {
    path: '/echart',
    component: Layout,
    redirect: '/echart/bar',
    name: 'Example',
    meta: {title: 'Echarts', icon: 'charts'},
    children: [
     /* {
        path: 'bar',
        name: 'bar',
        component: () => import('@/views/echarts/BarChart'),
        meta: {title: '柱状图', icon: 'bar'}
      },
      {
        path: 'map',
        name: 'map',
        component: () => import('@/views/echarts/ChinaMap'),
        meta: {title: '中国地图', icon: 'map'}
      },*/
      {
        path: 'line',
        name: 'line',
        component: () => import('@/views/overview/index'),
        meta: {title: '统计', icon: 'charts'}
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/user',
    name: 'admin',
    meta: {title: '后台管理', icon: 'setting'},
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/admin/User'),
        meta: {title: '用户管理', icon: 'user'}
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views/admin/Role'),
        meta: {title: '角色管理', icon: 'role'}
      }
    ]
  }, {
    path: '/order',
    component: Layout,
    meta: {title: '订单管理', icon: 'order'},
    name: 'Order',
    children: [
      {
        path: 'order',
        name: 'order',
        component: () => import('@/views/order/Order'),
        meta: {title: '订单列表', icon: 'order'}
      },
      {
        path: 'daily',
        name: 'daily',
        component: () => import('@/views/order/Daily'),
        meta: {title: '产量列表', icon: 'order'}
      }
    ]
  },
  {path: '/*', component: () => import('@/views/404'), hidden: true}
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
