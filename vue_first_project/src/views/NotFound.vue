
<script setup>
import router from '@/router';
import imageUrl from '@/assets/404.JPG';

const  data=({

})

</script>


<template>
  <el-result title="404" sub-title="请求错误">
    <template #icon>
<!--      <el-image-->
<!--          src="../assets/orange.svg"-->
<!--      />-->
<!--      <img src="@/assets/404.JPG" STYLE="width: 500PX;" alt="加载失败">-->
      <el-image :src="imageUrl"  style="width: 600px"  alt="加载失败"/>
    </template>
    <template #extra>
      <el-button  @click="router.back()" type="primary">Back</el-button>
    </template>
  </el-result>
</template>

