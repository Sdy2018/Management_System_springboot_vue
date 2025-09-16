<template>
  <div class="container">
    <div class="introduction_box">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span v-if="data.userData.role==='user'">个人介绍</span>
            <span v-if="data.userData.role==='admin'">账号信息</span>
          </div>
        </template>

        <el-form ref="introFormRef"  :model="data.form" :rules="data.rules">
          <div v-if="data.userData.role==='user'">
            <el-form-item class="avatar_form" >
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8083/api/files/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                  list-type="picture"
              >
                <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
            <el-form-item  prop="username"  label="账号" :label-width="'80px'">
              <el-input disabled v-model="data.form.username" autocomplete="off" />
            </el-form-item>
            <el-form-item :validate-event="true" prop="name"  label="姓名" :label-width="'80px'">
              <el-input v-model="data.form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item :validate-event="true" prop="gender"  label="性别" :label-width="'80px'">
              <el-select v-model="data.form.gender" placeholder="性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
            <el-form-item :validate-event="true" prop="age"  label="年龄" :label-width="'80px'">
              <el-input type="number" :min="18" :max="120"   placeholder="18-120"
                        @input="handleInput" v-model="data.form.age"/>
            </el-form-item>
            <el-form-item :validate-event="true" prop="hireDate"  label="入职日期" :label-width="'80px'">
              <el-date-picker
                  v-model="data.form.hireDate"
                  type="date"
                  placeholder="日期"
                  :size="'default'"
                  value-format="YYYY-MM-DD"
              />
            </el-form-item>
            <el-form-item :validate-event="true" prop="position"  label="职位" :label-width="'80px'">
              <el-input v-model="data.form.position" autocomplete="off" />
            </el-form-item>
            <el-form-item :validate-event="true" prop="salary"  label="薪资" :label-width="'80px'">
              <el-input type="number" :min="0" :max="2000000"
                        @input="handleSalaryInput" v-model="data.form.salary"/>
            </el-form-item>

            <el-form-item :validate-event="true" prop="idCard" label="身份证" maxlength="18" :label-width="'80px'">
              <el-input v-model="data.form.idCard" autocomplete="off" />
            </el-form-item>

            <el-form-item :validate-event="true" prop="email" label="邮箱"   :label-width="'80px'">
              <el-input maxlength="20" v-model="data.form.email" placeholder="请输入">
                <template #append>.com</template>
              </el-input>
            </el-form-item>

            <el-form-item :validate-event="true" prop="introduction" label="个人介绍"   :label-width="'80px'">
              <el-input
                  v-model="data.form.introduction"
                  maxlength="300"
                  placeholder="请输入"
                  show-word-limit
                  type="textarea"
                  resize="none"
                  class="introText"
                  rows="5"
              />
            </el-form-item>
          </div>

          <div  v-if="data.userData.role==='admin'">
            <el-form-item class="avatar_form" >
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8083/api/files/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                  list-type="picture"
              >
                <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
            <el-form-item :validate-event="true" prop="name"  label="账号" :label-width="'80px'">
              <el-input disabled v-model="data.form.username" autocomplete="off" />
            </el-form-item>
            <el-form-item :validate-event="true" prop="name"  label="姓名" :label-width="'80px'">
              <el-input v-model="data.form.name" autocomplete="off" />
            </el-form-item>
          </div>


        </el-form>

        <template #footer>
          <div class="left-align-container">
            <el-button @click="save" size="large" type="primary" class="button">保存</el-button>
          </div>
        </template>
      </el-card>
    </div>

  </div>

</template>

<style scoped>

.container{
  overflow: scroll;
  height: 100%;
  width: auto;
  background-color: rgba(237, 233, 233, 0.8);
}

.introduction_box{
  margin: 20px auto;
  height: auto;
  width: 900px;
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

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  border-radius: 50%;
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.avatar_form .el-form-item__content{
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>

<script setup>


import {reactive,ref} from "vue";
import {get, put} from "@/utils/reques.js";
import {ElMessage} from "element-plus";

const introFormRef=ref()

// const setup= ()=>{
//   console.log("123")
// }


const checkNameVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入姓名"))
  }else{
    callback()
  }
}

const checkGenderVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请选择性别"))
  }else{
    callback()
  }
}

const checkAgeVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入年龄"))
  }else{
    callback()
  }
}

const checkHireDateNVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入入职日期"))
  }else{
    callback()
  }
}

const checkEmailVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入邮件地址"))
  }else{
    callback()
  }
}

const checkPositionVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入职位"))
  }else{
    callback()
  }
}
const checkSalaryVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入薪水"))
  }else{
    callback()
  }
}

const checkIdCardVali=(rule,value,callback)=>{
  if(value===""||value===null){
    callback(new Error("请输入身份证"))
  }else{
    callback()
  }
}


const  data=reactive({
  form:{
    name: "",
    gender: "",
    age: "",
    hireDate: "",
    email: "",
    position: "",
    salary: "",
    idCard: "",
    avatar:"",
  },
  userData:{},
  rules:{
    name:[{
      validator:checkNameVali,
      trigger: 'blur',
    }],
    gender:[{
      validator:checkGenderVali,
      trigger: 'blur'
    }],
    age:[{
      validator:checkAgeVali,
      trigger: 'blur'
    }],
    hireDate:[{
      validator:checkHireDateNVali,
      trigger: 'blur'
    }],
    email:[{
      validator:checkEmailVali,
      trigger: 'blur'
    }],
    position:[{
      validator:checkPositionVali,
      trigger: 'blur'
    }],
    salary:[{
      validator:checkSalaryVali,
      trigger: 'blur'
    }],
    idCard:[{
      validator:checkIdCardVali,
      trigger: 'blur'
    }
    ],
  }
})

data.userData=JSON.parse(localStorage.getItem("userData"))

const load=()=>{
  if(data.userData.role==="user")
  {
    get("/api/employee/"+data.userData.id).then(res=>{
      data.form=res
      console.log(res)
    }).catch(res=>{
      ElMessage({
        message:res,
        type:"error"
      })
    })
  }else if (data.userData.role==="admin"){
    get("/api/admin/"+data.userData.id).then(res=>{
      data.form=res
      console.log(res)
    }).catch(res=>{
      ElMessage({
        message:res,
        type:"error"
      })
    })
  }
}
load()
const emit = defineEmits(['data-refreshed']); // 声明事件

const save=()=>{
  if(data.userData.role==="user"){
    introFormRef.value.validate(valid=>{
      if(valid){
        console.log(data.form)
        put("api/employee/"+data.userData.id,data.form).then(res=>{
          ElMessage({
            message: "保存成功",
            type: "success",}
          )
          emit('data-refreshed',data.form.name,data.form.avatar)
        }).catch(res=>{
          ElMessage({
            message:res,
            type:"error",
          })
        })
      }
    })
  }
  if(data.userData.role==="admin"){
    introFormRef.value.validate(valid=>{
      if(valid){
        console.log(data.form)
        put("api/admin/"+data.userData.id,data.form).then(res=>{
          ElMessage({
            message: "保存成功",
            type: "success",}
          )
          emit('data-refreshed',data.form.name,data.form.avatar)
        }).catch(res=>{
          ElMessage({
            message:res,
            type:"error",
          })
        })
      }
    })
  }

}


const handleAvatarSuccess= (res)=>{
  data.form.avatar=res.url
  console.log(data.form)
}

const  beforeAvatarUpload =(file)=>{
  if (file.type !== 'image/jpeg') {
    ElMessage.error('头像必须是PNG/JPG格式')
    return false
  }
  return true
}





</script>