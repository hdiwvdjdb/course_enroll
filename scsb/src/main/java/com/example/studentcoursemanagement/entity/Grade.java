package com.example.studentcoursemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    private Integer enrollmentID;

    @Column(name = "Grade")
    private Double grade;

    @OneToOne
    @MapsId
    private Enrollment enrollment;

    // Getters and Setters
    public Integer getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Integer enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }
}
