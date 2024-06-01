package com.webTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webTest.controller.StudentServlet;
import com.webTest.dto.StudentDto;
import com.webTest.model.Student;
import com.webTest.service.Service;
import com.webTest.service.ServiceCourseStudent;
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

class StudentServletTest {

    @Mock
    private Service<StudentDto> serviceStudent;

    @Mock
    private ServiceCourseStudent serviceCourseStudent;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StudentServlet servlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new StudentServlet();
        servlet.serviceStudent = serviceStudent;
        servlet.serviceCourseStudent = serviceCourseStudent;
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<StudentDto> students = Arrays.asList(
                new StudentDto(1, "Student1", 21, Arrays.asList("Course1", "Course2")),
                new StudentDto(2, "Student2", 22, Arrays.asList("Course3", "Course4"))
        );
        when(serviceStudent.read()).thenReturn(students);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        List<StudentDto> responseStudents = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertEquals(students, responseStudents);
    }

    @Test
    void testDoPost() throws Exception {
        StudentDto studentDto = new StudentDto(1, "Student1", 21, Arrays.asList("Course1", "Course2"));
        when(serviceStudent.create(any(StudentDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(studentDto))));

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
        StudentDto studentDto = new StudentDto(1, "Student1", 21, Arrays.asList("Course1", "Course2"));
        when(serviceStudent.update(any(StudentDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(studentDto))));

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
        when(serviceCourseStudent.deleteStudentConnection(1)).thenReturn(true);
        when(serviceStudent.delete(1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doDelete(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }

    @Test
    void testDoAddStudentToCourse() throws Exception {
        when(request.getParameter("idStudent")).thenReturn("1");
        when(request.getParameter("idCourse")).thenReturn("1");
        when(serviceCourseStudent.create(1, 1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doAddStudentToCourse(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }
}
