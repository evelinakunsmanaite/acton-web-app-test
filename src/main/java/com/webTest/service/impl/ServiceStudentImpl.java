package com.webTest.service.impl;

import com.webTest.dao.Dao;
import com.webTest.dto.StudentDto;
import com.webTest.model.Student;
import com.webTest.service.Service;
import com.webTest.service.ServiceCourseStudent;
import com.webTest.util.StudentMapper;
import com.webTest.util.TeacherMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceStudentImpl implements Service<StudentDto> {
    private final Dao<Student> daoStudent;
    private final StudentMapper studentMapper;


    public ServiceStudentImpl(Dao<Student> daoStudent, StudentMapper studentMapper){
        this.daoStudent = daoStudent;
        this.studentMapper = studentMapper;

    }

    @Override
    public boolean create(StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        return daoStudent.create(student) > 0;
    }

    @Override
    public List<StudentDto> read() {
        List<Student> students = daoStudent.read();
        return students.stream()
                .map(studentMapper::studentToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        return daoStudent.update(student) > 0;
    }

    @Override
    public boolean delete(int id) {
        return daoStudent.delete(id) > 0;
    }

}
