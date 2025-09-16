<script setup>
import {RouterView} from 'vue-router'
import router from "@/router/index.js";


router.beforeEach((to, from, next) => {
  // 从目标路由的 meta 中获取标题

  document.title = to.meta.title || '默认标题'; // 设置页面标题
  next(); // 继续导航
});
</script>

<template>

  <RouterView />
</template>
