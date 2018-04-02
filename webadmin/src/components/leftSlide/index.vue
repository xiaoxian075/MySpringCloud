<template>
  <div class="left-side">
    <div class="left-side-inner">
      <router-link to="/" class="logo block">
        <img src="./images/logo.png" alt="AdminX">
      </router-link>
      <el-menu
        class="menu-box"
        theme="dark"
        router
        :default-active="$route.path">
        <div
          v-for="(item, index) in nav_menu_data"
          :key="index">
          <el-menu-item
            class="menu-list"
            v-if="typeof item.child === 'undefined'"
            :index="item.path">
            <i class="icon fa" :class="item.icon"></i>
            <span v-text="item.title" class="text"></span>
          </el-menu-item>
          <el-submenu
            :index="item.path"
            v-else>
            <template slot="title">
              <i class="icon fa" :class="item.icon"></i>
              <span v-text="item.title" class="text"></span>
            </template>
            <el-menu-item
              class="menu-list"
              v-for="(sub_item, sub_index) in item.child"
              :index="sub_item.path"
              :key="sub_index">
              <!--<i class="icon fa" :class="sub_item.icon"></i>-->
              <span v-text="sub_item.title" class="text"></span>
            </el-menu-item>
          </el-submenu>
        </div>
      </el-menu>
    </div>
  </div>
</template>
<script type="text/javascript">

  import * as communication from '../../common/service/communication'

  export default{
    name: 'slide',
    data(){
      return {
        nav_menu_data: []
      }
    },
    created(){
//      this.nav_menu_data = [{
//          title: "主页",
//          path: "/home",
//          icon: "fa-home"
//        }, {
//          title: "表格管理",
//          path: "/table",
//          icon: "fa-table",
//          child: [{
//            title: "基本表格",
//            path: "/table/base"
//          }, {
//            title: "排序表格",
//            path: "/table/sort"
//          }]
//        }, {
//          title: "图表管理",
//          path: "/charts",
//          icon: "fa-bar-chart-o",
//          child: [{
//            title: "柱状图表",
//            path: "/charts/bar"
//          }]
//        }]

//      this.$fetch.api_common.menuNav()
//        .then(({data}) => {
//          this.nav_menu_data = data;
//          //localStorage.setItem("myUser", "tiaffku");
//        })
//        .catch(({code, msg}) => {
//          this.$message.success(msg);
//        })

      communication.httpPost(communication.MENU_NAV, null)
        .then(({data}) => {
          this.nav_menu_data = data;
        })
        .catch(({code, msg}) => {
          this.$message.success(msg);
        })
    }
  }
</script>
