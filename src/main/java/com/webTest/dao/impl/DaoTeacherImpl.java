package com.webTest.dao.impl;

import com.webTest.dao.Dao;
import com.webTest.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DaoTeacherImpl implements Dao<Teacher> {
    private final Connection connection;
    private final Logger logger = Logger.getLogger(DaoTeacherImpl.class.getName());


    public DaoTeacherImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(Teacher teacher) {
        String sql = "INSERT INTO teachers (teacher_name, teacher_age, teacher_phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getAge());
            preparedStatement.setInt(3, teacher.getAge());
            preparedStatement.executeUpdate();

            if (generatedKeys.next()) {
                teacher.setId(generatedKeys.getInt(1));
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Teacher creation exception: " + ex.getMessage());
            return -1;
        }
    }

    @Override
    public List<Teacher> read() {
        String query = "SELECT id, teacher_name, teacher_age, teacher_phone_number FROM teachers;";
        List<Teacher> teachers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("teacher_name"));
                teacher.setAge(rs.getInt("teacher_age"));
                teacher.setPhoneNumber(rs.getString("teacher_phone_number"));
                teachers.add(teacher);
            }
            return teachers;

        } catch (SQLException ex) {
            logger.severe("Teacher reading exception: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public int update(Teacher teacher) {
        String query = "UPDATE teachers SET teacher_name = ?, teacher_age = ?, teacher_phone_number = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getAge());
            preparedStatement.setString(3, teacher.getPhoneNumber());
            preparedStatement.setInt(4, teacher.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Exception updating teacher: " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM teachers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("Exception deleting teacher: " + ex.getMessage());
            return 0;
        }
    }

}

