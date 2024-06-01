package com.webTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webTest.dto.TeacherDto;
import com.webTest.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeacherServletTest {

    @Mock
    private Service<TeacherDto> serviceTeacher;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private TeacherServlet servlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new TeacherServlet();
        servlet.serviceTeacher = serviceTeacher;
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<TeacherDto> teachers = Arrays.asList(
                new TeacherDto(1, "Teacher1", "+375333175235", 21),
                new TeacherDto(2, "Teacher2", "+375293175235", 26)
        );
        when(serviceTeacher.read()).thenReturn(teachers);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        List<TeacherDto> responseTeachers = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertEquals(teachers, responseTeachers);
    }

    @Test
    void testDoPost() throws Exception {
        TeacherDto teacherDto = new TeacherDto(1, "Teacher1", "+375333175235", 21);
        when(serviceTeacher.create(any(TeacherDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(teacherDto))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }

    @Test
    void testDoPut() throws Exception {
        TeacherDto teacherDto = new TeacherDto(1, "Teacher1", "+375333175235", 21);
        when(serviceTeacher.update(any(TeacherDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(teacherDto))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }

    @Test
    void testDoDelete() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(serviceTeacher.delete(1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doDelete(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }
}
