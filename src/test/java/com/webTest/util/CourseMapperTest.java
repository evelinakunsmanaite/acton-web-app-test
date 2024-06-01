package com.webTest.util;

import com.webTest.dto.CourseDto;
import com.webTest.model.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class CourseMapperTest {

    @Test
   void testCourseToCourseDTO(){
        Course course = new Course(1,"Math","Lina","+375333175235", List.of("Max", "Leo","Mary"));

        CourseDto courseDto = CourseMapper.INSTANCE.courseToCourseDTO(course);

        assertEquals(course.getId(), courseDto.getId());
        assertEquals(course.getCourseName(), courseDto.getCourseName());
        assertEquals(course.getTeacherName(),courseDto.getTeacherName());
        assertEquals(course.getTeacherPhoneNumber(),courseDto.getTeacherPhoneNumber());
        assertEquals(course.getStudentList(),courseDto.getStudentList());

    }

     @Test
     void testCourseDTOToCourse(){
         CourseDto courseDto = new CourseDto(1,"Math","Lina","+375333175235", List.of("Max", "Leo","Mary"));

         Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);

         assertEquals(course.getId(), courseDto.getId());
         assertEquals(course.getCourseName(), courseDto.getCourseName());
         assertEquals(course.getTeacherName(),courseDto.getTeacherName());
         assertEquals(course.getTeacherPhoneNumber(),courseDto.getTeacherPhoneNumber());
         assertEquals(course.getStudentList(),courseDto.getStudentList());

     }
}
