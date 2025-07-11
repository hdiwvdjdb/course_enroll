 # 学生选课与成绩管理系统

## 项目简介
本系统实现了学生、课程、选课、成绩等信息的管理，支持前后端分离，适用于高校教务管理场景。

## 技术栈
- 后端：Spring Boot 2.7.0、Spring Data JPA、MySQL
- 前端：Vue 3、Element Plus、Axios

## 主要功能
- 学生信息管理（增删改查）
- 课程信息管理（增删改查）
- 选课管理（选课、退课）
- 成绩管理（录入、查询、统计）

## 后端启动方式
1. 确保本地已安装 MySQL，并创建数据库：
   ```sql
   CREATE DATABASE studentcoursemanagementsystem DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. 修改 `scsb/src/main/resources/application.properties` 中的数据库用户名和密码为你本地的配置。
3. 在 `scsb` 目录下执行：
   ```bash
   mvn spring-boot:run
   ```
4. 控制台出现“系统启动成功！”即后端启动完成。

## 前端启动方式
1. 进入 `scsf` 目录，安装依赖：
   ```bash
   npm install
   ```
2. 启动前端项目：
   ```bash
   npm run serve
   ```
3. 访问 [http://localhost:8080]（前端端口如有不同请以实际为准）。

## 数据库说明
- 数据库名：`studentcoursemanagementsystem`
- 启动后端时会自动建表。
- 如需初始化数据，可手动插入。

## 其他
- 前后端接口通过 `/api` 代理。
- 如有问题欢迎提 issue。
