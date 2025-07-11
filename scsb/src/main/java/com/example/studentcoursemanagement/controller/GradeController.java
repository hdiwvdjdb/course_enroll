package com.example.studentcoursemanagement.controller;

import com.example.studentcoursemanagement.entity.Grade;
import com.example.studentcoursemanagement.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Integer id) {
        Grade grade = gradeService.getGradeById(id);
        if (grade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(grade);
    }

    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        Grade createdGrade = gradeService.saveGrade(grade);
        return ResponseEntity.ok(createdGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Integer id, @RequestBody Grade grade) {
        grade.setEnrollmentID(id);
        Grade updatedGrade = gradeService.saveGrade(grade);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Integer id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
