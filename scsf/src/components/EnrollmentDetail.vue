<template>
  <div class="enrollment-detail-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <el-button
            @click="goBack"
            type="primary"
          >返回</el-button>
          <h1>选课管理</h1>
          <div>
            <el-input v-model="searchStudent" placeholder="学生姓名" style="width: 150px; margin-right: 10px;" clearable />
            <el-input v-model="searchCourse" placeholder="课程名称" style="width: 150px; margin-right: 10px;" clearable />
            <el-button type="primary" @click="fetchEnrollments">查询</el-button>
            <el-button type="success" @click="showAddDialog = true">新增选课</el-button>
          </div>
        </div>
      </template>
      <el-table :data="filteredEnrollments" style="width: 100%" class="enrollment-table">
        <el-table-column prop="enrollmentID" label="选课编号" align="center"></el-table-column>
        <el-table-column prop="studentName" label="学生姓名" align="center"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="enrollmentDate" label="选课日期" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="editEnrollment(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteEnrollment(scope.row.enrollmentID)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="editMode ? '编辑选课' : '新增选课'" v-model="showAddDialog">
      <el-form :model="form">
        <el-form-item label="学生姓名">
          <el-autocomplete
            v-model="form.studentName"
            :fetch-suggestions="querySearchStudent"
            placeholder="请输入学生姓名"
            @select="handleStudentSelect"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="课程">
          <el-select v-model="form.courseID" placeholder="请选择课程" style="width: 100%;">
            <el-option
              v-for="item in courses"
              :key="item.courseID"
              :label="item.courseName"
              :value="item.courseID"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选课日期">
          <el-date-picker
            v-model="form.enrollmentDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
            style="width: 100%;"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">{{ editMode ? '保存' : '新增' }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>
  
<script>
import request from '../utils/request';
import { ElMessageBox } from 'element-plus';

export default {
  name: 'EnrollmentDetail',
  data() {
    return {
      enrollments: [],
      courses: [], // 新增
      showAddDialog: false,
      editMode: false,
      form: {
        enrollmentID: null,
        studentName: '', // 改为学生姓名
        studentID: '', // 用于提交
        courseID: '',
        enrollmentDate: ''
      },
      students: [], // 新增
      searchStudent: '',
      searchCourse: ''
    };
  },
  computed: {
    filteredEnrollments() {
      return this.enrollments.filter(item => {
        const matchStudent = this.searchStudent ? item.studentName.includes(this.searchStudent) : true;
        const matchCourse = this.searchCourse ? item.courseName.includes(this.searchCourse) : true;
        return matchStudent && matchCourse;
      });
    }
  },
  mounted() {
    this.fetchEnrollments();
    this.fetchCourses(); // 新增
    this.fetchStudents(); // 新增
  },
  methods: {
    fetchEnrollments() {
      request.get('/api/enrollments').then(res => {
        this.enrollments = res.data.map(item => ({
          ...item,
          studentName: item.student ? item.student.name : '',
          courseName: item.course ? item.course.courseName : ''
        }));
      });
    },
    fetchCourses() {
      request.get('/api/courses').then(res => {
        this.courses = res.data;
      });
    },
    fetchStudents() {
      request.get('/api/students').then(res => {
        this.students = res.data;
      });
    },
    querySearchStudent(queryString, cb) {
      const results = this.students
        .filter(s => s.name.includes(queryString))
        .map(s => ({ value: s.name, studentID: s.studentID }));
      cb(results);
    },
    handleStudentSelect(item) {
      this.form.studentID = item.studentID;
    },

    submitForm() {
      if (this.editMode) {
        request.put(`/api/enrollments/${this.form.enrollmentID}`, {
          enrollmentID: this.form.enrollmentID,
          student: { studentID: this.form.studentID },
          course: { courseID: this.form.courseID },
          enrollmentDate: this.form.enrollmentDate
        }).then(() => {
          this.showAddDialog = false;
          this.fetchEnrollments();
        });
      } else {
        request.post('/api/enrollments', {
          student: { studentID: this.form.studentID },
          course: { courseID: this.form.courseID },
          enrollmentDate: this.form.enrollmentDate
        }).then(() => {
          this.showAddDialog = false;
          this.fetchEnrollments();
        });
      }
    },
    editEnrollment(row) {
      this.editMode = true;
      this.showAddDialog = true;
      this.form = {
        enrollmentID: row.enrollmentID,
        studentName: row.student ? row.student.name : '',
        studentID: row.student ? row.student.studentID : '',
        courseID: row.course ? row.course.courseID : '',
        enrollmentDate: row.enrollmentDate
      };
    },
    deleteEnrollment(id) {
      ElMessageBox.confirm('确定要删除这条选课记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/api/enrollments/${id}`).then(() => {
          this.fetchEnrollments();
        });
      }).catch(() => {});
    },
    goBack() {
      this.$router.push('/');
    }
  }
};
</script>
<style scoped>
.enrollment-detail-container {
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