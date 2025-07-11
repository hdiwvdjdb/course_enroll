<template>
  <el-container style="height: 100vh;">
    <el-header class="main-header">
      <div class="logo">学生选课管理系统</div>
      <el-menu mode="horizontal" router :default-active="$route.path" class="main-menu">
        <el-menu-item index="/student-detail">学生管理</el-menu-item>
        <el-menu-item index="/course-detail">课程管理</el-menu-item>
        <el-menu-item index="/enrollment-detail">选课管理</el-menu-item>
        <el-menu-item index="/grade-query">成绩管理</el-menu-item>
      </el-menu>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
  </el-container>
</template>

<style>
.main-header {
  display: flex;
  align-items: center;
  background: #2d3a4b;
  color: #fff;
  height: 60px;
  padding: 0 30px;
}
.logo {
  font-size: 22px;
  font-weight: bold;
  margin-right: 40px;
  letter-spacing: 2px;
  color: #fff;
}
.main-menu {
  background: transparent;
  flex: 1;
  display: flex;
  justify-content: flex-end;
}
.main-menu .el-menu-item {
  color: #fff;
  font-size: 16px;
  margin: 0 10px;
  height: 60px;
  line-height: 60px;
}
.main-menu .el-menu-item:hover {
  background-color: #409eff;
}
.main-content {
  background: #f5f7fa;
  padding: 30px 40px;
  min-height: calc(100vh - 60px);
}
</style>
