package com.webTest.service.impl;

import com.webTest.dao.DaoCourseStudent;
import com.webTest.model.CourseStudent;
import com.webTest.service.ServiceCourseStudent;

import java.util.List;

public class ServiceCourseStudentImpl implements ServiceCourseStudent {
    private final DaoCourseStudent daoCourseStudent;

    public ServiceCourseStudentImpl(DaoCourseStudent daoCourseStudent) {
        this.daoCourseStudent = daoCourseStudent;
    }


    @Override
    public boolean create(int idCourse, int idStudent) {
        return daoCourseStudent.create(new CourseStudent(idCourse,idStudent)) > 0;
    }

    @Override
    public List<String> readCourseByStudentId(int idStudent) {
        return daoCourseStudent.readCourseByStudentId(idStudent);
    }

    @Override
    public boolean updateCourseListInStudent(int idStudent, int idCourseOld, int idCourseNew) {
        return daoCourseStudent.updateCourseListInStudent(idStudent,idCourseOld,idCourseNew);
    }

    @Override
    public boolean updateStudentListInCourse(int idCourse, int idStudentOld, int idStudentNew) {
        return daoCourseStudent.updateStudentListInCourse(idCourse,idStudentOld,idStudentNew);
    }

    @Override
    public boolean deleteCourseConnection(int idCourse) {
        return daoCourseStudent.deleteCourseConnection(idCourse);
    }

    @Override
    public boolean deleteStudentConnection(int idStudent) {
        return daoCourseStudent.deleteStudentConnection(idStudent);
    }
}
