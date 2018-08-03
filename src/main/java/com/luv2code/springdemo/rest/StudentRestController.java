package com.luv2code.springdemo.rest;

import com.luv2code.springdemo.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    // define @PostConstruct to load the student data ... only once!
    @PostConstruct
    public void loadData() {
        students = new ArrayList<>();

        students.add(new Student("All", "All"));
        students.add(new Student("John", "Doe"));
        students.add(new Student("ADam", "patric"));
    }

    @GetMapping("/student")
    public List<Student> getStudents(){

        return students;
    }

    @GetMapping("/student/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        if ((studentId >= students.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        return students.get(studentId);
    }
}
