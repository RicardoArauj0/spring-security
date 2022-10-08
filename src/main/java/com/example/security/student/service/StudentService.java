package com.example.security.student.service;

import com.example.security.student.model.Student;

import java.util.List;


public interface StudentService {

    Student getStudent(Integer studentId);
    List<Student> getAll();
    void deleteStudent(Integer studentId);
    Student updateStudent(Integer studentId, Student student);
    void createStudent(Student student);
}
