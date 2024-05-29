package com.webTest.dao;

import com.webTest.model.CourseStudent;

import java.util.List;

public interface DaoCourseStudent {
    int create(CourseStudent courseStudent);
    List<String> readCourseByStudentId(int idStudent);
    boolean updateCourseListInStudent(int idStudent, int idCourseOld, int idCourseNew);
    boolean updateStudentListInCourse(int idCourse, int idStudentOld, int idStudentNew);
    boolean deleteCourseConnection(int idCourse);
    boolean deleteStudentConnection(int idStudent);
}
