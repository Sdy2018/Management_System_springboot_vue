<template>
  <div class="container">
    <div class="resetPass_box">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span >密码重置</span>
          </div>
        </template>
        <el-form ref="resetFormRef" :model="data.form" :rules="data.rules">
          <el-form-item :validate-event="true" prop="password"  label="原始密码" :label-width="'80px'">
            <el-input  show-password v-model="data.form.password" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item :validate-event="true" prop="resetPassword"  label="重置密码" :label-width="'80px'">
            <el-input  show-password v-model="data.form.resetPassword" type="password"  autocomplete="off" />
          </el-form-item>
          <el-form-item :validate-event="true" prop="reCheckReset"  label="再次确认" :label-width="'80px'">
            <el-input  show-password v-model="data.form.reCheckReset"  type="password"  autocomplete="off" />
          </el-form-item>
        </el-form>

        <template #footer>
          <div class="left-align-container">
            <el-button @click="reset" size="large" type="primary" class="button">保存</el-button>
          </div>
        </template>
      </el-card>
    </div>

  </div>
</template>

<style scoped>


.container{
  height: 100%;
  width: auto;
  background-color: rgba(237, 233, 233, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;

}

.resetPass_box{
  width: 600px;
  display: flex;
  align-items: center;
}





.box-card {
  padding: 20px 80px;
  width: 100%;

}

.left-align-container {
  display: flex;              /* 启用 Flex 布局 */
  justify-content: flex-end;/* 主轴左对齐 */
  gap: 8px;                   /* 按钮间距 */
  padding-left: 20px;         /* 左侧内边距 */
}

.fixed-height-textarea {
  height: 120px; /* 设置固定高度 */
}

</style>

<script setup>
import {reactive,ref,onBeforeUnmount,h} from "vue";
import {get, put} from "@/utils/reques.js";
import {ElMessage} from "element-plus";
import {showLoading,hideLoading} from '@/utils/loading.js'
import { useRouter } from 'vue-router';

const  router=useRouter()

const resetFormRef=ref()




// const setup= ()=>{
//   console.log("123")
// }


const checkPass=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入原密码"))
  }else{
    callback()
  }
}

const checkResetPass=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入重置密码"))
  }else{
    callback()
  }
}

const confirmPass=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请再次输入重置密码"))
  }else if (value!==data.form.resetPassword){
    callback(new Error("两次密码输入不一致"))
  }else{
    callback()
  }
}


const  data=reactive({
  form:{
    password:"",
    resetPassword:"",
    reCheckReset:""
  },
  rules:{
    password:[{
      validator:checkPass,
      trigger: 'blur',
    }],
    resetPassword:[{
      validator:checkResetPass,
      trigger: 'blur'
    }],
    reCheckReset:[{
      validator:confirmPass,
      trigger: 'blur'
    }]
  }
})

const userData=JSON.parse(localStorage.getItem("userData"))

data.form.role=userData.role

data.form.id=userData.id

let timer = null

const reset=()=>{
  resetFormRef.value.validate(valid=>{
    if(valid){
      console.log(data.form)
        put("/api/reset",data.form).then(res=>{
          ElMessage({
            message: h('p', null, [
              h('span', null, '重置成功,'),
              h('i', {
                style: {
                  color: '#67C23A', // 使用成功色
                  fontWeight: '500',
                  marginRight: '4px'
                }
              }, '3s后跳转'),
            ]),
            type: 'success', // 自动匹配主题色
            duration: 2000
          })
          timer = setTimeout(() => {
            showLoading({text:''})
          }, 300)

          timer = setTimeout(() => {
            hideLoading()
            router.replace("/login")
            // window.location.href= "/login"
            localStorage.removeItem("userData")
          }, 3000)


        }).catch(res=>{
          ElMessage({
            message:res,
            type:"error",
          })
        })
    }
  })
}

onBeforeUnmount(() => {
  if (timer) clearTimeout(timer)
})
</script>