<template>
  <div class="student-detail-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <el-button
            @click="goBack"
            type="primary"
          >返回</el-button>
          <h1>学生管理</h1>
          <div>
            <el-input v-model="searchName" placeholder="姓名" style="width: 150px; margin-right: 10px;" clearable />
            <el-input v-model="searchMajor" placeholder="专业" style="width: 150px; margin-right: 10px;" clearable />
            <el-button type="primary" @click="fetchStudents">查询</el-button>
            <el-button type="success" @click="showDialog = true; editMode = false; resetForm()">新增学生</el-button>
          </div>
        </div>
      </template>
      <el-table :data="filteredStudents" style="width: 100%" class="student-table">
        <el-table-column prop="studentID" label="学号" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" align="center"></el-table-column>
        <el-table-column prop="major" label="专业" align="center"></el-table-column>
        <el-table-column prop="classInfo" label="班级" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="editStudent(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteStudent(scope.row.studentID)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="editMode ? '编辑学生' : '新增学生'" v-model="showDialog">
      <el-form :model="form">
        <el-form-item label="姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.major"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="form.classInfo"></el-input>
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
  name: 'StudentDetail',
  data() {
    return {
      students: [],
      showDialog: false,
      editMode: false,
      form: {
        studentID: null,
        name: '',
        gender: '',
        major: '',
        classInfo: ''
      },
      searchName: '',
      searchMajor: ''
    };
  },
  computed: {
    filteredStudents() {
      return this.students.filter(item => {
        const matchName = this.searchName ? item.name.includes(this.searchName) : true;
        const matchMajor = this.searchMajor ? item.major.includes(this.searchMajor) : true;
        return matchName && matchMajor;
      });
    }
  },
  mounted() {
    this.fetchStudents();
  },
  methods: {
    fetchStudents() {
      request.get('/api/students').then(res => {
        this.students = res.data;
      });
    },
    submitForm() {
      if (this.editMode) {
        request.put(`/api/students/${this.form.studentID}`, this.form).then(() => {
          this.showDialog = false;
          this.fetchStudents();
        });
      } else {
        request.post('/api/students', this.form).then(() => {
          this.showDialog = false;
          this.fetchStudents();
        });
      }
    },
    editStudent(row) {
      this.editMode = true;
      this.showDialog = true;
      this.form = { ...row };
    },
    deleteStudent(id) {
      ElMessageBox.confirm('确定要删除该学生吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/api/students/${id}`).then(() => {
          this.fetchStudents();
        });
      }).catch(() => {});
    },
    resetForm() {
      this.form = { studentID: null, name: '', gender: '', major: '', classInfo: '' };
    },
    goBack() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.student-detail-container {
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
.student-table {
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