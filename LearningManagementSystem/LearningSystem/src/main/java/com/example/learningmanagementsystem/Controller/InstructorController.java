package com.example.learningmanagementsystem.Controller;

import com.example.learningmanagementsystem.API.ApiResponse;
import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Instructor;
import com.example.learningmanagementsystem.Service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/instructor")
@RequiredArgsConstructor
public class InstructorController {

    public final InstructorService instructorService;

    @GetMapping("/get")
    public ResponseEntity<?> getInstructor(){
        if(!instructorService.getInstructor().isEmpty())
            return ResponseEntity.status(200).body(instructorService.getInstructor());
        return ResponseEntity.status(400).body(new ApiResponse("array is empty"));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addInstructor(@Valid @RequestBody Instructor instructor , Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        instructorService.addInstructor(instructor);
        return ResponseEntity.status(200).body(new ApiResponse("added Successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatedInstructor(@PathVariable String id ,@Valid@RequestBody Instructor instructor ,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(instructorService.updateInstructor(id,instructor)){
            instructorService.updateInstructor(id,instructor);
            return ResponseEntity.status(200).body(new ApiResponse("updated Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id){
        if(instructorService.deleteInstructor(id)){
            instructorService.deleteInstructor(id);
            return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Wrong Id number"));
    }

    ////////////////////////{Extra EndPoint}////////////////////

    @PutMapping("/assign/{instructorId}/{courseId}")
    public ResponseEntity<String> assignInstructorToCourse(@PathVariable String instructorId, @PathVariable String courseId) {
        boolean assigned = instructorService.assignToCourse(instructorId, courseId);
        return assigned ?
                ResponseEntity.ok("Instructor assigned to course.") :
                ResponseEntity.status(404).body("Instructor or course not found.");
    }

    @DeleteMapping("/remove-course/{instructorId}/{courseId}")
    public ResponseEntity<String> removeCourseFromInstructor(@PathVariable String instructorId, @PathVariable String courseId) {
        boolean removed = instructorService.removeFromCourse(instructorId, courseId);
        return removed ?
                ResponseEntity.ok("Course removed from instructor.") :
                ResponseEntity.status(404).body("Instructor or course not found.");
    }

    @GetMapping("/courses/{instructorId}")
    public ResponseEntity<?> getAssignedCourses(@PathVariable String instructorId) {
        ArrayList<Course> courses = instructorService.listAssignedCourses(instructorId);
        return courses.isEmpty() ?
                ResponseEntity.status(404).body("Instructor not found or has no assigned courses.") :
                ResponseEntity.ok(courses);
    }


    @PutMapping("/grade/{instructorId}/{courseId}/{studentId}/{grade}")
    public ResponseEntity<String> gradeStudent(@PathVariable String instructorId, @PathVariable String courseId, @PathVariable String studentId, @PathVariable double grade) {

        boolean success = instructorService.gradeStudent(instructorId, courseId, studentId, grade);
        return success ?
                ResponseEntity.ok("Grade assigned successfully.") :
                ResponseEntity.status(400).body("Failed to assign grade. Instructor/course/student not found or not assigned.");
    }

    @GetMapping("/eligible/{instructorId}/{courseId}")
    public ResponseEntity<String> checkEligibility(@PathVariable String instructorId,@PathVariable String courseId) {
        boolean eligible = instructorService.isEligibleToTeach(instructorId, courseId);

        return eligible ?
                ResponseEntity.ok("Instructor is eligible to teach this course.") :
                ResponseEntity.status(400).body("Instructor is not eligible or data not found.");
    }

}
