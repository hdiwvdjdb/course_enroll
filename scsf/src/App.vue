<template>
  <div id="app">
    <el-container class="app-container">
      <el-header class="app-header">
        <span class="sys-title">学生选课与成绩管理系统</span>
      </el-header>
      <el-main class="app-main">
        <div v-if="$route.path === '/'" class="home-intro">
          <div class="quick-nav">
            <el-card class="nav-card" @click="goTo('/student-detail')">
              <div class="nav-title">学生管理</div>
              <div class="nav-desc">学生信息的添加、编辑、删除与查询</div>
              <el-button type="primary" class="nav-btn" plain>进入</el-button>
            </el-card>
            <el-card class="nav-card" @click="goTo('/course-detail')">
              <div class="nav-title">课程管理</div>
              <div class="nav-desc">课程信息的维护与管理</div>
              <el-button type="primary" class="nav-btn" plain>进入</el-button>
            </el-card>
            <el-card class="nav-card" @click="goTo('/enrollment-detail')">
              <div class="nav-title">选课管理</div>
              <div class="nav-desc">学生选课与退课操作</div>
              <el-button type="primary" class="nav-btn" plain>进入</el-button>
            </el-card>
            <el-card class="nav-card" @click="goTo('/grade-query')">
              <div class="nav-title">成绩管理</div>
              <div class="nav-desc">成绩录入、查询与统计</div>
              <el-button type="primary" class="nav-btn" plain>进入</el-button>
            </el-card>
          </div>
          <div class="system-desc">
            <h2>系统简介</h2>
            <p>
              本系统面向高校教务管理，提供学生、课程、选课、成绩等信息的高效管理，助力教学管理数字化、规范化。
            </p>
            <ul>
              <li>学生管理：支持学生信息的增删改查</li>
              <li>课程管理：支持课程信息的增删改查</li>
              <li>选课管理：支持学生选课、退课操作</li>
              <li>成绩管理：支持成绩录入、查询与统计</li>
            </ul>
          </div>
        </div>
        <router-view v-else />
      </el-main>
      <el-footer class="app-footer">
        <p>2025 学生成绩管理系统</p>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
const router = useRouter();
const route = useRoute();

function goTo(path) {
  router.push(path);
}
</script>

<style>
#app {
  font-family: "Microsoft YaHei", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #222;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.app-header {
  background-color: #2d3a4b;
  color: #fff;
  padding: 18px 0;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 2px;
  text-align: center;
  box-shadow: 0 2px 8px #e0e3e7;
}

.sys-title {
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 2px;
}

.app-main {
  flex: 1;
  padding: 30px 0;
  background-color: #f5f7fa;
  border-radius: 0;
  box-shadow: none;
  margin: 0;
}

.app-footer {
  background-color: #2d3a4b;
  color: #fff;
  padding: 10px 0;
  font-size: 14px;
  text-align: center;
}

.home-intro {
  max-width: 1100px;
  margin: 0 auto;
  text-align: center;
}

.quick-nav {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
  gap: 32px;
}
.nav-card {
  flex: 1;
  cursor: pointer;
  transition: box-shadow 0.2s, border-color 0.2s;
  border-radius: 10px;
  min-width: 200px;
  min-height: 180px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 1px solid #e4e7ed;
  background: #fff;
  box-shadow: 0 2px 8px #f0f1f2;
}
.nav-card:hover {
  box-shadow: 0 6px 24px rgba(45,58,75,0.10);
  border-color: #409eff;
}
.nav-title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #222;
}
.nav-desc {
  font-size: 15px;
  color: #666;
  margin-bottom: 18px;
}
.nav-btn {
  width: 80px;
  font-size: 15px;
  font-weight: 500;
  border-radius: 6px;
  letter-spacing: 1px;
}

.system-desc {
  text-align: left;
  margin-top: 30px;
  background: #f7f9fb;
  border-radius: 10px;
  padding: 28px 36px;
  box-shadow: 0 2px 8px #f0f1f2;
}
.system-desc h2 {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #2d3a4b;
}
.system-desc p {
  font-size: 16px;
  margin-bottom: 10px;
  color: #333;
}
.system-desc ul {
  list-style-type: disc;
  padding-left: 20px;
  font-size: 16px;
  color: #444;
}
</style>