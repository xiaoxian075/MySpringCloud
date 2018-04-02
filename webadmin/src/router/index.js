/**
 * Created by zzmhot on 2017/3/23.
 *
 * 路由Map
 *
 * @author: zzmhot
 * @github: https://github.com/zzmhot
 * @email: zzmhot@163.com
 * @Date: 2017/3/23 18:30
 * @Copyright(©) 2017 by zzmhot.
 *
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import store from 'store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

// import {mapGetters, mapActions} from 'vuex'
// import {GET_USER_INFO} from 'store/getters/type'
// import {SET_USER_INFO} from 'store/actions/type'

//import components
//view page warp component
import viewPageComponent from 'pages/App'

//home
import homeComponent from 'pages/home'
import remind from 'pages/home/remind'
//404
import noPageComponent from 'pages/error/404'
//login
import loginComponent from 'pages/user/login'
// //base table
// import baseTableComponent from 'pages/table/base'
// //sort table
// import sortTableComponent from 'pages/table/sort'
// //save table
// import saveTableComponent from 'pages/table/save'
// //bar charts
// import barChartsComponent from 'pages/charts/bar'

import kd100 from 'pages/comset/kd100'
import carousel from 'pages/comset/carousel'
import expenses from 'pages/comset/expenses'

import community from 'pages/community/community'

import commodity from 'pages/commodity/commodity'
import commodityAttr from 'pages/commodity/commodityAttr'
import shop from 'pages/commodity/shop'
import shopActivity from 'pages/commodity/shopActivity'

import order from 'pages/order/order'

import account from 'pages/member/account'

import message from 'pages/member/message'

import payRecord from  'pages/member/payRecord'

import version from  'pages/other/version'

//import editorTest from 'pages/comset/editorTest'

//import goodsList from 'pages/goods/list'

Vue.use(VueRouter)

//使用AMD方式加载
// component: resolve => require(['pages/home'], resolve),
const routes = [/*{
  path: '/404',
  name: 'notPage',
  component: noPageComponent
}, {
  path: '*',
  redirect: '/user/login'
}, */{
  path: '/user/login',
  name: 'login',
  component: loginComponent
}, {
  path: '/',
  redirect: '/home/remind',
  component: viewPageComponent,
  children: [/*{
    path: '/home',
    name: 'home',
    component: homeComponent,
    meta: {
      title: "主页",
      auth: true
    }
  },*/{
    path: '/home/remind',
    name: 'remind',
    component: remind,
    meta: {
      title: "主页",
      auth: true
    }
  }, {
    path: '/member/account',
    name: 'account',
    component: account,
    meta: {
      title: "会员列表",
      auth: true
    }
  }, {
    path: '/member/message',
    name: 'message',
    component: message,
    meta: {
      title: "消息设置",
      auth: true
    }
  }, {
    path: '/member/payRecord',
    name: 'payRecord',
    component: payRecord,
    meta: {
      title: "交易记录",
      auth: true
    }
  }, {
    path: '/comset/kd100',
    name: 'kd100',
    component: kd100,
    meta: {
      title: "快递100",
      auth: true
    }
  }, {
    path: '/comset/carousel',
    name: 'carousel',
    component: carousel,
    meta: {
      title: "轮播图",
      auth: true
    }
  }, {
    path: '/community/community',
    name: 'community',
    component: community,
    meta: {
      title: "云社区资讯",
      auth: true
    }
  }, {
    path: '/comset/expenses',
    name: 'expenses',
    component: expenses,
    meta: {
      title: "运费地区设置",
      auth: true
    }
  }, {
    path: '/commodity/commodity',
    name: 'commodity',
    component: commodity,
    meta: {
      title: "商品",
      auth: true
    }
  }, {
    path: '/commodity/commodityAttr',
    name: 'commodityAttr',
    component: commodityAttr,
    meta: {
      title: "商品属性",
      auth: true
    }
  }, {
    path: '/commodity/shop',
    name: 'shop',
    component: shop,
    meta: {
      title: "店铺",
      auth: true
    }
  }, {
    path: '/commodity/shopActivity',
    name: 'shopActivity',
    component: shopActivity,
    meta: {
      title: "店铺活动",
      auth: true
    }
  }, {
    path: '/order/order',
    name: 'order',
    component: order,
    meta: {
      title: "订单",
      auth: true
    }
  }, {
    path: '/other/version',
    name: 'version',
    component: version,
    meta: {
      title: "版本",
      auth: true
    }
  },{
    path: '*',
    name: 'notPage',
    component: noPageComponent
  }]
}]


// function showAction(){
//   var arr=[
//     {
//       path: '/table/base',
//       name: 'tableBase',
//       component: baseTableComponent,
//       meta: {
//         title: "基本表格",
//         auth: true
//       }
//     }, {
//       path: '/table/sort',
//       name: 'tableSort',
//       component: sortTableComponent,
//       meta: {
//         title: "排序表格",
//         auth: true
//       }
//     }
//   ]
//   return arr
// }

function checkPower(name) {
  if (
    name == 'login' ||
    name == 'home' ||
    name == 'notPage'
  ) {
    return true;
  }

  let strMenuPower = window.localStorage.getItem("menuPower");
  if (strMenuPower) {
    let menuPower = JSON.parse(strMenuPower);
    console.log(menuPower);
    if (menuPower) {
      for (var index = 0; index < menuPower.length; index++) {
        if (name == menuPower[index]) {
          return true;
        }
      }
    }
  }


  return false;
};

const router = new VueRouter({
  routes,
  mode: 'hash', //default: hash ,history
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }
})

//全局路由配置
//路由开始之前的操作
router.beforeEach((to, from, next) => {
  NProgress.done().start()
  let toName = to.name
  // let fromName = from.name
  let is_login = store.state.user_info.login;

  if (!checkPower(toName)) {
    next('/404');
    // if (toName == 'commodityAttr') {
    //   next('/commodity/commodityAttr');
    // }
    // next({
    //   name: 'notPage'
    // })
  } else if (!is_login && toName !== 'login') {
    next({
      name: 'login'
    })
  } else {
    if (is_login && toName === 'login') {
      next({
        path: '/'
      })
    } else {
      next()
    }
  }

  // var arr = showAction();
  // var isError = false
  // for(var i=0;i<arr.length;i++){
  //   alert(arr[i].name);
  //   if(to.name == arr[i].name){ // 判断是否有权限
  //     isError = false
  //     if (!is_login && toName !== 'login') {
  //       next({
  //         name: 'login'
  //       })
  //     } else {
  //       if (is_login && toName === 'login') {
  //         next({
  //           path: '/'
  //         })
  //       } else {
  //         next()
  //       }
  //     }
  //     break;
  //   }else{
  //     isError = true
  //   }
  // }
  //
  // if(isError){
  //   next('/404') // 否则全部重定向到404页
  // }


})

//路由完成之后的操作
router.afterEach(route => {
  NProgress.done()
})

export default router
