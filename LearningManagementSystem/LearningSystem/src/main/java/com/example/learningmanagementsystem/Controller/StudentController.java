package com.example.learningmanagementsystem.Controller;

import com.example.learningmanagementsystem.API.ApiResponse;
import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Instructor;
import com.example.learningmanagementsystem.Model.Student;
import com.example.learningmanagementsystem.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    public final StudentService studentService;
    @GetMapping("/get")
    public ResponseEntity<?> getStudent(){
        if(!studentService.getStudents().isEmpty())
            return ResponseEntity.status(200).body(studentService.getStudents());
        return ResponseEntity.status(400).body(new ApiResponse("array is empty"));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@Valid @RequestBody Student student, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        studentService.addStudent(student);
        return ResponseEntity.status(200).body(new ApiResponse("added Successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id ,@Valid@RequestBody Student student ,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(studentService.updateStudent(id,student)){
            studentService.updateStudent(id,student);
            return ResponseEntity.status(200).body(new ApiResponse("updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id){
        if(studentService.deleteStudent(id)){
            studentService.deleteStudent(id);
            return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }

    /// /////////////////////{student Extra Endpoint}/////////////////////////////////////
    @PutMapping("/enrolled/{studentID}/{courseID}")
    public ResponseEntity<?> enrolledStudent(@PathVariable String studentID ,@PathVariable String courseID){
        boolean isEnrolled = studentService.enrolledStudentInTheCourse(studentID,courseID);
        if(isEnrolled){
            return ResponseEntity.status(200).body(new ApiResponse("Added Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number , Or the student Dose not Achieve the minimum Requirements"));
    }
    @PutMapping("/drope-course/{studentId}/{courseId}")
    public ResponseEntity<ApiResponse> dropCourse(@PathVariable String studentId,@PathVariable String courseId) {
        boolean dropped = studentService.dropCourse(studentId, courseId);
        if (dropped) {
            return ResponseEntity.ok(new ApiResponse("Student dropped from course successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Invalid student or course ID."));
    }

    @GetMapping("/view-student/{studentId}")
    public ResponseEntity<?> viewStudentCourses(@PathVariable String studentId) {
        ArrayList<Course> courses = studentService.viewEnrolledCourses(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/check-eligibility/{studentId}/{courseId}")
    public ResponseEntity<ApiResponse> checkEligibility(@PathVariable String studentId,@PathVariable String courseId) {
        boolean isEligible = studentService.isStudentEligible(studentId, courseId);
        if (isEligible) {
            return ResponseEntity.ok(new ApiResponse("Student is eligible for the course."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Student is not eligible or invalid IDs."));
    }

}
