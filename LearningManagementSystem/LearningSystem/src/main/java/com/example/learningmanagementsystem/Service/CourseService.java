package com.example.learningmanagementsystem.Service;

import com.example.learningmanagementsystem.Model.Course;
import com.example.learningmanagementsystem.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CourseService {

    ArrayList<Course> courses = new ArrayList<>();

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public boolean updateCourse(String id, Course newCourse){
        for (int i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).getId().equals(id)){
                courses.set(i,newCourse);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(String id){
        for (int i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).getId().equals(id)){
                courses.remove(i);
                return true;
            }
        }
        return false;
    }

    public Course findByID(String id){//اذا الايدي موجود ترجع ترو
        for (int i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).getId().equals(id)){
                return courses.get(i);
            }
        }
        return null;
    }
    ////////////////////{extra Method}////////////////////////////
    ///
    public boolean removeStudent(String courseId, String studentId) {
        Course course = findByID(courseId);
        if (course != null) {
            ArrayList<Student> students = course.getEnrolledStudents();
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(studentId)) {
                    students.remove(i);
                    return true;
                }
            }
        }
        return false; // الطالب غير موجود أو الكورس غير موجود
    }

    public ArrayList<Student> listEnrolledStudents(String courseId) {
        Course course = findByID(courseId);
        if (course != null) {
            return course.getEnrolledStudents();
        }
        return null;//اذا مافيه كورسات
    }
    
    //بحسب المتوسط لجميع درجات الطلاب داخل كورس معين
    public double calculateAverageGrade(String courseId) {
        Course course = findByID(courseId);
        if (course != null && !course.getGrades().isEmpty()) {
            double total = 0;
            for (double grade : course.getGrades().values()) {
                total += grade;
            }
            return total / course.getGrades().size();
        }
        return 0.0; // إما مافيه درجات أو الكورس غير موجود
    }

}
