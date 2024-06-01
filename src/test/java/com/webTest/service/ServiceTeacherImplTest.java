package com.webTest.service;

import com.webTest.dao.Dao;
import com.webTest.dto.TeacherDto;
import com.webTest.model.Teacher;
import com.webTest.service.impl.ServiceTeacherImpl;
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

class ServiceTeacherImplTest {
    @Mock
    private Dao<Teacher> daoTeacher;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private ServiceTeacherImpl serviceTeacher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        TeacherDto teacherDto = new TeacherDto(1, "Teacher", "+375333175235", 35);
        Teacher teacher = TeacherMapper.INSTANCE.teacherDtoToTeacher(teacherDto);
        when(daoTeacher.create(any(Teacher.class))).thenReturn(1);

        boolean result = serviceTeacher.create(teacherDto);

        assertTrue(result);
        verify(daoTeacher).create(teacher);
    }

    @Test
    void testRead() {
        List<Teacher> teachers = Arrays.asList(
                new Teacher(1, "Teacher1", "+375333175235", 35),
                new Teacher(2, "Teacher2", "+375333175258", 40));
        when(daoTeacher.read()).thenReturn(teachers);
        when(teacherMapper.teacherToTeacherDTO(any(Teacher.class))).thenAnswer(invocation -> {
            Teacher teacher = invocation.getArgument(0);
            return new TeacherDto(teacher.getId(), teacher.getName(), teacher.getPhoneNumber(), teacher.getAge());
        });

        List<TeacherDto> teacherDtos = serviceTeacher.read();

        assertNotNull(teacherDtos);
        assertEquals(teachers.size(), teacherDtos.size());
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            TeacherDto teacherDto = teacherDtos.get(i);
            assertEquals(teacher.getId(), teacherDto.getId());
            assertEquals(teacher.getName(), teacherDto.getName());
            assertEquals(teacher.getPhoneNumber(), teacherDto.getPhoneNumber());
            assertEquals(teacher.getAge(), teacherDto.getAge());
        }
    }

    @Test
    void testUpdate() {
        TeacherDto teacherDto = new TeacherDto(1, "Teacher", "+375333175235", 35);
        Teacher teacher = TeacherMapper.INSTANCE.teacherDtoToTeacher(teacherDto);
        when(daoTeacher.update(any(Teacher.class))).thenReturn(1);

        boolean result = serviceTeacher.update(teacherDto);

        assertTrue(result);
        verify(daoTeacher).update(teacher);
    }

    @Test
    void testDelete() {
        int teacherId = 1;
        when(daoTeacher.delete(teacherId)).thenReturn(1);

        boolean result = serviceTeacher.delete(teacherId);

        assertTrue(result);
        verify(daoTeacher).delete(teacherId);
    }
}
