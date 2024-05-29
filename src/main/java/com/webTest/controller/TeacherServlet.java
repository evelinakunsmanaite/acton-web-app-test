package com.webTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webTest.dto.TeacherDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/teachers")
public class TeacherServlet extends InitServlet {
    private final ObjectMapper objectMapper;
    private static final String PATH = "application/json";
    private static final String SUCCESS_PATH = "{\"success\":";
    private final Logger logger = Logger.getLogger(TeacherServlet.class.getName());

    public TeacherServlet() {
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
            List<TeacherDto> teachers = serviceTeacher.read();
            String json = objectMapper.writeValueAsString(teachers);
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
            TeacherDto teacherDto = objectMapper.readValue(request.getReader(), TeacherDto.class);
            boolean isCreated = serviceTeacher.create(teacherDto);
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
            TeacherDto teacherDto = objectMapper.readValue(request.getReader(), TeacherDto.class);
            boolean isUpdated = serviceTeacher.update(teacherDto);
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
            boolean isDeleted = serviceTeacher.delete(id);
            response.setContentType(PATH);
            response.getWriter().write(SUCCESS_PATH + isDeleted + "}");
        } catch (IOException | NumberFormatException ex) {
            logger.severe("Data deletion isn't possible. An error has occurred: " + ex.getMessage());
        }

    }

}
