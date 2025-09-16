
<template>
  <div>
    <div class="box-card">
      <el-input class="input"  v-model="data.input" placeholder="请输入姓名" />
      <el-button @click="handleReachClink" type="primary">查询</el-button>
      <el-button @click="reset"  type="warning">重置</el-button>
    </div>

    <div class="box-card">
      <el-button @click="data.addFormVisible=true" type="primary">新增</el-button>
      <el-button type="warning" @click="handleBatchDeleteClick">批量删除</el-button>
      <el-button color="#9645ea">导入</el-button>
      <el-button color="#58931b" type="warning">导出</el-button>
    </div>
    <div class="box-card table_pag">
      <el-table ref="tableRef" @getSelectionRows="getSelectionRows" :data="data.tableData" stripe style="height: 450px">
        <el-table-column type="selection" width="55" />
        <el-table-column fixed  prop="employeeId" label="工号"  width="100" />
        <el-table-column prop="name" label="姓名"  />
        <el-table-column prop="gender" label="性别"  />
        <el-table-column prop="age" label="年龄"  />
        <el-table-column prop="hireDate" label="入职时间"  />
        <el-table-column prop="position" label="职位"  />
        <el-table-column prop="salary" label="薪资"  />
        <el-table-column prop="idCard" label="身份证" width="250" />
        <el-table-column  prop="email" label="邮箱"  width="250" />
        <el-table-column fixed="right" label="操作" width="150">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="handleDeleteClick(scope.row)">删除</el-button>
            <el-button link type="primary" size="small" @click="handleEditClick(scope.row)">编辑</el-button>
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

  <el-dialog width="30%"  @close="addClose" v-model="data.addFormVisible" title="添加员工信息">
    <el-form ref="addFromRef" :rules="data.rules" :model="data.form">
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
        <el-input type="number" :min="18" :max="120"   placeholder="18-120"
                  @input="handleInput" v-model="data.form.age"/>
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
      <el-form-item  prop="position" label="职位" :label-width="'80px'">
        <el-input v-model="data.form.position" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="salary" label="薪资" :label-width="'80px'">
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




  <el-dialog width="30%" @close="data.form={}" v-model="data.editFormVisible" title="编辑员工信息">
    <el-form :model="data.form" >
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
</style>

<script setup>

import {ref, onBeforeMount, onMounted, reactive} from "vue";
import request, {batchDel} from "@/utils/reques.js";
import {ElMessage, ElTable} from 'element-plus'




const  data=reactive({
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
}
const updateCancelClick=()=>{
  data.editFormVisible=false
}
// 确认更新
const updateConfirmClick=()=>{
  request.put("/api/employee/"+data.form.employeeId,data.form).then(res=>{
    ElMessage({
      message: '更新成功',
      type: 'success',
    })
    data.form={}
    data.editFormVisible=false
    load({
      page:data.pageNum
    })
  })
}


const handleConfirmDeleteClick=()=>{
  request.delete("/api/employee/"+data.form.employeeId).then(res=>{
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
      data.form.email=data.form.email+".com"
      request.post("/api/employee/",data.form).then(res=>{
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
  // addFromRef.validate((res)=>{
  //   console.log(addFromRef.value)
  // })
}

const handleBatchDeleteClick=()=>{
  data.ids=tableRef.value.getSelectionRows().map(row=>{
    return row.employeeId;
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
  request.delete("/api/employee/batchDelete",{data:data.ids}).then(res=>{
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

// pageNum
// pageSize
// total
const load=({ page = 1, size = 10 } = {})=>{
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
  })
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


</script>


