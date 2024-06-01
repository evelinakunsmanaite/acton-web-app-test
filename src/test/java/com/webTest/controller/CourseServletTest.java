package com.webTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webTest.dto.CourseDto;
import com.webTest.service.Service;
import com.webTest.service.ServiceCourseStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServletTest {

    @Mock
    private Service<CourseDto> serviceCourse;

    @Mock
    private ServiceCourseStudent serviceCourseStudent;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private CourseServlet servlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new CourseServlet();
        servlet.serviceCourse = serviceCourse;
        servlet.serviceCourseStudent = serviceCourseStudent;
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<CourseDto> courses = Arrays.asList(
                new CourseDto(1, "Course1", "Lina", "+375333175235", Arrays.asList("Student1", "Student2")),
                new CourseDto(2, "Course2", "Mary", "+375294567656", Arrays.asList("Student3", "Student4"))
        );
        when(serviceCourse.read()).thenReturn(courses);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        List<CourseDto> responseCourses = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertEquals(courses, responseCourses);
    }

    @Test
    void testDoPost() throws Exception {
        CourseDto courseDto = new CourseDto(1, "Course1", "Teacher1", "+375333175235", Arrays.asList("Student1", "Student2"));
        when(serviceCourse.create(any(CourseDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(courseDto))));

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
        CourseDto courseDto = new CourseDto(1, "Course1", "Teacher1", "+375333175235", Arrays.asList("Student1", "Student2"));
        when(serviceCourse.update(any(CourseDto.class))).thenReturn(true);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(courseDto))));

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
        when(serviceCourseStudent.deleteCourseConnection(1)).thenReturn(true);
        when(serviceCourse.delete(1)).thenReturn(true);

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
    void testDoAddCourseToStudent() throws Exception {
        when(request.getParameter("idStudent")).thenReturn("1");
        when(request.getParameter("idCourse")).thenReturn("1");
        when(serviceCourseStudent.create(1, 1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doAddCourseToStudent(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = stringWriter.toString();
        assertEquals("{\"success\":true}", jsonResponse);
    }
}