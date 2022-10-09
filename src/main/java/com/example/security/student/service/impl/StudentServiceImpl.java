package com.example.security.student.service.impl;

import com.example.security.student.model.Student;
import com.example.security.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private List<Student> studentList;

    public StudentServiceImpl() {
        studentList = new ArrayList<>();
        studentList.add(new Student(1, "Luke Skywalker"));
        studentList.add(new Student(2,"Leia Organa"));
        studentList.add(new Student(3,"Han Solo"));
    }

    @Override
    public Student getStudent(Integer studentId) {
        return studentList.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Student not found with the ID: %s", studentId)));
    }

    @Override
    public List<Student> getAll() {
        return studentList;
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentList.removeIf(student -> studentId.equals(student.getStudentId()));
    }

    @Override
    public Student updateStudent(Integer studentId, Student student) {
        studentList.stream()
                .filter(s -> studentId.equals(s.getStudentId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .setStudentName(student.getStudentName());

        return studentList.stream()
                .filter(s -> studentId.equals(s.getStudentId()))
                .findFirst().get();
    }

    @Override
    public void createStudent(Student student) {
        studentList.add(student);
    }
}
