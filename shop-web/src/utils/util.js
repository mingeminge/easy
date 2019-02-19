import store from '../store'
import {validatenull} from "./validate";

export const initMenu = (router, menu) => {
  if (menu.length === 0) {
    return
  }
  const menus = formatRoutes(menu)
  const unfound = {
    path: '*',
    redirect: '/404',
    hidden: true
  }
  menus.push(unfound)
  console.log(router)
  store.commit('ADD_ROUTERS', menus)
}
export const formatRoutes = (aMenu) => {
  const aRouter = []
  aMenu.forEach(oMenu => {
    const {
      path,
      component,
      name,
      icon,
      children
    } = oMenu
    if (!validatenull(component)) {
      const oRouter = {
        path: path,
        component(resolve) {
          let componentPath = ''
          if (component === 'Layout') {
            require(['../views/layout/Layout'], resolve)
            return
          } else {
            componentPath = component
          }
          require([`@/views/${componentPath}.vue`], resolve)
        },
        name: name,
        meta: {
          icon: icon,
          title: name
        },
        icon: icon,
        children: validatenull(children) ? [] : formatRoutes(children)
      }
      aRouter.push(oRouter)
    }
  })
  return aRouter
}
