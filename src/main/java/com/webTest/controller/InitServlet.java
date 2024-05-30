package com.webTest.controller;

import com.webTest.dao.Dao;
import com.webTest.dao.DaoCourseStudent;
import com.webTest.dto.*;
import com.webTest.init.DatabaseConnection;
import com.webTest.model.*;
import com.webTest.service.Service;
import com.webTest.service.ServiceCourseStudent;
import com.webTest.service.impl.*;
import com.webTest.dao.impl.*;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;

public abstract class InitServlet extends HttpServlet {

    protected Service<TeacherDto> serviceTeacher;
    protected Service<CourseDto> serviceCourse;
    protected Service<StudentDto> serviceStudent;
    protected ServiceCourseStudent serviceCourseStudent;


    @Override
    public void init() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connect();

        Dao<Teacher> daoTeacher = new DaoTeacherImpl(connection);
        Dao<Course> daoCourse = new DaoCourseImpl(connection);
        Dao<Student> daoStudent = new DaoStudentImpl(connection);
        DaoCourseStudent daoCourseStudent = new DaoCourseStudentImpl(connection);

        serviceTeacher = new ServiceTeacherImpl(daoTeacher);
        serviceCourse = new ServiceCourseImpl(daoCourse);
        serviceStudent = new ServiceStudentImpl(daoStudent);
        serviceCourseStudent = new ServiceCourseStudentImpl(daoCourseStudent);

    }
}

