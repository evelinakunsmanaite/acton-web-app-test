package com.webTest.util;

import com.webTest.dto.TeacherDto;
import com.webTest.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDto teacherToTeacherDTO(Teacher teacher);

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

}

