package com.webTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webTest.dto.CourseDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet("/courses")
public class CourseServlet extends InitServlet {
    private final ObjectMapper objectMapper;
    private static final String PATH = "application/json";
    private static final String SUCCESS_PATH = "{\"success\":";
    private final Logger logger = Logger.getLogger(CourseServlet.class.getName());


    public CourseServlet() {
        this.objectMapper = new ObjectMapper();
    }

    private void configureResponse(HttpServletResponse response) {
        response.setContentType(PATH + " charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        configureResponse(response);

        try {
            List<CourseDto> courses = serviceCourse.read();
            String json = objectMapper.writeValueAsString(courses);
            response.setContentType(PATH);
            response.getWriter().write(json);
        } catch (IOException ex) {
            logger.severe("Data output isn't possible. An error has occurred: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        configureResponse(response);

        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && pathInfo.equals("/addCourseToStudent")) {
                doAddCourseToStudent(request, response);
                return;
            }

            CourseDto courseDto = objectMapper.readValue(request.getReader(), CourseDto.class);
            boolean isCreated = serviceCourse.create(courseDto);
            response.setContentType(PATH);
            response.getWriter().write(SUCCESS_PATH + isCreated + "}");
        } catch (IOException ex) {
            logger.severe("Data entry isn't possible. An error has occurred: " + ex.getMessage());

        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        configureResponse(response);

        try {
            CourseDto courseDto = objectMapper.readValue(request.getReader(), CourseDto.class);
            boolean isUpdated = serviceCourse.update(courseDto);
            response.setContentType(PATH);
            response.getWriter().write(SUCCESS_PATH + isUpdated + "}");
        } catch (IOException ex) {
            logger.severe("Data update isn't possible. An error has occurred: " + ex.getMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        configureResponse(response);

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean isDeleted;
                    if (serviceCourseStudent.deleteCourseConnection(id)) isDeleted = serviceCourse.delete(id);
                    else isDeleted = false;
            response.setContentType(PATH);
            response.getWriter().write(SUCCESS_PATH + isDeleted + "}");
        } catch (IOException | NumberFormatException ex) {
            logger.severe("Data course deletion isn't possible. An error has occurred: " + ex.getMessage());
        }

    }

    protected void doAddCourseToStudent(HttpServletRequest request, HttpServletResponse response) {
        configureResponse(response);

        try {
            int idStudent = Integer.parseInt(request.getParameter("idStudent"));
            int idCourse = Integer.parseInt(request.getParameter("idCourse"));
            boolean isAdded = serviceCourseStudent.create(idCourse, idStudent);
            response.setContentType(PATH);
            response.getWriter().write(SUCCESS_PATH + isAdded + "}");
        } catch (IOException | NumberFormatException ex) {
            logger.severe("Add course to student isn't possible. An error has occurred: " + ex.getMessage());
        }

    }
}
