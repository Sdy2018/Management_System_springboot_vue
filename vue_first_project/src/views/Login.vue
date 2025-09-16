<template>
  <div class="container_box">
    <div class="right-div">
      <div class="login_box">
            <div class="login_content">
              <p  class="title">欢迎登录后台管理系统</p>

              <el-form
                  :model="data.form"
                  style="max-width: 600px"
                  :rules="data.rules"
                  ref="logFromRef"
                  label-width="55px"
              >
                <el-form-item   label="用户名" prop="username" style="margin-top: 20px">
                  <el-input   v-model="data.form.username" placeholder="请输入用户名">
                    <!-- 左侧插入 Check 图标 -->
                    <template #prefix>
                      <el-icon><User /></el-icon>
                    </template>

                    <!-- 右侧插入 Check 图标 -->
                    <!-- <template #suffix>
                      <el-icon><Check /></el-icon>
                    </template> -->
                  </el-input>
                </el-form-item>

                <el-form-item label="密码" prop="password" style="margin-top: 30px">
                  <el-input   show-password v-model="data.form.password" placeholder="请输入密码">
                    <!-- 左侧插入 Check 图标 -->
                    <template #prefix>
                      <el-icon><Lock /></el-icon>
                    </template>
                    <!-- 右侧插入 Check 图标 -->
                    <!-- <template #suffix>
                      <el-icon><Check /></el-icon>
                    </template> -->
                  </el-input>
                </el-form-item>

                <el-form-item label="角色" prop="role" style="margin-top: 30px">
                  <el-select  v-model="data.form.role" placeholder="角色">
                    <el-option label="用户" value="user" />
                    <el-option label="管理员" value="admin" />
                  </el-select>
                </el-form-item>


              </el-form>

              <div class="loginButton">
                <el-button @click="handelLoginClick" style="width: 100%" color="blue" type="primary">
                  登录
                </el-button>
              </div>
              <div class="register-text">还没有账号？请<a class='register' @click="registerClick">注册</a></div>

            </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.register:hover{
  cursor:pointer;
}

.container_box{
  height: 100vh;
  width: 100%;
  background-image: url("@/assets/bg2.jpg");
  background-size: cover;
  position: absolute;
}

.right-div {
  width: 50%; /* 直接设置宽度为 50% */
  height: 100%;
  margin-left: 50%;
  background-color: #4782f3;
  background-image: url("@/assets/bg3.jpg");
  background-size: cover;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.login_box{
  height: 50%;
  width: 500px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  min-width: 400px;
}

.login_content{
  height: auto;
  width: 70%;
  display: flex;
  flex-direction: column;
}

.title{
  text-align: center;
  font-size: 24px;
  color: blue;
  font-weight: bold;
  margin-bottom: 5px;
  margin-top: 0px;
}

.loginButton{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.register-text{
  text-align: right;
  font-size: 14px;
  margin-top:5px;
}

.register-text a {
  text-decoration: none;
  color: blue;
}

</style>



<script setup >

import {reactive, ref} from "vue";
import {post} from "@/utils/reques.js";
import {ElMessage, ElMessageBox} from "element-plus";
import { useRouter } from 'vue-router';

const  router=useRouter()

const logFromRef=ref()

const registerClick=()=>{
  router.push("register")
}

const checkUsername=(rule,value,callback)=>{
    if(value===""){
      callback(new Error('请输入用户名'))
    }else{
      callback()
    }
}

const checkPassword=(rule,value,callback)=>{
  if(value===""){
    callback(new Error('请输入密码'))
  }else{
    callback()
  }
}



const data=reactive({
  form:{
    username:"",
    password:"",
    role:"user"
  },
  rules:{
    username:[{
      validator:checkUsername,
      trigger: 'blur'

    }],
    password:[{
      validator:checkPassword,
      trigger: 'blur'
    }]
  }
})


const handelLoginClick=()=>{
  logFromRef.value.validate(valid=>{
    if (valid){
      console.log(data.form)
      post("/api/login",data.form).then(res=>{
        console.log(res)
        localStorage.setItem('userData', JSON.stringify(res));
        router.push('/');

      }).catch(res=>{
        ElMessage({
          message:res,
          type:"error"
        })
      })
    }
  })

}

</script>