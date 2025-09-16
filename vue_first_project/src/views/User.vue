
<template>
    <div class="data_table">
      <div class="box-card">
        <el-input class="input"  v-model="data.input" placeholder="请输入姓名" />
        <el-button @click="handleReachClink" type="primary">查询</el-button>
        <el-button @click="reset"  type="warning">重置</el-button>
      </div>

      <div class="box-card">
        <el-button @click="handleAdd" type="primary">新增</el-button>
        <el-button type="warning" @click="handleBatchDeleteClick">批量删除</el-button>

        <el-upload
            ref="excelUpload"
            :action="data.baseUrl+`/api/employee/csv`"
            :auto-upload="true"
            :on-success="importSuccess"
            :on-error="importError"
            :accept="'.xlsx'"
            :limit="1"
            :show-file-list="false"
            style="height: 48px;"
        >
          <el-button  color="#626aef" style="display: inline-block; margin-left: 10px; margin-right: 10px"  type="primary" @click="submitUpload">导入</el-button>
        </el-upload>


        <el-button color="#58931b"  @click="exportData" type="warning">导出</el-button>
      </div>
      <div class="box-card table_pag" >
        <el-table  v-loading="data.loading" ref="tableRef" style="width: 100%;"   :data="data.tableData" stripe max-height="600px">
          <el-table-column type="selection" width="55" />
          <el-table-column header-align="center" align="center" label="头像" width="150">
            <template #default="scope">
              <el-avatar style="margin-right: 5px" :size="50" :src="scope.row.avatar || data.circleUrl" />
            </template>
          </el-table-column>

          <el-table-column header-align="center" align="center" fixed  prop="id" label="工号"  width="150px" />
          <el-table-column header-align="center" align="center" prop="username" label="账号"  width="150px" />
          <el-table-column header-align="center" align="center" prop="name" label="姓名"   width="150px" />
          <el-table-column header-align="center" align="center" prop="gender" label="性别"  width="150px"  />
          <el-table-column header-align="center" align="center" prop="age" label="年龄"  width="150px"  />
          <el-table-column header-align="center" align="center" prop="hireDate" label="入职时间"   width="150px" />
          <el-table-column header-align="center" align="center" prop="departmentName" label="部门"   width="150px" />
          <el-table-column header-align="center" align="center" prop="position" label="职位"   width="150px" />
          <el-table-column header-align="center" align="center" prop="salary" label="薪资"   width="150px" />
          <el-table-column header-align="center" align="center" prop="idCard" label="身份证" width="150px" />
          <el-table-column header-align="center" align="center" prop="email" label="邮箱"  width="150px" />
          <el-table-column  header-align="center" align="center" fixed="right" label="操作" width="150px">
            <template   #default="scope">
              <el-button link type="danger" size="default" @click="handleDeleteClick(scope.row)">删除</el-button>
              <el-button link type="primary" size="default" @click="handleEditClick(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
            :current-page="data.pageNum"
            :page-size="data.pageSize"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="prev, pager, next, jumper"
            :total="data.total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>

  <el-dialog width="600px"  @close="addClose" v-model="data.addFormVisible" title="添加员工信息">
    <el-form ref="addFromRef" :rules="data.rules" :model="data.form">
      <el-form-item  prop="username"   label="账号" :label-width="'80px'">
        <el-input  v-model="data.form.username" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="name" label="姓名" :label-width="'80px'">
        <el-input v-model="data.form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="gender" label="性别" :label-width="'80px'">
        <el-select v-model="data.form.gender" placeholder="性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>


      <el-form-item  prop="age" label="年龄" :label-width="'80px'">
        <el-input-number v-model="data.form.age" :min="18" :max="120"  />
      </el-form-item>
      <el-form-item  prop="hireDate" label="入职日期" :label-width="'80px'">
        <el-date-picker
            v-model="data.form.hireDate"
            type="date"
            placeholder="日期"
            :size="'default'"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item prop="departmentId" label="部门"  :label-width="'80px'">
        <el-select
            :loading="data.dSelectionLoad"
            v-model=data.form.departmentId
            class="m-2"
            placeholder="选择"
            size="large"
            style="width: 240px"
        >
          <el-option
              v-for="item in data.departmentData"
              :key="item.name"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item  prop="position" label="职位" :label-width="'80px'">
        <el-input v-model="data.form.position" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="salary" label="薪资" :label-width="'80px'">
        <el-input-number v-model="data.form.salary" :min="0" :max="2000000"  />
      </el-form-item>

      <el-form-item :validate-event="true" prop="idCard" label="身份证" maxlength="18" :label-width="'80px'">
        <el-input v-model="data.form.idCard" autocomplete="off" />
      </el-form-item>

      <el-form-item :validate-event="true" prop="email" label="邮箱"   :label-width="'80px'">
        <el-input maxlength="20" v-model="data.form.email" placeholder="请输入">
          <template #append>.com</template>
        </el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="fromCancelClick">取消</el-button>
        <el-button type="primary" @click="fromConfirmClick">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>




  <el-dialog   width="600px" @close="editDialogClose" v-model="data.editFormVisible" title="编辑员工信息">
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

      <el-form :model="data.form" >
        <el-form-item  prop="username"   label="账号" :label-width="'80px'">
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
          <el-input-number v-model="data.form.age" :min="18" :max="120"  />
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

        <el-form-item prop="departmentId" label="部门"  :label-width="'80px'">
          <el-select
              :loading="data.dSelectionLoad"
              v-model=data.form.departmentId
              class="m-2"
              placeholder="选择"
              size="large"
              style="width: 240px"
          >
            <el-option
                v-for="item in data.departmentData"
                :key="item.name"
                :label="item.name"
                :value="item.id"
              />
          </el-select>
        </el-form-item>

        <el-form-item :validate-event="true" prop="position"  label="职位" :label-width="'80px'">
          <el-input v-model="data.form.position" autocomplete="off" />
        </el-form-item>
        <el-form-item :validate-event="true" prop="salary"  label="薪资" :label-width="'80px'">
          <el-input-number v-model="data.form.salary" :min="0" :max="2000000"  />
        </el-form-item>

        <el-form-item :validate-event="true" prop="idCard" label="身份证" maxlength="18" :label-width="'80px'">
          <el-input v-model="data.form.idCard" autocomplete="off" />
        </el-form-item>

        <el-form-item :validate-event="true" prop="email" label="邮箱"   :label-width="'80px'">
          <el-input maxlength="20" v-model="data.form.email" placeholder="请输入">
            <template #append>.com</template>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateCancelClick">取消</el-button>
        <el-button type="primary" @click="updateConfirmClick">
          更新
        </el-button>
      </span>
      </template>
  </el-dialog>

<!--  单个删除-->
  <el-dialog
      v-model="data.confirmDeleteDiaVisible"
      title="提示"
      width="30%"
  >
    <span>确定删除吗</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.confirmDeleteDiaVisible = false">取消</el-button>
        <el-button type="primary" @click="handleConfirmDeleteClick">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>


  <el-dialog
      v-model="data.batchDeleteDiaVisible"
      title="提示"
      width="30%"

  >
    <span>确定删除吗</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.batchDeleteDiaVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmBatchDeleteClick">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>


</template>

<style scoped>
.data_table{
  padding: 10px;
}
.box-card {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  display: flex;
  border-radius: 5px; /* 添加圆角 */
  align-items: center;
  padding: 10px;
  box-shadow:0 0 10px rgba(0,0,0,0.25);
  margin-bottom: 10px;
}

.table_pag{
  width: 100%;
  flex-direction: column;
  align-items: flex-start;
}

.input{
  width: 200px;
  margin: 0 20px 0 0;
}


.el-table th,
.el-table td {
  flex: 1;
  min-width: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}

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

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>

<script setup>

import {ref, onBeforeMount, onMounted, reactive} from "vue";
import request, {batchDel, get} from "@/utils/reques.js";
import {ElMessage, ElTable} from 'element-plus'
import axios from "axios";



const  data=reactive({
  dSelectionLoad:true,
  baseUrl:import.meta.env.VITE_API_BASE_URL,
  loading:true,
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  tableData:[],
  pageNum:1,
  pageSize:10,
  total:null,
  input:"",
  addFormVisible:false,
  form:{},
  confirmDeleteDiaVisible:false,
  row:{},
  editFormVisible:false,
  ids:[],
  batchDeleteDiaVisible:false,
  rules:{
    username:[{
      required: true, message: '请输入账号', trigger: 'blur'
    }],
    name:[{
      required: true, message: '请输入名称', trigger: 'blur'
    }],
    gender:[{
      required: true, message: '请选择性别', trigger: 'blur'
    }],
    age:[{
      required: true, message: '请选择年龄', trigger: 'blur'
    }],
    hireDate:[{
      required: true, message: '请输入入职日期', trigger: 'blur'
    }],
    email:[{
      required: true, message: '请输入电子邮件', trigger: 'blur'
    }],
    position:[{
      required: true, message: '请输入职位', trigger: 'blur'
    }],
    salary:[{
      required: true, message: '请输入薪水', trigger: 'blur'
    }],
    idCard:[{
      required: true, message: '请输入身份证', trigger: 'blur'
    }],
  }
})
const tableRef = ref()
const addFromRef=ref()
  onBeforeMount(()=>{
  })

  onMounted(()=>{
    load()
  })

const handleInput=(value)=>{
  // 强制转换为数字
  let num = Number(value);
  // 修正范围
  num = Math.min(Math.max(num, 0), 120);
  data.form.age = num;
}


const handleSalaryInput=(value)=>{
  // 强制转换为数字
  let num = Number(value);
  // 修正范围
  num = Math.min(Math.max(num, 0), 1200000);
  data.form.salary = num;
}

const fromCancelClick=()=>{
  data.addFormVisible=false
  addFromRef.value.resetFields()
  data.form={}
}

const handleDeleteClick=(value)=>{
  data.confirmDeleteDiaVisible=true
  data.form=value
}

const handleEditClick=(value)=>{
  data.form=JSON.parse(JSON.stringify(value))
  data.editFormVisible=true
  requestDepartment()
}
const updateCancelClick=()=>{
  data.editFormVisible=false
}
// 确认更新
const updateConfirmClick=()=>{

  request.put("/api/employee/"+data.form.id,data.form).then(res=>{
    ElMessage({
      message: '更新成功',
      type: 'success',
    })
    data.form={}
    data.editFormVisible=false
    load({
      page:data.pageNum
    })
  }).catch(res=>{
    ElMessage({
      message: res,
      type: 'error',
    })
  })
}


const handleConfirmDeleteClick=()=>{
  request.delete("/api/employee/"+data.form.id).then(res=>{
    ElMessage({
      message: '删除成功',
      type: 'success',
    })
    data.form={}
    data.confirmDeleteDiaVisible=false
    load({
      page:data.pageNum
    })
  }).catch(res=>{
    console.log(res)
  })
}

const fromConfirmClick=()=>{

  addFromRef.value.validate((valid)=>{
    if (valid){
      data.form.email=data.form.email+".com"
      request.post("/api/employee",data.form).then(res=>{
        ElMessage({
          message: '添加成功',
          type: 'success',
        })
        load()
      }).catch(res=>{
        ElMessage({
          message:'业务异常',
          type: 'error',
        })
      }).finally(()=>{
        data.addFormVisible=false
        data.form={}
          }
      )
    }
  })
    // addFromRef.validate((res)=>{
    //   console.log(addFromRef.value)
    // })
}

const handleBatchDeleteClick=()=>{
 data.ids=tableRef.value.getSelectionRows().map(row=>{
   return row.id;
 })

  if (data.ids.length===0){
    ElMessage({
      message:"请先选择删除数据",
      type:"warning"
    })
  }else {
    data.batchDeleteDiaVisible=true
  }

}

const confirmBatchDeleteClick=()=>{
  request.delete("/api/employee",{data:data.ids}).then(res=>{
    console.log(res)
    data.ids=[]
    ElMessage({
      message:"删除成功",
      type:"success"
    })
    data.batchDeleteDiaVisible=false
    load({page:data.pageNum})
  })
}


const requestDepartment=({ page = 1, size = 10 } = {})=>{
  request.get("/api/department/pages",{
    params:{
      page:page,
      size:size,
    }
  }).then((res)=>{
    data.departmentData=res.list
    console.log(data.departmentData)
  }).finally(
      data.dSelectionLoad=false
  )
}


// pageNum
// pageSize
// total
const load=({ page = 1, size = 10 } = {})=>{
    data.loading=true
    request.get("/api/employee/pages",{
      params:{
        page:page,
        size:size,
      }
    }).then((res)=>{
      data.tableData=res.list
      data.pageNum=res.pageNum
      data.pageSize=res.pageSize
      data.total=res.total
      console.log(data.tableData)
    }).finally(
        data.loading=false
    )
}


const addClose=()=>{
  data.form={}
  addFromRef.value.resetFields()
  data.dSelectionLoad=true
}



  const small = ref(false)
  const background = ref(false)
  const disabled = ref(false)

  const handleSizeChange = (val) => {
    console.log(`${val} items per page`)
  }
  const handleCurrentChange = (val) => {
    load({page:val})
  }

const handleReachClink =()=>{
    request("/api/employees",{
      params:{
        name:data.input
      }
    }).then((res)=>{
      data.tableData=res
    })
}
const reset=()=>{
  load()
  data.input=""
}


const handleAvatarSuccess= (res)=>{
  data.form.avatar=res.url
}

const  beforeAvatarUpload =(file)=>{
  if (file.type !== 'image/jpeg') {
    ElMessage.error('头像必须是PNG/JPG格式')
    return false
  }
  return true
}

const exportData=()=>{
  axios.get(import.meta.env.VITE_API_BASE_URL+'/api/employee/csv', {
    responseType: 'blob', // 必需：声明二进制流
    headers: {
      'Accept': 'application/vnd.ms-excel', // 可选：明确期望文件类型
      'Authorization': 'Bearer token' // 按需：身份验证
    }
  }).then(response => {
    // console.log(response)
    // 创建Blob并触发下载
    const blob = new Blob([response.data], { type: response.headers['content-type'] });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = '员工列表.xlsx'; // 文件名与后端一致
    a.click();
    URL.revokeObjectURL(url);
  }).finally(()=>{
  });
}

const importSuccess=(res)=>{
  ElMessage.success("导入成功")
  load()
}


const importError=(res,file)=>{
  ElMessage.error("导入失败")
}

const excelUpload=ref()

const submitUpload=()=> {
  excelUpload.value.submit()
}

const editDialogClose=()=>{
  data.form={}
  data.dSelectionLoad=true
}

const handleAdd=()=>{
  data.addFormVisible=true
  requestDepartment()
}


</script>


