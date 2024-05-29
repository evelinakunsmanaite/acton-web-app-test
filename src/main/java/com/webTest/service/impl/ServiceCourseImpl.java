package com.webTest.service.impl;

import com.webTest.dao.Dao;
import com.webTest.dto.CourseDto;
import com.webTest.model.Course;
import com.webTest.service.Service;
import com.webTest.util.CourseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceCourseImpl implements Service<CourseDto> {
    Dao<Course> daoCourse;
    private final CourseMapper courseMapper;


    public ServiceCourseImpl(Dao<Course> daoCourse, CourseMapper courseMapper){
        this.daoCourse = daoCourse;
        this.courseMapper = courseMapper;

    }

    @Override
    public boolean create(CourseDto courseDto) {
        Course course = courseMapper.courseDtoToCourse(courseDto);
        return daoCourse.create(course) > 0;
    }

    @Override
    public List<CourseDto> read() {
        List<Course> courses = daoCourse.read();
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(CourseDto courseDto) {
        Course course = courseMapper.courseDtoToCourse(courseDto);
        return daoCourse.update(course) > 0;
    }

    @Override
    public boolean delete(int id) {
        return daoCourse.delete(id) > 0;
    }

}
