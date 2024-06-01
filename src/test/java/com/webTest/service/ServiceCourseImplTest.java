package com.webTest.service;

import com.webTest.dao.Dao;
import com.webTest.dto.CourseDto;
import com.webTest.model.Course;
import com.webTest.service.impl.ServiceCourseImpl;
import com.webTest.util.CourseMapper;
import com.webTest.util.TeacherMapper;
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

class ServiceCourseImplTest {
    @Mock
    private Dao<Course> daoCourse;

    @InjectMocks
    private ServiceCourseImpl serviceCourse;

    @Mock
    private CourseMapper courseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        CourseDto courseDto = new CourseDto(1, "Math", "Teacher", "+375333175235", Arrays.asList("Student1", "Student2"));
        Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);
        when(daoCourse.create(any(Course.class))).thenReturn(1);

        boolean result = serviceCourse.create(courseDto);

        assertTrue(result);
        verify(daoCourse).create(course);
    }

    @Test
    void testReadAllCourses() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Math", "Teacher", "+375333175235", Arrays.asList("Student1", "Student2")),
                new Course(2, "Physics", "Teacher1", "+375333175584", Arrays.asList("Student4", "Student3")));
        when(daoCourse.read()).thenReturn(courses);
        when(courseMapper.courseToCourseDTO(any(Course.class)))
                .thenAnswer(invocation -> {
                    Course course = invocation.getArgument(0);
                    return new CourseDto(course.getId(), course.getCourseName(), course.getTeacherName(), course.getTeacherPhoneNumber(), course.getStudentList());
                });

        List<CourseDto> courseDtos = serviceCourse.read();

        assertNotNull(courseDtos);
        assertEquals(courses.size(), courseDtos.size());
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            CourseDto courseDto = courseDtos.get(i);
            assertEquals(course.getId(), courseDto.getId());
            assertEquals(course.getCourseName(), courseDto.getCourseName());
            assertEquals(course.getTeacherName(), courseDto.getTeacherName());
            assertEquals(course.getTeacherPhoneNumber(), courseDto.getTeacherPhoneNumber());
            assertEquals(course.getStudentList(), courseDto.getStudentList());
        }
    }


    @Test
    void testUpdateCourse() {
        CourseDto courseDto = new CourseDto(1, "Math", "Teacher", "+375333175235", Arrays.asList("Student1", "Student3"));
        Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);
        when(daoCourse.update(any(Course.class))).thenReturn(1);

        boolean result = serviceCourse.update(courseDto);

        assertTrue(result);
        verify(daoCourse).update(course);
    }

    @Test
    void testDeleteCourseById() {
        int courseId = 1;
        when(daoCourse.delete(courseId)).thenReturn(1);

        boolean result = serviceCourse.delete(courseId);

        assertTrue(result);
        verify(daoCourse).delete(courseId);
    }
}
