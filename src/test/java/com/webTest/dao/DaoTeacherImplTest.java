package com.webTest.dao;

import com.webTest.dao.impl.DaoTeacherImpl;
import com.webTest.model.Teacher;
import lombok.var;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DaoTeacherImplTest {

    @Container
    public static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private Connection connection;
    private DaoTeacherImpl daoTeacher;


    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword());
        daoTeacher = new DaoTeacherImpl(connection);
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE teachers (id SERIAL PRIMARY KEY, teacher_name VARCHAR(255), teacher_age INT, teacher_phone_number VARCHAR(255))");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS teachers");
        }
        connection.close();
    }

    @Test
     void testCreate() {
        Teacher teacher = new Teacher(1, "Teacher", "+375333175235", 35);

        int result = daoTeacher.create(teacher);
        assertEquals(1, result);
        assertTrue(teacher.getId() > 0);
    }

    @Test
     void testRead() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_age, teacher_phone_number) VALUES ('Teacher1', 35, '+375333175235')");
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_age, teacher_phone_number) VALUES ('Teacher2', 40, '+375333175265')");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        List<Teacher> teachers = daoTeacher.read();
        assertEquals(2, teachers.size());
        assertEquals("Teacher1", teachers.get(0).getName());
        assertEquals(35, teachers.get(0).getAge());
        assertEquals("+375333175235", teachers.get(0).getPhoneNumber());
        assertEquals("Teacher2", teachers.get(1).getName());
        assertEquals(40, teachers.get(1).getAge());
        assertEquals("+375333175265", teachers.get(1).getPhoneNumber());
    }

    @Test
     void testUpdate() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_age, teacher_phone_number) VALUES ('Teacher2', 36, '+375333175235')");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        Teacher teacher = new Teacher(1, "Teacher", "+375333175235", 36);

        int result = daoTeacher.update(teacher);
        assertEquals(1, result);
    }

    @Test
     void testDelete() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_age, teacher_phone_number) VALUES ('Teacher', 35, '+375333175235')");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        int result = daoTeacher.delete(1);
        assertEquals(1, result);
    }
}
