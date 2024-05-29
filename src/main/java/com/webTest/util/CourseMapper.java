package com.webTest.util;

import com.webTest.dto.CourseDto;
import com.webTest.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto courseToCourseDTO(Course course);

    Course courseDtoToCourse(CourseDto courseDto);


}
