
<template>
  <div>
<!--    <div class="box-card">-->
<!--      <el-input class="input"  v-model="data.input" placeholder="请输入姓名" />-->
<!--      <el-button @click="handleReachClink" type="primary">查询</el-button>-->
<!--      <el-button @click="reset"  type="warning">重置</el-button>-->
<!--    </div>-->

    <div class="box-card">
      <el-button @click="data.addFormVisible=true" type="primary">新增</el-button>
      <el-button type="warning" @click="handleBatchDeleteClick">批量删除</el-button>
<!--      <el-button color="#9645ea">导入</el-button>-->
<!--      <el-button color="#58931b" type="warning">导出</el-button>-->
    </div>
    <div class="box-card table_pag">
      <el-table v-loading="data.loading" ref="tableRef"  :data="data.tableData" stripe max-height="600px">
        <el-table-column type="selection"  />
        <el-table-column prop="name" label="名称"  />
        <el-table-column fixed="right" label="操作" >
          <template #default="{row}">
            <el-button link type="danger" size="default" @click="handleDeleteClick(row)">删除</el-button>
            <el-button link type="primary" size="default" @click="handleEditClick(row)">编辑</el-button>
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

  <el-dialog width="30%"  @close="addClose" v-model="data.addFormVisible" title="添加部门信息">
    <el-form ref="addFromRef" :rules="data.rules" :model="data.form">
      <el-form-item  prop="username" label="部门名称" :label-width="'80px'">
        <el-input v-model="data.form.name" autocomplete="off" />
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




  <el-dialog width="30%" @close="data.form={}" v-model="data.editFormVisible" title="编辑部门信息">



    <el-form :model="data.form" >
      <el-form-item  prop="name" label="名称" :label-width="'80px'">
        <el-input v-model="data.form.name" autocomplete="off" />
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
      :before-close="handleClose"
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
      :before-close="handleClose"
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
.box-card {
  width: auto;
  height: 100%;
  margin: 10px;
  display: flex;
  border-radius: 5px; /* 添加圆角 */
  align-items: center;
  padding: 10px;
  box-shadow:0px 0px 10px rgba(0,0,0,0.25)
}

.table_pag{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.input{
  margin: 0;
  width: 200px;
  margin-right:20px;
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
import request, {batchDel} from "@/utils/reques.js";
import {ElMessage, ElTable} from 'element-plus'




const  data=reactive({
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
    name:[{
      required: true, message: '请输入部门名称', trigger: 'blur'
    }]
  }
})
const tableRef = ref()
const addFromRef=ref()
onBeforeMount(()=>{
})

onMounted(()=>{
  load()
})





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
}
const updateCancelClick=()=>{
  data.editFormVisible=false
}
// 确认更新
const updateConfirmClick=()=>{
  request.put("/api/department/"+data.form.id,data.form).then(res=>{
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
  request.delete("/api/department/"+data.form.id).then(res=>{
    ElMessage({
      message: '删除成功',
      type: 'success',
    })
    data.form={}
    data.confirmDeleteDiaVisible=false
    load({
      page:data.pageNum
    })
  })
}

const fromConfirmClick=()=>{
  // addFromRef.validate((valid)=>{
  //   if (valid){
  //     console.log("验证成功")
  //   }
  addFromRef.value.validate((valid)=>{
    if (valid){
      request.post("/api/department",data.form).then(res=>{
        ElMessage({
          message: '添加成功',
          type: 'success',
        })
        load()
      })
      data.addFormVisible=false
      data.form={}
    }
  })
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
  request.delete("/api/department",{data:data.ids}).then(res=>{
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


const load=({ page = 1, size = 10 } = {})=>{
  data.loading=true
  request.get("/api/department/pages",{
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
  }).finally( ()=>{
    data.loading=false
  }
  )
}


const addClose=()=>{
  data.form={}
  addFromRef.value.resetFields()
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
  request("/api/department/pages",{
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


</script>


