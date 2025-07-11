package com.example.studentcoursemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "CourseID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;
    
    @Column(name = "CourseName", nullable = false)
    private String courseName;

    @Column(name = "Credits", nullable = false)
    private Integer credits;

    // Getters and Setters
    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
