    package com.webTest.dao.impl;
    
    import com.webTest.dao.Dao;
    import com.webTest.model.Course;
    
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    import java.util.logging.Logger;
    
    
    public class DaoCourseImpl implements Dao<Course> {
        private final Connection connection;
        private static final String GET_ID_TEACHER = "SELECT id FROM teachers " +
                "WHERE teacher_name = ? AND teacher_phone_number = ?";
        private final Logger logger = Logger.getLogger(DaoCourseImpl.class.getName());
    
    
        public DaoCourseImpl(Connection connection) {
            this.connection = connection;
        }
    
        @Override
        public int create(Course course) {
            String sql = "INSERT INTO courses (course_name, teacher_id) " +
                    "VALUES (?, (" + GET_ID_TEACHER + "))";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, course.getCourseName());
                preparedStatement.setString(2, course.getTeacherName());
                preparedStatement.setString(3, course.getTeacherPhoneNumber());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                }

                return 1;
            } catch (SQLException ex) {
                logger.severe("Course creation exception: " + ex.getMessage());
                return -1;
            }
        }
    
        @Override
        public List<Course> read() {
            String sql = "SELECT c.id, c.course_name, t.teacher_name, t.teacher_phone_number, s.student_name " +
                    "FROM courses c " +
                    "JOIN teachers t ON c.teacher_id = t.id " +
                    "JOIN student_course sc ON c.id = sc.course_id " +
                    "JOIN students s ON sc.student_id = s.id";
            List<Course> courses = new ArrayList<>();
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet courseResultSet = preparedStatement.executeQuery();
    
                while (courseResultSet.next()) {
                    int courseId = courseResultSet.getInt("id");
                    String courseName = courseResultSet.getString("course_name");
                    String teacherName = courseResultSet.getString("teacher_name");
                    String teacherPhoneNumber = courseResultSet.getString("teacher_phone_number");
                    String studentName = courseResultSet.getString("student_name");
    
                    Course course = courses.stream().filter(c -> c.getId() == courseId).findFirst().orElse(null);
    
                    if (course == null) {
                        course = new Course(courseId, courseName, teacherName, teacherPhoneNumber, new ArrayList<>());
                        courses.add(course);
                    }
                    course.getStudentList().add(studentName);

                }
                return courses;
            } catch (SQLException ex) {
                logger.severe("Course reading exception: " + ex.getMessage());
                return Collections.emptyList();
            }
        }
    
        @Override
        public int update(Course course) {
            String sql = "UPDATE courses SET course_name = ?, teacher_id = (" + GET_ID_TEACHER + ") WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, course.getCourseName());
                statement.setString(2, course.getTeacherName());
                statement.setString(3, course.getTeacherPhoneNumber());
                statement.setInt(4, course.getId());
    
                return statement.executeUpdate();
            } catch (SQLException ex) {
                logger.severe("Exception updating course: " + ex.getMessage());
                return -1;
            }
        }
    
        @Override
        public int delete(int id) {
            String sql = "DELETE FROM Courses WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                return statement.executeUpdate();
            } catch (SQLException ex) {
                logger.severe("Exception deleting course: " + ex.getMessage());
                return -1;
            }
        }
    }
