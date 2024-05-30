package com.webTest.dao.impl;

import com.webTest.dao.Dao;
import com.webTest.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class DaoStudentImpl implements Dao<Student> {
    private final Connection connection;
    private final Logger logger = Logger.getLogger(DaoStudentImpl.class.getName());


    public DaoStudentImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(Student student) {
        String sql = "INSERT INTO students (student_name, student_age) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());

            if (resultSet.next()) {
                student.setId(resultSet.getInt(1));
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Student creation exception: " + ex.getMessage());
            return -1;
        }

    }

    @Override
    public List<Student> read() {
        String query = "SELECT s.id, s.student_name, s.student_age, c.course_name" +
                "FROM students s" +
                "JOIN student_course sc ON s.id = sc.student_id" +
                "JOIN courses c ON sc.course_id = c.id";
        List<Student> students = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                int studentId = rs.getInt("id");
                String studentName = rs.getString("student_name");
                int studentAge = rs.getInt("student_age");
                String courseName = rs.getString("course_name");

                Student student  = students.stream().filter(s -> s.getId() == studentId).findFirst().orElse(null);

                if (student == null) {
                    student = new Student(studentId, studentName, studentAge, new ArrayList<>());
                }
                student.getCoursesList().add(courseName);
                students.add(student);
            }

            return students;
        } catch (SQLException ex) {
            logger.severe("Student reading exception: " + ex.getMessage());
            return Collections.emptyList();
        }


    }

    @Override
    public int update(Student student) {
        String query = "UPDATE students SET student_name = ?, student_age = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setInt(3, student.getId());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Exception updating student: " + ex.getMessage());
            return -1;
        }
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Exception deleting student: " + ex.getMessage());
            return -1;
        }
    }

}
