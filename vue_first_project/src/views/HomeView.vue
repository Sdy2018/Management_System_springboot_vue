<template>
<!--  <router-view/>-->
   <div>
     home
     <el-button @click="router.push({name:'about'})">
       about
     </el-button>
     <el-button  @click="router.push({name:'test'})">
       test
     </el-button>
     <router-view/>
   </div>
</template>



<script setup>

import {onMounted, reactive} from "vue";
import router from "@/router/index.js";

const data = reactive({
  show: 0,
  color: ["blue","yellow","peachpuff"],
  ani:"dog"
});

onMounted(()=>
 console.log("挂载成功")
)


const push_test=()=>{
  router.push({
    name: 'test',
    params: { id: 789 },
    query: { color: 'black' },
    hash: '#specs'
  })
}

const showAlert = () => alert('ok');

</script>
