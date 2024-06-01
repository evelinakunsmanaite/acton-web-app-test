package com.webTest.dao;

import com.webTest.dao.impl.DaoCourseStudentImpl;
import com.webTest.model.CourseStudent;
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
public class DaoCourseStudentImplTest {

    @Container
    public static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private Connection connection;
    private DaoCourseStudentImpl daoCourseStudent;


    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword());
        daoCourseStudent = new DaoCourseStudentImpl(connection);
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE courses (id SERIAL PRIMARY KEY, course_name VARCHAR(255))");
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
        }
        connection.close();
    }

    @Test
    void testCreate() {
        CourseStudent courseStudent = new CourseStudent(1, 1);

        int result = daoCourseStudent.create(courseStudent);
        assertEquals(1, result, "The result should be the generated ID");
        assertTrue(result > 0, "The courseStudent ID should be greater than 0");
    }


    @Test
    void testReadCourseByStudentId() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO courses (course_name) VALUES ('Math')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Lina')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        List<String> courses = daoCourseStudent.readCourseByStudentId(1);
        assertNotNull(courses, "Courses list should not be null");
        assertEquals(1, courses.size());
        assertEquals("Math", courses.get(0));
    }


    @Test
    void testUpdateCourseListInStudent() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO courses (course_name) VALUES ('Math')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Lina')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        boolean result = daoCourseStudent.updateCourseListInStudent(1, 1, 2);
        assertTrue(result);
    }

    @Test
    void testUpdateStudentListInCourse() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO courses (course_name) VALUES ('Math')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Lina')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        boolean result = daoCourseStudent.updateStudentListInCourse(1, 1, 2);
        assertTrue(result);
    }

    @Test
    void testDeleteCourseConnection() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO courses (course_name) VALUES ('Math')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Lina')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        boolean result = daoCourseStudent.deleteCourseConnection(1);
        assertTrue(result);
    }

    @Test
    void testDeleteStudentConnection() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO courses (course_name) VALUES ('Math')");
            stmt.execute("INSERT INTO students (student_name) VALUES ('Lina')");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        boolean result = daoCourseStudent.deleteStudentConnection(1);
        assertTrue(result);
    }
}

