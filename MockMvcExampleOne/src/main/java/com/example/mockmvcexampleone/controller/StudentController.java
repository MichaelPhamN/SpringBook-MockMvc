package com.example.mockmvcexampleone.controller;

import com.example.mockmvcexampleone.constant.URIConstant;
import com.example.mockmvcexampleone.exception.DataNotFoundException;
import com.example.mockmvcexampleone.exception.InvalidDataRequestException;
import com.example.mockmvcexampleone.model.Student;
import com.example.mockmvcexampleone.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(URIConstant.GET)
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getStudents();
        if(students.isEmpty()) {
            return new ResponseEntity<>(students,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(URIConstant.GET_BY_NAME)
    public ResponseEntity<List<Student>> getStudentByName(@RequestParam("name") String name) {
        List<Student> students = studentService.getStudentByName(name);
        if(students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(URIConstant.GET_BY_ID)
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        if(student == null) {
            throw new DataNotFoundException("Data not found");
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(URIConstant.POST)
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        if(student == null || student.getName() == null) {
            throw new InvalidDataRequestException("Student data is not null");
        }
        studentService.saveStudent(student.getName());
        return new ResponseEntity<>("create new student", HttpStatus.CREATED);
    }

    @PutMapping(URIConstant.PUT)
    public ResponseEntity<String> editStudent(@RequestBody Student student) {
        if(student == null || student.getId() == null || student.getName() == null) {
            throw new InvalidDataRequestException("Student data is not null");
        }

        Student getStudent = studentService.getStudentById(student.getId());
        if(getStudent == null) {
            throw new DataNotFoundException("Data not found");
        }

        studentService.editStudent(student);
        return new ResponseEntity<>("updated student", HttpStatus.OK);
    }

    @DeleteMapping(URIConstant.DELETE_BY_ID)
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        Student getStudent = studentService.getStudentById(id);
        if(getStudent == null) {
            throw new DataNotFoundException("Data not found");
        }

        studentService.deleteStudent(id);
        return new ResponseEntity<>("deleted student", HttpStatus.OK);
    }
}
