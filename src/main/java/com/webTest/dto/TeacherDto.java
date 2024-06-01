package com.webTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private int id;
    private String name;
    private String phoneNumber;
    private int age;
}
