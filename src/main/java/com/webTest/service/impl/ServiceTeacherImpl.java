package com.webTest.service.impl;

import com.webTest.dao.Dao;
import com.webTest.dto.TeacherDto;
import com.webTest.model.Teacher;
import com.webTest.service.Service;
import com.webTest.util.StudentMapper;
import com.webTest.util.TeacherMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceTeacherImpl implements Service<TeacherDto> {
    private final Dao<Teacher> daoTeacher;

    public ServiceTeacherImpl(Dao<Teacher> daoTeacher){
        this.daoTeacher = daoTeacher;
    }


    @Override
    public boolean create(TeacherDto teacherDto) {
        Teacher teacher = TeacherMapper.INSTANCE.teacherDtoToTeacher(teacherDto);
        return daoTeacher.create(teacher) > 0;
    }

    @Override
    public List<TeacherDto> read() {
        List<Teacher> teachers = daoTeacher.read();
        return teachers.stream()
                .map(TeacherMapper.INSTANCE::teacherToTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(TeacherDto teacherDto) {
        Teacher teacher = TeacherMapper.INSTANCE.teacherDtoToTeacher(teacherDto);
        return daoTeacher.update(teacher) > 0;
    }

    @Override
    public boolean delete(int id) {
        return daoTeacher.delete(id) > 0;
    }
}
