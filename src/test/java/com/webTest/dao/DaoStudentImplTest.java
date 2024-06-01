package com.webTest.dao;

import com.webTest.dao.impl.DaoStudentImpl;
import com.webTest.model.Student;
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
public class DaoStudentImplTest {

    @Container
    public static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private Connection connection;
    private DaoStudentImpl daoStudent;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword());
        daoStudent = new DaoStudentImpl(connection);
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE teachers (id SERIAL PRIMARY KEY, teacher_name VARCHAR(255), teacher_phone_number VARCHAR(255))");
            stmt.execute("CREATE TABLE courses (id SERIAL PRIMARY KEY, course_name VARCHAR(255), teacher_id INT)");
            stmt.execute("CREATE TABLE students (id SERIAL PRIMARY KEY, student_name VARCHAR(255), student_age INT)");
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
        Student student = new Student(1, "Lina", 20, List.of("Math", "Bio"));

        int result = daoStudent.create(student);
        assertEquals(1, result);
        assertTrue(student.getId() > 0);
    }

    @Test
     void testRead() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO students (student_name, student_age) VALUES ('Lina', 20)");
            stmt.execute("INSERT INTO courses (course_name, teacher_id) VALUES ('Math', 1)");
            stmt.execute("INSERT INTO courses (course_name, teacher_id) VALUES ('Bio', 1)");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 1)");
            stmt.execute("INSERT INTO student_course (student_id, course_id) VALUES (1, 2)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        List<Student> students = daoStudent.read();
        assertEquals(1, students.size());
        assertEquals("Lina", students.get(0).getName());
        assertEquals(20, students.get(0).getAge());
        assertEquals(2, students.get(0).getCoursesList().size());
    }

    @Test
     void testUpdate() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO students (student_name, student_age) VALUES ('Lina', 20)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        Student student = new Student(1, "Lev", 22, List.of("Math", "Bio"));

        int result = daoStudent.update(student);
        assertEquals(1, result);
    }

    @Test
     void testDelete() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO students (student_name, student_age) VALUES ('Lina', 21)");
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }

        int result = daoStudent.delete(1);
        assertEquals(1, result);
    }
}
