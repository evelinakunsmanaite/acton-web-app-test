package com.webTest.util;

import com.webTest.dto.TeacherDto;
import com.webTest.model.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherMapperTest {

    @Test
    void testTeacherToTeacherDTO(){
        Teacher teacher = new Teacher(1,"Lina","+375333175235", 21);

        TeacherDto teacherDto = TeacherMapper.INSTANCE.teacherToTeacherDTO(teacher);

        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getName(), teacherDto.getName());
        assertEquals(teacher.getPhoneNumber(),teacherDto.getPhoneNumber());
        assertEquals(teacher.getAge(),teacherDto.getAge());

    }


    @Test
    void testTeacherDtoToTeacher(){
        TeacherDto teacherDto= new TeacherDto(1,"Lina","+375333175235", 21);

        Teacher teacher  = TeacherMapper.INSTANCE.teacherDtoToTeacher(teacherDto);

        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getName(), teacherDto.getName());
        assertEquals(teacher.getPhoneNumber(),teacherDto.getPhoneNumber());
        assertEquals(teacher.getAge(),teacherDto.getAge());

    }

    }
