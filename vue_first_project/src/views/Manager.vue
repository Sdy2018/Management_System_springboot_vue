
<template>
<!--    头部-->
  <div class="parent">
    <div  class="custom-header ">
      <div  class="header-left">
<!--        <img src="logo.png" alt="系统Logo" class="logo-icon"> &lt;!&ndash; 雪花图标/自定义logo &ndash;&gt;-->
        <img src="../assets/orange.svg" style="margin-right: 10px;height: 38px;width: 38px" alt="Logo" class="logo-icon">
        <span >后台管理系统</span>
      </div>
      <div class="header-right">
        <div class="user-info">
          <el-avatar style="margin-right: 5px" :size="50" :src="data.userData.avatar|| data.circleUrl  " />
        </div>
<!--        <div style="display: block;">-->
          <span class="user-name">{{data.userData.name}}</span>
<!--        </div>-->
      </div>
    </div>

    <div class="mid-content">
      <el-menu
          :default-active=data.active
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          :default-openeds="['3']"
          router="true"
          @select="handleSelect"
      >
        <el-menu-item index="sys_home">
          <el-icon><location /></el-icon>
          <span>系统首页</span>
        </el-menu-item>


        <el-menu-item index="statistic">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>

        <el-menu-item v-if="data.userData.role==='admin'" index="article">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item  v-if="data.userData.role==='admin'" index="department">
          <el-icon><Document /></el-icon>
          <span>部门管理</span>
        </el-menu-item>

        <el-sub-menu v-if="data.userData.role==='admin'"  index="3">
          <template #title>
            <el-icon><location /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item  index="admin">管理员信息</el-menu-item>

          <el-menu-item   index="user">员工信息</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="personInfo">
          <el-icon><setting /></el-icon>
          <span>个人信息</span>
        </el-menu-item>
        <el-menu-item index="resetPass">
          <el-icon><Lock /></el-icon>
          <span>密码重置</span>
        </el-menu-item>
        <el-menu-item @click="toLogin" >
          <el-icon><setting /></el-icon>
          <span>退出登录</span>
        </el-menu-item>
        </el-menu>

      <div class="data_show" >
        <router-view  @data-refreshed="fetchData"/>
      </div>

    </div>

<!--    <footer class="footer">-->
<!--      <p>© 2025 MySite. 保留所有权利.</p>-->
<!--    </footer>-->

  </div>

<!--  中间-->

</template>

<style scoped>

body{
  overflow: hidden
}

div{
  overflow: hidden
}

.custom-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  width: 100%;
  background-color: #4782f3;
}
/* 左侧区域样式 */
.header-left {
  display: flex;
  align-items: center;
  margin-left: 15px;
  color: azure;
}

.header-right {
  margin-right: 20px;
  color: azure;
  display: flex;
  justify-content: center;
  align-items: center;
}

.parent{
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-size: 25px;
}

.mid-content{
  display: flex;
  flex: 1;
  width: auto;
}

.data_show{
  width: 100%; /* 占用50%的空间 */
  height: 100%;
  overflow: auto;
  background-color: rgba(211, 211, 211, 10%);
}


</style>
<script setup>

  import { reactive } from 'vue'
  import { useRoute } from 'vue-router'
  import router from "@/router/index.js"
  import {DataAnalysis, Document, Location, Lock, Setting, SwitchButton} from "@element-plus/icons-vue";

  const route = useRoute() // 获取当前路由信息


  const data=reactive({
    active:route.name,
    username:"",
    circleUrl:
        'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  })


  const toLogin=()=>{
    localStorage.removeItem("useData")
    router.push("/login")
  }

  data.userData= JSON.parse(localStorage.getItem("userData"))

  const fetchData=(name,avatar)=>{
    data.userData.name=name
    data.userData.avatar=avatar
    localStorage.setItem("userData",JSON.stringify(data.userData))
  }


  // console.log(route.path)
  // const handleSelect=(index,path)=>{
  //
  // }

  // // 导入 useRoute 函数
  // import { useRoute } from 'vue-router'
  //
  // // 创建路由实例
  // const route = useRoute()
  //
  // // 获取 URL 中的参数
  // const id = route.params.id  // 会得到 "789"
  // const color = route.query.color  // 会得到 "black"
  //
  // // 如果需要使用这些参数，可以直接使用
  // console.log('ID:', route.params.id )  // 输出: ID: 789
  // console.log('Color:', color)  // 输出: Color: black
  // console.log('Hash:', route.hash)  // 输出: Hash: #specs

  // 也可以将它们暴露给模板使用
  // (不写return的话，变量也会自动暴露给模板)
</script>
