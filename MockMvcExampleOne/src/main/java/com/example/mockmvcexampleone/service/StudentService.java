package com.example.mockmvcexampleone.service;

import com.example.mockmvcexampleone.dao.StudentDao;
import com.example.mockmvcexampleone.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao dao;
    public List<Student> getStudents() {
        return dao.getStudents();
    }

    public List<Student> getStudentByName(String name) {
        return dao.getStudentByName(name);
    }

    public Student getStudentById(Integer id) {
        return dao.getStudentById(id);
    }

    public void saveStudent(String name) {
        dao.saveStudent(name);
    }

    public void editStudent(Student student) {
        dao.editStudent(student);
    }

    public void deleteStudent(Integer id) {
        dao.deleteStudent(id);
    }
}
