import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ManagerView from "../views/Manager.vue"
import AboutView from "../views/About.vue"
import SysHome from "../views/Sys_home.vue"
import User from "../views/User.vue"
import LoginView from "../views/Login.vue"

import Register from "../views/Register.vue"

import Admin from "../views/Admin.vue"
import personInfo from  "../views/PersonInfo.vue"
import ResetPass from "../views/ResetPass.vue"
import Article from "../views/Article.vue"
import Department from "@/views/Department.vue";
import Statistic from  "@/views/Statistic.vue"


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home-redirect',
      redirect:"/manage"
    },
    {
      path:"/manage",
      name:"manage",
      component:()=>import("../views/Manager.vue"),
      redirect:"/manage/sys_home",
      meta:{title:"管理"},
      children:[
        {
          path:"sys_home",
          name:"sys_home",
          meta:{title:"主页"},
          component:()=>import("../views/Sys_home.vue")
        },
        {
          path:"user",
          name:"user",
          meta:{title:"用户信息"},
          component:()=>import("../views/User.vue")
        },
        {
          path:"admin",
          name:"admin",
          meta:{title:"管理员信息"},
          component:()=>import("../views/Admin.vue")
        },
        {
          path:"personInfo",
          name:"personInfo",
          meta:{title:"信息介绍"},
          component:()=>import("../views/PersonInfo.vue")
        },
        {
          path:"resetPass",
          name:"resetPass",
          meta:{title:"密码重置"},
          component:()=>import("../views/ResetPass.vue")
        },
        {
          path: 'article',
          name: 'article',
          meta:{title:"文章管理"},
          component:()=>import("../views/Article.vue")
        },
        {
          path: 'department',
          name: 'department',
          meta:{title:"部门"},
          component:()=>import("@/views/Department.vue")
        },
        {
          path: 'statistic',
          name: 'statistic',
          meta:{title:"数据统计"},
          component:()=>import("@/views/Statistic.vue")
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta:{title:"登录"},
      component:()=>import("../views/Login.vue")
    },
    {
      path: '/register',
      name: 'register',
      meta:{title:"注册"},
      component:()=>import("../views/Register.vue")
    },

    // {
    //   path: '/home',
    //   name: 'home',
    //   component: HomeView,
    //   meta:{title:"首页"},
    //   children:[
    //     {
    //       path: 'test',
    //       name: 'test',
    //       component: ManagerView,
    //       meta:{title:"测试"}
    //     },
    //     {
    //       path: 'about',
    //       name: 'about',
    //       component: AboutView,
    //       meta:{title:"关于我们"}
    //     }
    //   ]
    // },
    // 404 路由（必须放在最后）
    {
      path: '/:pathMatch(.*)*', // Vue Router 4.x 语法
      name: 'NotFound',
      component: () => import('@/views/NotFound.vue') // 懒加载优化性能
    }
  ],
})


// router.beforeEach((to, from, next) => {
//   const user_data = localStorage.getItem('userData') // 从 localStorage 读取用户数据
//
//   console.log(to)
//   console.log(from)
//
//   if (!user_data) {
//     // 需要登录态但用户未登录
//     next({ name: 'login' }) // 跳转到登录页
//   } else {
//     next() // 放行
//   }
// })

export default router
