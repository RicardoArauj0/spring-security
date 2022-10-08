package com.example.security.student.controller;

import com.example.security.student.model.Student;
import com.example.security.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/students/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return studentService.getStudent(studentId);
    }

    @GetMapping(path = "/")
    public String okEndpoint() {
        return "OK";
    }

}
