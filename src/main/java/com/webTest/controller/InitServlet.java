package com.webTest.controller;

import com.webTest.dao.Dao;
import com.webTest.dao.DaoCourseStudent;
import com.webTest.dto.*;
import com.webTest.model.*;
import com.webTest.service.Service;
import com.webTest.service.ServiceCourseStudent;
import com.webTest.service.impl.*;
import com.webTest.util.*;
import com.webTest.dao.impl.*;

import javax.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class InitServlet extends HttpServlet {
    private static final String URL = "jdbc:postgresql://localhost:5432/databasecourse?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "userpg";
    private static final String PASSWORD = System.getenv("evelina2002");

    protected Service<TeacherDto> serviceTeacher;
    protected Service<CourseDto> serviceCourse;
    protected Service<StudentDto> serviceStudent;
    protected ServiceCourseStudent serviceCourseStudent;
    private final Logger logger = Logger.getLogger(InitServlet.class.getName());


    @Override
    public void init() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            Dao<Teacher> daoTeacher = new DaoTeacherImpl(connection);
            Dao<Course> daoCourse = new DaoCourseImpl(connection);
            Dao<Student> daoStudent = new DaoStudentImpl(connection);
            DaoCourseStudent daoCourseStudent = new DaoCourseStudentImpl(connection);

            serviceTeacher = new ServiceTeacherImpl(daoTeacher, TeacherMapper.INSTANCE);
            serviceCourse = new ServiceCourseImpl(daoCourse, CourseMapper.INSTANCE);
            serviceStudent = new ServiceStudentImpl(daoStudent, StudentMapper.INSTANCE);
            serviceCourseStudent = new ServiceCourseStudentImpl(daoCourseStudent);

        } catch (SQLException ex) {
            logger.severe("Connection exception: " + ex.getMessage());
        }
    }
}

