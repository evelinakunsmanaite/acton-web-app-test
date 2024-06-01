package com.webTest.service;

import com.webTest.dao.DaoCourseStudent;
import com.webTest.model.CourseStudent;
import com.webTest.service.impl.ServiceCourseStudentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceCourseStudentImplTest {

    @Mock
    private DaoCourseStudent daoCourseStudent;

    @InjectMocks
    private ServiceCourseStudentImpl serviceCourseStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourseStudent() {
        when(daoCourseStudent.create(any(CourseStudent.class))).thenReturn(1);

        boolean result = serviceCourseStudent.create(1, 1);

        assertTrue(result);
        verify(daoCourseStudent).create(any(CourseStudent.class));
    }

    @Test
    void testReadCourseByStudentId() {
        List<String> courses = Arrays.asList("Math", "Bio");
        when(daoCourseStudent.readCourseByStudentId(1)).thenReturn(courses);

        List<String> result = serviceCourseStudent.readCourseByStudentId(1);

        assertNotNull(result);
        assertEquals(courses.size(), result.size());
        assertEquals(courses, result);
    }

    @Test
    void testUpdateCourseListInStudent() {
        when(daoCourseStudent.updateCourseListInStudent(anyInt(), anyInt(), anyInt())).thenReturn(true);

        boolean result = serviceCourseStudent.updateCourseListInStudent(1, 1, 2);

        assertTrue(result);
        verify(daoCourseStudent).updateCourseListInStudent(1, 1, 2);
    }

    @Test
    void testUpdateStudentListInCourse() {
        when(daoCourseStudent.updateStudentListInCourse(anyInt(), anyInt(), anyInt())).thenReturn(true);

        boolean result = serviceCourseStudent.updateStudentListInCourse(1, 1, 2);

        assertTrue(result);
        verify(daoCourseStudent).updateStudentListInCourse(1, 1, 2);
    }

    @Test
    void testDeleteCourseConnection() {
        when(daoCourseStudent.deleteCourseConnection(1)).thenReturn(true);

        boolean result = serviceCourseStudent.deleteCourseConnection(1);

        assertTrue(result);
        verify(daoCourseStudent).deleteCourseConnection(1);
    }

    @Test
    void testDeleteStudentConnection() {
        when(daoCourseStudent.deleteStudentConnection(1)).thenReturn(true);

        boolean result = serviceCourseStudent.deleteStudentConnection(1);

        assertTrue(result);
        verify(daoCourseStudent).deleteStudentConnection(1);
    }
}
