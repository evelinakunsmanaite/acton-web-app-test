package com.webTest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TeacherDto {
    private int id;
    private String name;
    private String phoneNumber;
    private int age;
}
