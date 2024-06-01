package com.webTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String courseName;
    private String teacherName;
    private String teacherPhoneNumber;
    private List<String> studentList;

    public Course(int id) {
        this.id = id;
    }



    public Course(String courseName, String teacherName, String teacherPhoneNumber) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;

    }

    public Course(int id, String courseName, String teacherName, String teacherPhoneNumber) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;

    }
}
