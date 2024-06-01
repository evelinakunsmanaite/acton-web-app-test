package com.webTest.dao;

import com.webTest.dao.impl.DaoCourseImpl;
import com.webTest.model.Course;
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
public class DaoCourseImplTest {

    @Container
    public static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private Connection connection;
    private DaoCourseImpl daoCourse;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword());
        daoCourse = new DaoCourseImpl(connection);
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE teachers (id SERIAL PRIMARY KEY, teacher_name VARCHAR(255), teacher_phone_number VARCHAR(255))");
            stmt.execute("CREATE TABLE courses (id SERIAL PRIMARY KEY, course_name VARCHAR(255), teacher_id INT)");
            stmt.execute("CREATE TABLE students (id SERIAL PRIMARY KEY, student_name VARCHAR(255))");
            stmt.execute("CREATE TABLE student_course (student_id INT, course_id INT, PRIMARY KEY (student_id, course_id))");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS student_course");
            stmt.execute("DROP TABLE IF EXISTS students");
            stmt.execute("DROP TABLE IF EXISTS courses");
            stmt.execute("DROP TABLE IF EXISTS teachers");
        }
        connection.close();
    }

    @Test
     void testCreate() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_phone_number) VALUES ('Lina', '1234567890')");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        Course course = new Course(1, "Math", "Lina", "1234567890", List.of("Max", "Mary"));

        int result = daoCourse.create(course);
        assertEquals(1, result);
        assertTrue(course.getId() > 0);
    }

    @Test
     void testRead() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_phone_number) VALUES ('Lina', '1234567890')");
            stmt.execute("INSERT INTO courses (course_name, teacher_id) VALUES ('Math', 1)");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Alice')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Bob')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (2, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        List<Course> courses = daoCourse.read();
        assertEquals(1, courses.size());
        assertEquals("Math", courses.get(0).getCourseName());
        assertEquals("Lina", courses.get(0).getTeacherName());
        assertEquals("1234567890", courses.get(0).getTeacherPhoneNumber());
        assertEquals(2, courses.get(0).getStudentList().size());
    }

    @Test
     void testUpdate() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_phone_number) VALUES ('Lina', '1234567890')");
            stmt.execute("INSERT INTO courses (course_name, teacher_id) VALUES ('Math', 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        Course course = new Course(1, "Bio", "Evelina", "1234567890", List.of("Alice", "Bob"));

        int result = daoCourse.update(course);
        assertEquals(1, result);
    }

    @Test
     void testDelete() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO teachers (teacher_name, teacher_phone_number) VALUES ('Lina', '1234567890')");
            stmt.execute("INSERT INTO courses (course_name, teacher_id) VALUES ('Math', 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        int result = daoCourse.delete(1);
        assertEquals(1, result);
    }
}
