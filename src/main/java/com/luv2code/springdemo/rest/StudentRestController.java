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

    // Add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handlerException(StudentNotFoundException exc){
        // create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return Response Entity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //add another exception handler .. to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
        // create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("BAD_REQUEST");
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
