package com.example.mockmvcexampleone.dao;

import com.example.mockmvcexampleone.model.Student;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentDao {
    private static List<Student> students = new ArrayList<>();
    {
        Faker faker = new Faker();
        for (int i = 0 ; i < 8; i++) {
            Student student = new Student();
            student.setId(i + 1);
            student.setName(faker.name().fullName());
            students.add(student);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudentById(Integer id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst().orElse(new Student());
    }

    public List<Student> getStudentByName(String name) {
        return students.stream()
                .filter(student -> student.getName().contains(name))
                .collect(Collectors.toList());
    }

    public void saveStudent(String name) {
        Student student = new Student();
        student.setId(students.size() + 1);
        student.setName(name);
        students.add(student);
    }

    public void editStudent(Student st) {
        Student student = students.get(st.getId() - 1);
        student.setName(st.getName());
        students.remove(student.getId() - 1);
        students.add(student.getId() - 1, student);
    }

    public void deleteStudent(Integer id) {
        students.remove(id - 1);
    }
}
