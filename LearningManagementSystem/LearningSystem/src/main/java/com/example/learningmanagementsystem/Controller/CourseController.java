package com.example.learningmanagementsystem.Controller;

import com.example.learningmanagementsystem.API.ApiResponse;
import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Student;
import com.example.learningmanagementsystem.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    public final CourseService courseService;
    @GetMapping("/get")
    public ResponseEntity<?> getCourses(){
        if(!courseService.getCourses().isEmpty())
            return ResponseEntity.status(200).body(courseService.getCourses());
        return ResponseEntity.status(400).body(new ApiResponse("array is empty"));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@Valid@RequestBody Course course , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        courseService.addCourse(course);
        return ResponseEntity.status(200).body(new ApiResponse("added Successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id ,@Valid@RequestBody Course course ,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(courseService.updateCourse(id,course)){
            courseService.updateCourse(id,course);
            return ResponseEntity.status(200).body(new ApiResponse("updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }

@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id){
        if(courseService.deleteCourse(id)){
            courseService.deleteCourse(id);
            return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }
    ///////////////////////////{Extra Method}///////////////////////////////////////////
    ///
    ///
    @DeleteMapping("/course/remove-student/{courseId}/{studentId}")
    public ResponseEntity<String> removeStudent(
            @PathVariable String courseId,
            @PathVariable String studentId) {
        boolean isRemoved = courseService.removeStudent(courseId, studentId);
        if (isRemoved)
            return ResponseEntity.ok("Student removed from course.");
        return ResponseEntity.status(404).body("Course or student not found.");
    }
    @GetMapping("/course/students/{courseId}")
    public ResponseEntity<?> getEnrolledStudents(@PathVariable String courseId) {
        ArrayList<Student> students = courseService.listEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/course/average-grade/{courseId}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable String courseId) {
        double average = courseService.calculateAverageGrade(courseId);
        return ResponseEntity.ok(average);
    }




}
