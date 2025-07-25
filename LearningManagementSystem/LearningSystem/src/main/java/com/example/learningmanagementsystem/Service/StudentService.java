package com.example.learningmanagementsystem.Service;

import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CourseService courseService;//dependency(Service) injection.
    ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public boolean updateStudent(String id, Student newStudent){
        for (int i = 0; i < students.size() ; i++){
            if(students.get(i).getId().equals(id)){
                students.set(i, newStudent);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(String id){
        for (int i = 0; i < students.size() ; i++){
            if(students.get(i).getId().equals(id)){
                students.remove(i);
                return true;
            }
        }
        return false;
    }

    public Student findByID(String id){//اذا الايدي موجود ترجع ترو
        for (int i = 0 ; i < students.size() ; i++){
            if(students.get(i).getId().equals(id)){
                return students.get(i);
            }
        }
        return null;
    }

    /// /////////////////{Extra Method}//////////////////


    /// enroled student in course by id
    public boolean enrolledStudentInTheCourse(String studentID, String courseID){
        Student student = this.findByID(studentID);
        Course course = courseService.findByID(courseID);

        if(student == null || course == null) return false;
        if(!course.isAvailable()) return false;
        if(student.getLevel() >= course.getLevelRequired()) return false;

        course.getEnrolledStudents().add(student);
        return true;
    }
    /// delete student from specfic course by id
    public boolean dropCourse(String studentId, String courseId){
        Student student = this.findByID(studentId);
        Course course = courseService.findByID(courseId);
        if(student == null || course == null) return false;
        course.getEnrolledStudents().remove(student);
        return true;
    }

    //هذي الميثود توري الطالب الكورسات اللي هو مسجل فيها
    public ArrayList<Course> viewEnrolledCourses(String studentId) {
        ArrayList<Course> enrolledCourses = new ArrayList<>();

        for (Course course : courseService.getCourses()) {
            for (Student student : course.getEnrolledStudents()) {
                if (student.getId().equals(studentId)) {
                    enrolledCourses.add(course);
                    break;
                }
            }
        }

        return enrolledCourses;
    }

    //يتحقق من الستيودنت هل هو مؤهل انه يسجل في الكورس على فكره بس يتحقق ما يسجله القيمه الراجعه في الاند بوينت المفروض تكون بولين وبناء عليها ارسله رساله قول له فيها انت مؤهل او غير مؤهل
    public boolean isStudentEligible(String studentID, String courseID){
        Student student = this.findByID(studentID);
        Course course = courseService.findByID(courseID);

        if(student == null || course == null) return false;
        if(!course.isAvailable()) return false;
        if(student.getLevel() >= course.getLevelRequired()) return false;

        return true;
    }


}
