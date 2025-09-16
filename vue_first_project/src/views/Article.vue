
<template>
  <div>
    <div class="box-card">
      <el-input class="input"  v-model="data.input" placeholder="请输入姓名" />
      <el-button @click="handleReachClink" type="primary">查询</el-button>
      <el-button @click="reset"  type="warning">重置</el-button>
    </div>

    <div class="box-card">
      <el-button @click="handleAddClick" type="primary">新增</el-button>
      <el-button type="warning" @click="handleBatchDeleteClick">批量删除</el-button>
    </div>
    <div class="box-card table_pag">
      <el-table ref="tableRef" v-loading="data.loading" :data="data.tableData" stripe max-height="600px">
        <el-table-column type="selection"  />
        <el-table-column fixed  prop="title" label="标题"   />
        <el-table-column label="封面" #default="{row}">
          <el-image
              style="width: 100px; height: 100px"
              :src="row.image"
              :zoom-rate="1.2"
              :max-scale="10"
              :min-scale="0.5"
              :preview-src-list="[row.image]"
              :initial-index="0"
              fit="cover"
              preview-teleported
          />

        </el-table-column>
        <el-table-column   show-overflow-tooltip  prop="describe" label="简介"   />
        <el-table-column  prop="author" label="作者"  />
          <el-table-column   prop="content" label="内容"  >
            <template #default="{row}">
            <el-button link type="primary" size="default" @click="handleShowClick(row)">查看内容</el-button>
            </template>
          </el-table-column>
        <el-table-column fixed="right" label="操作" >
          <template #default="{row}">
            <el-button  type="danger" size="default" @click="handleDeleteClick(row)">删除</el-button>
            <el-button  type="primary" size="default" @click="handleEditClick(row)">编辑</el-button>
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

  <el-dialog style="padding-right: 50px" width="800px"  @close="addClose" v-model="data.addFormVisible" title="添加文章信息">
    <el-form ref="addFromRef" :rules="data.rules" :model="data.form">
      <el-form-item  prop="title" label="标题" :label-width="'80px'">
        <el-input v-model="data.form.title" autocomplete="off" />
      </el-form-item>

      <el-form-item  prop="describe" label="简介" :label-width="'80px'">
        <el-input
            v-model="data.form.describe"
            maxlength="300"
            placeholder="请输入"
            show-word-limit
            type="textarea"
            resize="none"
            class="introText"
            rows="5"
        />
      </el-form-item>

      <el-form-item  prop="image" label="封面" :label-width="'80px'">
        <el-upload
            ref="uploadRef"
            v-model="data.uploadImages"
            class="upload-demo"
            :action="data.baseUrl+'/api/files/upload'"
            :on-success="handleImageSuccess"
            :before-upload="beforeImageUpload"
            list-type="picture"
            :limit="1"
        >
          <el-button type="primary">上传</el-button>
          <template #tip>
<!--            <div class="el-upload__tip">-->
<!--              图片格式为jpg/png-->
<!--            </div>-->
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item  prop="author" label="作者" :label-width="'80px'">
        <el-input v-model="data.form.author" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="content" label="内容" :label-width="'80px'">
        <EditorComponent :visible="data.addFormVisible" v-model:content="data.form.content"/>

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




  <el-dialog width="800px" @close="data.form={}" v-model="data.editFormVisible" title="编辑文章信息">
    <el-form ref="addFromRef" :rules="data.rules" :model="data.form">
      <el-form-item  prop="title" label="标题" :label-width="'80px'">
        <el-input v-model="data.form.title" autocomplete="off" />
      </el-form-item>

      <el-form-item  prop="describe" label="简介" :label-width="'80px'">
        <el-input
            v-model="data.form.describe"
            maxlength="300"
            placeholder="请输入"
            show-word-limit
            type="textarea"
            resize="none"
            class="introText"
            rows="5"
        />
      </el-form-item>

      <el-form-item  prop="image" label="封面" :label-width="'80px'">
        <el-upload
            :file-list="[{
              'url':data.form.image
            }]"
            ref="uploadRef"
            class="upload-demo"
            :action="data.baseUrl+'/api/files/upload'"
            :on-success="handleImageSuccess"
            :before-upload="beforeImageUpload"
            list-type="picture"
            :limit="1"
        >
          <el-button type="primary">上传</el-button>
          <template #tip>
            <!--            <div class="el-upload__tip">-->
            <!--              图片格式为jpg/png-->
            <!--            </div>-->
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item  prop="author" label="作者" :label-width="'80px'">
        <el-input v-model="data.form.author" autocomplete="off" />
      </el-form-item>
      <el-form-item  prop="content" label="内容" :label-width="'80px'">
        <EditorComponent :visible="data.editFormVisible"  v-model:content="data.form.content"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateCancelClick">取消</el-button>
        <el-button type="primary" @click="updateConfirmClick">
          确认
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

  <el-dialog v-model="data.articleDialogVisible" :title="data.articleInfo.title" width="1000px" center>
    <div class="editor-content-view">
      <div v-html="data.articleInfo.content"></div>
    </div>


    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="data.articleDialogVisible = false">
          关闭
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

.Image-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  border-radius: 50%;
}

.Image-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.Image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.Image_form .el-form-item__content{
  display: flex;
  justify-content: center;
  align-items: center;
}

.Image-uploader .Image {
  width: 178px;
  height: 178px;
  display: block;
}

</style>



<script setup>

import {ref, onBeforeMount, onMounted, reactive, shallowRef, onBeforeUnmount} from "vue";
import request, {batchDel} from "@/utils/reques.js";
import {ElMessage, ElTable} from 'element-plus'
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import EditorComponent from "@/components/EditorComponent.vue";





const  data=reactive({
  loading:true,
  baseUrl:import.meta.env.VITE_API_BASE_URL,
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  tableData:[],
  articleInfo:{

  },
  uploadImages:[],
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
  articleDialogVisible:false,
  rules:{
    title:[{
      required: true, message: '请输入标题', trigger: 'blur'
    }],
    describe:[{
      required: true, message: '请输入简介', trigger: 'blur'
    },],
    content:[{
      required: true, message: '请输入内容', trigger: 'blur'
    },],
    author:[{
      required: true, message: '请输入作者', trigger: 'blur'
    },],
    image:[{
      required: true, message: '请上传封面', trigger: 'blur'
    },]
  }
})
const tableRef = ref()
const addFromRef=ref()





const handleAddClick=()=>{
  data.addFormVisible=true
  // editorRef.value.restoreSelection();
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
  console.log(data.form)
}
const updateCancelClick=()=>{
  data.editFormVisible=false
}
// 确认更新
const updateConfirmClick=()=>{
  console.log(data.form)
  request.put("/api/article/"+data.form.id,data.form).then(res=>{
    console.log(res)
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
  request.delete("/api/article/"+data.form.id).then(res=>{
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
  console.log(data.form)
  addFromRef.value.validate((valid)=>{
    if (valid){
      console.log("----------")
      request.post("/api/article",data.form).then(res=>{
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
  request.delete("/api/article",{data:data.ids}).then(res=>{
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
  request.get("/api/article/pages",{
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
  }).finally(()=>{
        data.loading=false
  }
  )
}

load()


const addClose=()=>{
  data.form={}
  addFromRef.value.resetFields()
  uploadRef.value.clearFiles()
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
  request("/api/article/title",{
    params:{
      title:data.input
    }
  }).then((res)=>{
    data.tableData=res
  })
}
const reset=()=>{
  load()
  data.input=""
}

const uploadRef=ref()

const handleImageSuccess= (res)=>{
  data.form.image=res.url
}

const  beforeImageUpload =(file)=>{
  const allowedTypes = ['image/jpeg', 'image/png'];
  if (!allowedTypes.includes(file.type)) {
    ElMessage.error('图片格式必须是 PNG/JPG');
    return false;
  }
  const maxSize = 2 * 1024 * 1024;
  if (file.size > maxSize) {
    ElMessage.error(`图片大小不能超过 ${2}MB`);
    return false;
  }

  return true
}


const handleShowClick=(res)=>{
  data.articleDialogVisible=true
  data.articleInfo=res
}



</script>


