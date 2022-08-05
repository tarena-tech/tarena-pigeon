import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      }
    ]
  },
  {
    path: '/app',
    component: Layout,
    redirect: '/app',
    meta: { title: '应用管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'app',
        name: 'App',
        component: () => import('@/views/app-center/app'),
        meta: { title: '应用管理', icon: 'table' }
      }
    ]
  },
  {
    path: '/provider',
    component: Layout,
    redirect: '/provider',
    meta: { title: '供应商管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'provider',
        name: 'Provider',
        component: () => import('@/views/provider-center/provider'),
        meta: { title: '供应商管理', icon: 'table' }
      }
    ]
  },
  {
    path: '/template',
    component: Layout,
    redirect: '/template/sms',
    meta: { title: '模板管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'sms',
        name: 'TemplateSms',
        component: () => import('@/views/template-center/sms'),
        meta: { title: '短信', icon: 'table' }
      }
    ]
  },
  {
    path: '/sign',
    component: Layout,
    redirect: '/sign',
    meta: { title: '签名管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'sign',
        name: 'smsSign',
        component: () => import('@/views/sign-center/sign'),
        meta: { title: '签名', icon: 'table' }
      }
    ]
  },
  {
    path: '/task',
    component: Layout,
    redirect: '/task',
    meta: { title: '任务管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'task',
        name: 'task',
        component: () => import('@/views/task-center/task'),
        meta: { title: '任务', icon: 'table' }
      },
      {
        path: 'task/detail',
        name: 'detail',
        component: () => import('@/components/task/dialog-detail'),
        meta: { title: '详情', icon: 'table' },
        hidden: true
      },
      {
        path: 'task/targets',
        name: 'targets',
        component: () => import('@/components/task/dialog-target'),
        meta: { title: '执行明细', icon: 'table' },
        hidden: true
      },
      {
        path: 'task/target/records',
        name: 'records',
        component: () => import('@/components/task/dialog-target-send-record'),
        meta: { title: '发送明细', icon: 'table' },
        hidden: true
      },

    ]
  },

  /* {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  }, */

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
