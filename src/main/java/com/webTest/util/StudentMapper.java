package com.webTest.util;

import com.webTest.dto.StudentDto;

import com.webTest.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

     StudentDto studentToStudentDto(Student student);

     Student studentDtoToStudent(StudentDto student);

}
