package com.example.learningmanagementsystem.Service;

import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Instructor;
import com.example.learningmanagementsystem.Model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final CourseService courseService;
    ArrayList<Instructor> instructors = new ArrayList<>();

    public ArrayList<Instructor> getInstructor(){
        return instructors;
    }

    public void addInstructor(Instructor instructor){
        instructors.add(instructor);
    }

    public boolean updateInstructor(String id, Instructor newInstructor){
        for (int i = 0; i < instructors.size() ; i++){
            if(instructors.get(i).getId().equals(id)){
                instructors.set(i, newInstructor);
                return true;
            }
        }
        return false;
    }

    public boolean deleteInstructor(String id){
        for (int i = 0; i < instructors.size() ; i++){
            if(instructors.get(i).getId().equals(id)){
                instructors.remove(i);
                return true;
            }
        }
        return false;
    }

    public Instructor findByID(String id){//اذا الايدي موجود ترجع ترو
        for (int i = 0 ; i < instructors.size() ; i++){
            if(instructors.get(i).getId().equals(id)){
                return instructors.get(i);
            }
        }
        return null;
    }
    /// //////////////////////////{Extra Method}////////////////////////////

    public boolean assignToCourse(String instructorId, String courseId) {
        Instructor instructor = findByID(instructorId);
        Course course = courseService.findByID(courseId);
        if (instructor != null && course != null) {
            instructor.getCourses().add(course);
            return true; //  رجع true إذا المدرس موجود وتمت الإضافة
        }
        return false; //  رجع false إذا المدرس ما كان موجود
    }

    // يحذف كورس معين من المدرس
    public boolean removeFromCourse(String instructorId, String courseId) {
        Instructor instructor = findByID(instructorId);
        if (instructor != null) {
            ArrayList<Course> courses = instructor.getCourses();
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getId().equals(courseId)) {
                    courses.remove(i);
                    return true; //  الكورس تم حذفه
                }
            }
            return false; //  المدرس موجود لكن ما عنده الكورس المطلوب
        }
        return false; //  المدرس نفسه غير موجود
    }

    // يرجع كل الكورسات اللي يدرسها المدرس
    public ArrayList<Course> listAssignedCourses(String instructorId) {
        Instructor instructor = findByID(instructorId);
        if (instructor != null) {
            return instructor.getCourses(); //  المدرس موجود
        }
        return new ArrayList<>(); //  المدرس غير موجود، رجع قائمة فاضية
    }

    // يعطي درجة لطالب في كورس معين
    public boolean gradeStudent(String instructorId, String courseId, String studentId, double grade) {
        Instructor instructor = findByID(instructorId);
        Course course = courseService.findByID(courseId);

        if (instructor != null && course != null) {
            // تأكد أن المدرس يدرس الكورس هذا
            if (instructor.getCourses().contains(course)) {
                // نبحث عن الطالب
                for (Student student : course.getEnrolledStudents()) {
                    if (student.getId().equals(studentId)) {
                        course.getGrades().put(student, grade); // سجل الدرجة
                        return true;
                    }
                }
                return false; //  الطالب غير مسجل في الكورس
            }
            return false; // المدرس لا يدرس الكورس
        }
        return false; //  المدرس أو الكورس غير موجود
    }

    // يتحقق هل المدرس مؤهل لتدريس الكورس حسب الـ position
    public boolean isEligibleToTeach(String instructorId, String courseId) {
        Instructor instructor = findByID(instructorId);
        Course course = courseService.findByID(courseId);

        if (instructor != null && course != null) {
            return instructor.getPosition().equalsIgnoreCase(course.getPositionRequired());
        }
        return false; //  المدرس أو الكورس غير موجود
    }

}
