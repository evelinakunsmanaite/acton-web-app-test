package com.webTest.service;

import com.webTest.dao.Dao;
import com.webTest.dto.StudentDto;
import com.webTest.model.Student;
import com.webTest.service.impl.ServiceStudentImpl;
import com.webTest.util.StudentMapper;
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

class ServiceStudentImplTest {
        @Mock
        private Dao<Student> daoStudent;

        @Mock
        private StudentMapper studentMapper;

        @InjectMocks
        private ServiceStudentImpl serviceStudent;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreate() {
            StudentDto studentDto = new StudentDto(1, "Student", 25, Arrays.asList("Math", "Bio"));
            Student student = new Student(1, "Student", 25, Arrays.asList("Math", "Bio"));
            when(studentMapper.studentDtoToStudent(any(StudentDto.class))).thenReturn(student);
            when(daoStudent.create(any(Student.class))).thenReturn(1);

            boolean result = serviceStudent.create(studentDto);

            assertTrue(result);
            verify(daoStudent).create(student);
        }

        @Test
        void testRead() {
            List<Student> students = Arrays.asList(
                    new Student(1, "Student", 25, Arrays.asList("Math", "Bio")),
                    new Student(2, "Student2", 22, Arrays.asList("Math", "Physics")));
            when(daoStudent.read()).thenReturn(students);
            when(studentMapper.studentToStudentDto(any(Student.class))).thenAnswer(invocation -> {
                Student student = invocation.getArgument(0);
                return new StudentDto(student.getId(), student.getName(), student.getAge(), student.getCoursesList());
            });

            List<StudentDto> studentDtoList = serviceStudent.read();

            assertNotNull(studentDtoList);
            assertEquals(students.size(), studentDtoList.size());
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                StudentDto studentDto = studentDtoList.get(i);
                assertEquals(student.getId(), studentDto.getId());
                assertEquals(student.getName(), studentDto.getName());
                assertEquals(student.getAge(), studentDto.getAge());
                assertEquals(student.getCoursesList(), studentDto.getCoursesList());
            }
        }

        @Test
        void testUpdate() {
            StudentDto studentDto = new StudentDto(1, "Student", 25, Arrays.asList("Math", "Bio"));
            Student student = new Student(1, "Student", 25, Arrays.asList("Math", "Bio"));
            when(studentMapper.studentDtoToStudent(any(StudentDto.class))).thenReturn(student);
            when(daoStudent.update(any(Student.class))).thenReturn(1);

            boolean result = serviceStudent.update(studentDto);

            assertTrue(result);
            verify(daoStudent).update(student);
        }

        @Test
        void testDelete() {
            int studentId = 1;
            when(daoStudent.delete(studentId)).thenReturn(1);

            boolean result = serviceStudent.delete(studentId);

            assertTrue(result);
            verify(daoStudent).delete(studentId);
        }
    }