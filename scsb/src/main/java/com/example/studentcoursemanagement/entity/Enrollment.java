package com.example.studentcoursemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @Column(name = "EnrollmentID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentID;

    @Column(name = "EnrollmentDate", nullable = false)
    private java.sql.Date enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    private Course course;

    // Getters and Setters
    public Integer getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Integer enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public java.sql.Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(java.sql.Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
