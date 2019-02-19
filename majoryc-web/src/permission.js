import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import {Message} from 'element-ui'
import {getToken} from '@/utils/auth'
import {GetMenu} from "./api/menu";
import lazyLoading from "./utils/lazyLoading"; // 验权

const whiteList = ['/login', '/index'] // 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          /*GetMenu('admin').then(resp => {
            let route = resp.data;
            route.forEach(item => {
              console.log(item);
              item.component = resolve => require(['@/views/dashboard/index'], resolve);
              if (item.children) {
                item.children.forEach(child => {
                  /!*child.component = () => lazyLoading(child.component)*!/
                  child.component = resolve => require([`@/views/${child.component}`],resolve);
                })
              }
            });
            router.addRoutes(route);
          })*/
          next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({path: '/'})
          })
        })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})
router.afterEach(() => {
  NProgress.done() // 结束Progress
})
