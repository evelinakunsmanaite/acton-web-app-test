package com.webTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String name;
    private String phoneNumber;
    private int age;

    public Teacher(int id) {
        this.id = id;
    }

}
