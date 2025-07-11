<template>
  <div class="course-detail-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <el-button
            @click="goBack"
            type="primary"
          >返回</el-button>
          <h1>课程管理</h1>
          <div>
            <el-input v-model="searchName" placeholder="课程名称" style="width: 150px; margin-right: 10px;" clearable />
            <el-input v-model="searchTeacher" placeholder="任课教师" style="width: 150px; margin-right: 10px;" clearable />
            <el-button type="primary" @click="fetchCourses">查询</el-button>
            <el-button type="success" @click="showDialog = true; editMode = false; resetForm()">新增课程</el-button>
          </div>
        </div>
      </template>
      <el-table :data="filteredCourses" style="width: 100%" class="course-table">
        <el-table-column prop="courseID" label="课程编号" align="center"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="credits" label="学分" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="editCourse(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteCourse(scope.row.courseID)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="editMode ? '编辑课程' : '新增课程'" v-model="showDialog">
      <el-form :model="form">
        <el-form-item label="课程名称">
          <el-input v-model="form.courseName"></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model.number="form.credits" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">{{ editMode ? '保存' : '新增' }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from '../utils/request';
import { ElMessageBox } from 'element-plus';

export default {
  name: 'CourseDetail',
  data() {
    return {
      courses: [],
      showDialog: false,
      editMode: false,
      form: {
        courseID: null,
        courseName: '',
        credits: '', // 改为 credits
        teacher: '',
        description: ''
      },
      searchName: '',
      searchTeacher: ''
    };
  },
  computed: {
    filteredCourses() {
      return this.courses.filter(item => {
        const matchName = this.searchName ? item.courseName.includes(this.searchName) : true;
        const matchTeacher = this.searchTeacher ? item.teacher.includes(this.searchTeacher) : true;
        return matchName && matchTeacher;
      });
    }
  },
  mounted() {
    this.fetchCourses();
  },
  methods: {
    fetchCourses() {
      request.get('/api/courses').then(res => {
        this.courses = res.data.map(item => ({
          ...item,
          teacher: item.teacher ? item.teacher : ''
        }));
      });
    },
    submitForm() {
      if (this.editMode) {
        request.put(`/api/courses/${this.form.courseID}`, this.form).then(() => {
          this.showDialog = false;
          this.fetchCourses();
        });
      } else {
        request.post('/api/courses', this.form).then(() => {
          this.showDialog = false;
          this.fetchCourses();
        });
      }
    },
    editCourse(row) {
      this.editMode = true;
      this.showDialog = true;
      this.form = { ...row };
    },
    deleteCourse(id) {
      ElMessageBox.confirm('确定要删除该课程吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/api/courses/${id}`).then(() => {
          this.fetchCourses();
        });
      }).catch(() => {});
    },
    resetForm() {
      this.form = { courseID: null, courseName: '', credits: '', teacher: '', description: '' };
    },
    goBack() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.course-detail-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #f5f5f5;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
h1 {
  font-size: 24px;
  color: #333;
  margin: 0;
  font-weight: 700;
}
.el-card {
  width: 100%;
  max-width: 1200px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.enrollment-table {
  margin-top: 20px;
}
.el-table th {
  background-color: #f0f2f5;
  color: #333;
  font-weight: 600;
}
.el-button {
  background-color: #409eff;
  color: #fff;
  border-color: #409eff;
}
.el-button:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}
.el-dialog {
  border-radius: 10px;
}
.el-form-item__label {
  font-weight: 500;
}
</style>