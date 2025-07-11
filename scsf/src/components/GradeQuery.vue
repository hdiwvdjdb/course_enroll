<template>
  <div class="grade-query-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <el-button
            @click="goBack"
            type="primary"
          >返回</el-button>
          <h1>成绩管理</h1>
          <div>
            <el-input v-model="searchStudent" placeholder="学生姓名" style="width: 150px; margin-right: 10px;" clearable />
            <el-input v-model="searchCourse" placeholder="课程名称" style="width: 150px; margin-right: 10px;" clearable />
            <el-button type="primary" @click="fetchGrades">查询</el-button>
            <el-button type="success" @click="showDialog = true; editMode = false; resetForm()">新增成绩</el-button>
          </div>
        </div>
      </template>
      <el-table :data="filteredGrades" style="width: 100%" class="grade-table">
        <el-table-column prop="enrollmentID" label="选课编号" align="center"></el-table-column>
        <el-table-column prop="studentName" label="学生姓名" align="center"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="grade" label="成绩" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="editGrade(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteGrade(scope.row.enrollmentID)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="editMode ? '编辑成绩' : '新增成绩'" v-model="showDialog">
      <el-form :model="form">
        <el-form-item label="学生">
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
        <el-form-item label="成绩">
          <el-input v-model="form.grade" type="number" placeholder="请输入成绩"></el-input>
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
  name: 'GradeQuery',
  data() {
    return {
      grades: [],
      students: [],
      courses: [],
      showDialog: false,
      editMode: false,
      form: {
        enrollmentID: null,
        studentName: '',
        studentID: '',
        courseID: '',
        grade: ''
      },
      searchStudent: '',
      searchCourse: ''
    };
  },
  computed: {
    filteredGrades() {
      return this.grades.filter(item => {
        const matchStudent = this.searchStudent ? item.studentName.includes(this.searchStudent) : true;
        const matchCourse = this.searchCourse ? item.courseName.includes(this.searchCourse) : true;
        return matchStudent && matchCourse;
      });
    }
  },
  mounted() {
    this.fetchGrades();
    this.fetchStudents();
    this.fetchCourses();
  },
  methods: {
    fetchGrades() {
      request.get('/api/grades').then(res => {
        this.grades = res.data.map(item => ({
          ...item,
          studentName: item.enrollment && item.enrollment.student ? item.enrollment.student.name : '',
          studentID: item.enrollment && item.enrollment.student ? item.enrollment.student.studentID : '',
          courseName: item.enrollment && item.enrollment.course ? item.enrollment.course.courseName : '',
          courseID: item.enrollment && item.enrollment.course ? item.enrollment.course.courseID : ''
        }));
      });
    },
    fetchStudents() {
      request.get('/api/students').then(res => {
        this.students = res.data;
      });
    },
    fetchCourses() {
      request.get('/api/courses').then(res => {
        this.courses = res.data;
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
      // 查找enrollmentID
      request.get('/api/enrollments').then(res => {
        const enrollment = res.data.find(e =>
          e.student && e.student.studentID === this.form.studentID &&
          e.course && e.course.courseID === this.form.courseID
        );
        if (!enrollment) {
          this.$message.error('未找到对应的选课记录，请先添加选课信息');
          return;
        }
        if (this.editMode) {
          request.put(`/api/grades/${enrollment.enrollmentID}`, {
            enrollmentID: enrollment.enrollmentID,
            enrollment: { enrollmentID: enrollment.enrollmentID },
            grade: this.form.grade
          }).then(() => {
            this.showDialog = false;
            this.fetchGrades();
          });
        } else {
          request.post('/api/grades', {
            enrollmentID: enrollment.enrollmentID,
            enrollment: { enrollmentID: enrollment.enrollmentID },
            grade: this.form.grade
          }).then(() => {
            this.showDialog = false;
            this.fetchGrades();
          });
        }
      });
    },
    editGrade(row) {
      this.editMode = true;
      this.showDialog = true;
      this.form = {
        enrollmentID: row.enrollmentID,
        studentName: row.studentName,
        studentID: row.studentID,
        courseID: row.courseID,
        grade: row.grade
      };
    },
    deleteGrade(id) {
      ElMessageBox.confirm('确定要删除该成绩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/api/grades/${id}`).then(() => {
          this.fetchGrades();
        });
      }).catch(() => {});
    },
    resetForm() {
      this.form = { enrollmentID: null, studentName: '', studentID: '', courseID: '', grade: '' };
    },
    goBack() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.grade-query-container {
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

.grade-table {
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
</style>
