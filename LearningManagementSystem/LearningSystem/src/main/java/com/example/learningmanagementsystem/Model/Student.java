package com.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    @NotEmpty
    @Size(min = 3)//starting from 001
    private String id;

    @NotEmpty
    @Size(max = 10)
    private String name;

    @Min(18)
    @Max(27)
    private int age;

    @Size(max = 10 , min = 10 , message = "wrong phone Number")
    private String phoneNumber;

    @Email
    private String email;


    private boolean isTeachAsisstence = false;


    @Min(1)
    @Max(10)
    private int level;


}
