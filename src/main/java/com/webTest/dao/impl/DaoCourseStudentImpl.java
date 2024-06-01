package com.webTest.dao.impl;

import com.webTest.dao.DaoCourseStudent;
import com.webTest.model.CourseStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DaoCourseStudentImpl implements DaoCourseStudent {
    Connection connection;
    private final Logger logger = Logger.getLogger(DaoCourseStudentImpl.class.getName());

    public DaoCourseStudentImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(CourseStudent courseStudent) {
        String sql = "INSERT INTO student_course (student_id, course_id) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, courseStudent.getIdStudent());
            preparedStatement.setInt(2, courseStudent.getIdCourse());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                courseStudent.setId(generatedKeys.getInt(1));
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Link table creation exception: " + ex.getMessage());
            return -1;
        }
    }

    @Override
    public List<String> readCourseByStudentId(int idStudent) {
        String sql = "SELECT c.course_name " +
                "FROM courses c " +
                "JOIN student_course sc ON c.id = sc.course_id " +
                "WHERE sc.student_id = ?";
        List<String> listCoursesNames = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listCoursesNames.add(resultSet.getString("course_name"));
            }

            return listCoursesNames;
        } catch (SQLException ex) {
            logger.severe("Course by student id reading exception: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateCourseListInStudent(int idStudent, int idCourseOld, int idCourseNew) {
        String sql = "UPDATE student_course SET course_id = ? WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCourseNew);
            ps.setInt(2, idStudent);
            ps.setInt(3, idCourseOld);
            return true;
        } catch (SQLException ex) {
            logger.severe("Exception updating course in link table: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStudentListInCourse(int idCourse, int idStudentOld, int idStudentNew) {
        String sql = "UPDATE student_course SET student_id = ? WHERE course_id = ? AND student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idStudentNew);
            ps.setInt(2, idCourse);
            ps.setInt(3, idStudentOld);
            return true;
        } catch (SQLException ex) {
            logger.severe("Exception updating student in link table: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCourseConnection(int idCourse) {
        String sql = "DELETE FROM course_student WHERE course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCourse);
            return true;
        } catch (SQLException ex) {
            logger.severe("Exception deleting course link table: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteStudentConnection(int idStudent) {
        String sql = "DELETE FROM course_student WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            return true;
        } catch (SQLException ex) {
            logger.severe("Exception deleting student link table: " + ex.getMessage());
            return false;
        }
    }
}
