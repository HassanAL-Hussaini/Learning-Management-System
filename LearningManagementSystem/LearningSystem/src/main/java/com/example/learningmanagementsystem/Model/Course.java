package com.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @NotEmpty
    @Size(min = 3)//starting from 001
    private String id;


    @NotEmpty
    @Size(max = 10)
    private String name;

    @NotNull
    @Positive
    private int Chapters;


    private boolean isAvailable = true;

    @NotNull
    @Positive
    private int levelRequired ;//this is for students

    @NotEmpty
    @Pattern(regexp = ("^doctor|professor$"))
    private String positionRequired;//this is for instructors

    ArrayList<Student> enrolledStudents = new ArrayList<>();//هذي الاراي لحفظ اوبجكت من كل طالب مسجل في الكورس

    private HashMap<Student, Double> grades = new HashMap<>(); // لتخزين درجات الطلاب

}
