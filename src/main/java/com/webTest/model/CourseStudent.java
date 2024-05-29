package com.webTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStudent {
    private int id;
    private int idStudent;
    private int idCourse;

    public CourseStudent(int idCourse, int idStudent) {
        this.idCourse = idCourse;
        this.idStudent = idStudent;

    }
}
