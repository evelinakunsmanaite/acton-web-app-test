package com.webTest.service;

import java.util.List;

public interface ServiceCourseStudent {
    boolean create(int idCourse, int idStudent);
    List<String> readCourseByStudentId(int idStudent);
    boolean updateCourseListInStudent(int idStudent, int idCourseOld, int idCourseNew);
    boolean updateStudentListInCourse(int idCourse, int idStudentOld, int idStudentNew);
    boolean deleteCourseConnection(int idCourse);
    boolean deleteStudentConnection(int idStudent);
}
