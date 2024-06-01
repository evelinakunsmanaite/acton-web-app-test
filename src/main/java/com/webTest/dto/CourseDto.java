package com.webTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
        private int id;
        private String courseName;
        private String teacherName;
        private String teacherPhoneNumber;
        private List<String> studentList;

}
