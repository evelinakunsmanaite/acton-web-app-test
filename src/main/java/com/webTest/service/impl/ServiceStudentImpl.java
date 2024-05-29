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
    private final ServiceCourseStudent serviceCourseStudent;


    public ServiceStudentImpl(Dao<Student> daoStudent, StudentMapper studentMapper, ServiceCourseStudent serviceCourseStudent){
        this.daoStudent = daoStudent;
        this.studentMapper = studentMapper;
        this.serviceCourseStudent = serviceCourseStudent;

    }

    @Override
    public boolean create(StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        return daoStudent.create(student) > 0;
    }

    @Override
    public List<StudentDto> read() {
        List<Student> students = daoStudent.read();
        for (Student student : students) {
            student.setCoursesList(serviceCourseStudent.readCourseByStudentId(student.getId()));
        }
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
