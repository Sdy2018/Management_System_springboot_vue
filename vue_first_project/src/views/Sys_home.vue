<template>
  <!-- 英雄区 -->
  <section class="hero">
    <div class="container">
      <h1>springboot + vue + mybatis</h1>
      <p>个人学习后台管理项目</p>
    </div>
  </section>

  <!-- 功能模块 -->
  <section class="features">
    <div class="container">
      <div class="feature-card">
        <h3>springboot+vue+element ui</h3>
        <p>前后端分离,RESTful设计风格</p>
      </div>
      <div class="feature-card">
        <h3>EasyExcel</h3>
        <p>excel文件导入导出</p>
      </div>
      <div class="feature-card">
        <h3>eChart   WangEditor</h3>
        <p>数据可视化 文章编辑</p>
      </div>
    </div>
  </section>

  <!-- 页脚 -->
<!--  <footer class="footer">-->
<!--    <p>© 2025 MySite. 保留所有权利.</p>-->
<!--  </footer>-->
</template>

<script setup>
// Vue3 组合式 API（当前无复杂逻辑可省略）
</script>

<style scoped>
/* 基础样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:root {
  --primary-color: #2c3e50;
  --secondary-color: #3498db;
  --light-bg: #f8f9fa;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  line-height: 1.6;
  color: var(--primary-color);
  background: var(--light-bg);
}

/* 布局结构 */
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}



/* 英雄区 */
.hero {
  padding: 4rem 0;
  text-align: center;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: white;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

/* 功能模块 */
.features {
  padding: 4rem 0;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.feature-card {
  padding: 2rem;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
}

/* 按钮样式 */
.cta-button {
  margin-top: 20px;
  padding: 12px 30px;
  background: white;
  //color: var(--primary-color);
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.cta-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

/* 响应式设计 */
@media (max-width: 768px) {

  .nav-menu li {
    margin: 0.5rem 0;
  }
}


</style>