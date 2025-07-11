import { createRouter, createWebHistory } from 'vue-router';
import CourseDetail from '@/components/CourseDetail.vue';
import EnrollmentDetail from '@/components/EnrollmentDetail.vue';
import GradeQuery from '@/components/GradeQuery.vue';
import StudentDetail from '@/components/StudentDetail.vue';
import Navigation from '@/components/Navigation.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Navigation
  },
  {
    path: '/course-detail',
    name: 'CourseDetail',
    component: CourseDetail
  },
  {
    path: '/enrollment-detail',
    name: 'EnrollmentDetail',
    component: EnrollmentDetail
  },
  {
    path: '/grade-query',
    name: 'GradeQuery',
    component: GradeQuery
  },
  {
    path: '/student-detail',
    name: 'StudentDetail',
    component: StudentDetail
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
