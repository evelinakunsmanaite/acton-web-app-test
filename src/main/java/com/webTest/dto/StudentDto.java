package com.webTest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDto {
    private int id;
    private String name;
    private int age;
    private List<String> coursesList;
}
