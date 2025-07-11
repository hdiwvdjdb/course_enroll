package com.example.studentcoursemanagement.controller;

import com.example.studentcoursemanagement.entity.Enrollment;
import com.example.studentcoursemanagement.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Integer id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment createdEnrollment = enrollmentService.saveEnrollment(enrollment);
        return ResponseEntity.ok(createdEnrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Integer id, @RequestBody Enrollment enrollment) {
        enrollment.setEnrollmentID(id);
        Enrollment updatedEnrollment = enrollmentService.saveEnrollment(enrollment);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
