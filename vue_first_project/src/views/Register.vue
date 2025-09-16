<template>
  <div class="container_box">
    <div class="right-div">
      <div class="login_box">
        <div class="login_content">
          <H6  class="title">欢迎注册</H6>

          <el-form
              :model="data.form"
              style="max-width: 460px"
              :rules="data.rules"
              ref="regisFromRef"
              status-icon
          >
            <el-form-item prop="username" style="margin-top: 20px">
              <el-input  v-model="data.form.username" placeholder="请输入用户名">
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

            <el-form-item prop="password" style="margin-top: 30px">
              <el-input  show-password v-model="data.form.password" placeholder="请输入密码">
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

            <el-form-item prop="checkPassword" style="margin-top: 30px">
              <el-input  show-password v-model="data.form.checkPassword" placeholder="请确认输入密码">
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
          </el-form>


          <div class="registerButton">
            <el-button @click="handelRegisterClick" style="width: 100%" color="blue" type="primary">
              注册
            </el-button>
          </div>
          <div class="register-text">已有账号？<a class="login-txt" @click="registerClick">立即登录</a></div>

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
  height: 500px;
  width: 500px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
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

.login-txt:hover{
  cursor: pointer;
}

</style>



<script setup >

import {reactive, ref} from "vue";

import { useRouter } from 'vue-router';
import {useRouterInstance} from "@/utils/userRouterInstance.js";
import {post} from "@/utils/reques.js";
import {ElMessage} from "element-plus";
const regisFromRef=ref()

const  router=useRouter()


const registerClick=()=>{
  router.back()
}

const validateRePassword = (rule, value, callback) => {
  if (value !== data.form.password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};


const data=reactive({
  form:{
    username:"",
    password:"",
  },
  rules:{
    username:[{
      required: true,
      message: '请输入用户名',
      trigger: 'blur'

    }],
    password:[
      {required: true, message: '请输入密码', trigger: 'blur'},
      { min: 6, max: 20, message: '密码长度在6到20个字符之间', trigger: 'blur' }
    ],
    checkPassword:[{
      required: true,
      message: '请再次确认密码',
      trigger: 'blur'
    },
      { validator: validateRePassword, trigger: 'blur' }
    ],
  }
})


const handelRegisterClick=()=>{
  regisFromRef.value.validate(valid=>{
    if(valid){
      post("/api/register",data.form).then(res=>{
        ElMessage({
          message:"注册成功",
          type:"success"
        })
        // router.push("/login")
        router.replace("login")
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