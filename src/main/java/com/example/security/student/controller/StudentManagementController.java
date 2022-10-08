package com.example.security.student.controller;

import com.example.security.student.model.Student;
import com.example.security.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/students")
public class StudentManagementController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @PreAuthorize("hasAuthority('student:read')")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<HttpStatus> registerNewStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void delete(@PathVariable("studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void update(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        studentService.updateStudent(studentId, student);
    }
}
