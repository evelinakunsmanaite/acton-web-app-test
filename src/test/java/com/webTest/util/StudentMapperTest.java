package com.webTest.util;

import com.webTest.dto.StudentDto;
import com.webTest.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentMapperTest {

    @Test
    void testStudentToStudentDto(){
        Student student = new Student(1,"Lina", 21, List.of("Math", "Bio"));

        StudentDto studentDto = StudentMapper.INSTANCE.studentToStudentDto(student);

        assertEquals(student.getId(), studentDto.getId());
        assertEquals(student.getName(), studentDto.getName());
        assertEquals(student.getAge(),studentDto.getAge());
        assertEquals(student.getCoursesList(),studentDto.getCoursesList());

    }


    @Test
    void  testStudentDtoToStudent (){

        StudentDto studentDto = new StudentDto(1,"Lina", 21, List.of("Math", "Bio"));

        Student student = StudentMapper.INSTANCE.studentDtoToStudent(studentDto);

        assertEquals(student.getId(), studentDto.getId());
        assertEquals(student.getName(), studentDto.getName());
        assertEquals(student.getAge(),studentDto.getAge());
        assertEquals(student.getCoursesList(),studentDto.getCoursesList());

    }
}
