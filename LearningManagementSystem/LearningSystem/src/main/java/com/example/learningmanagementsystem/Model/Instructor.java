package com.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {
    @NotEmpty
    @Size(min = 3)//starting from 001
    private String id;

    @NotEmpty
    @Size(max = 10)
    private String name;

    @Min(25)
    @Max(65)
    private int age;

    @Size(max = 10 , min = 10 , message = "wrong phone Number")
    private String phoneNumber;

    @Email
    private String email;

    @Pattern(regexp = "^(Chemistry|Physics|Mathematics|AI|frontend|Backend)$", message = "Must be one of: Chemistry, Physics, Mathematics, AI, LLM, Church")
    private String specialty;


    @Pattern(regexp = "^(doctor|professor)$", message = "Must be one of: Chemistry, Physics, Mathematics, AI, LLM, Church")
    private String position;

    private ArrayList<Course> courses = new ArrayList<>(); // الكورسات اللي يدرسها المدرس
}
