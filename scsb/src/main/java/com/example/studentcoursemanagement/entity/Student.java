package com.example.studentcoursemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "StudentID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentID;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Gender", length = 1)
    private Character gender;

    @Column(name = "Major")
    private String major;

    @Column(name = "Class")
    private String classInfo;

    // Getters and Setters
    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }
}
