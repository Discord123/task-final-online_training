package by.epam.onlinetraining.dao.impl;

import by.epam.onlinetraining.dao.AbstractDao;
import by.epam.onlinetraining.dao.CoursesDao;
import by.epam.onlinetraining.dao.pool.ProxyConnection;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CoursesDaoImpl extends AbstractDao implements CoursesDao {
    private static final Logger Logger = LogManager.getLogger(CoursesDaoImpl.class);

    private static final String FIND_RELATED_COURSES =
            "SELECT course_id, " +
                    "course_title, " +
                    "course_subject_id, " +
                    "course_status, " +
                    "course_isAvailable, " +
                    "course_teacher_id, " +
                    "subject_id, " +
                    "subject_language, " +
                    "subject_level, " +
                    "user_id, " +
                    "user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role, " +
                    "user_isDeleted " +
                    "FROM courses " +
                    "LEFT JOIN subjects " +
                    "ON courses.course_subject_id = subjects.subject_id "+
                    "LEFT JOIN users " +
                    "ON courses.course_teacher_id = users.user_id " +
                    "WHERE course_teacher_id=?";

    private static final String FIND_TAKEN_COURSES_AND_RELATED_DATA =
            "SELECT course_id, " +
                    "course_title, " +
                    "course_subject_id, " +
                    "course_status, " +
                    "course_isAvailable, " +
                    "course_teacher_id, " +
                    "user_id, " +
                    "user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role, " +
                    "user_isDeleted, " +
                    "subject_id, " +
                    "subject_language, " +
                    "subject_level, " +
                    "courses_course_id, " +
                    "users_user_id " +
                    "FROM courses " +
                    "LEFT JOIN users " +
                    "ON courses.course_teacher_id = users.user_id " +
                    "LEFT JOIN subjects " +
                    "ON courses.course_subject_id = subjects.subject_id "+
                    "LEFT JOIN students_courses AS chs " +
                    "ON courses.course_id = chs.courses_course_id " +
                    "WHERE chs.users_user_id=?";

    private static final String FIND_AVAILABLE_COURSES_AND_RELATED_DATA =
            "SELECT course_id, " +
                    "course_title, " +
                    "course_subject_id, " +
                    "course_status, " +
                    "course_isAvailable, " +
                    "course_teacher_id, " +
                    "user_id, " +
                    "user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role, " +
                    "user_isDeleted, " +
                    "subject_id, " +
                    "subject_language, " +
                    "subject_level " +
                    "FROM courses " +
                    "LEFT JOIN users " +
                    "ON courses.course_teacher_id = users.user_id " +
                    "LEFT JOIN subjects " +
                    "ON courses.course_subject_id = subjects.subject_id " +
                    "WHERE course_isAvailable=1 " +
                    "AND course_id NOT IN( " +
                    "SELECT courses_course_id " +
                    "FROM students_courses " +
                    "AS taken_courses " +
                    "WHERE users_user_id=?)";

    private static final String FIND_ALL_COURSES_AND_RELATED_DATA =
            "SELECT course_id, " +
                    "course_title, " +
                    "course_subject_id, " +
                    "course_status, " +
                    "course_isAvailable, " +
                    "course_teacher_id, " +
                    "user_id, " +
                    "user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role, " +
                    "user_isDeleted, " +
                    "subject_id, " +
                    "subject_language, " +
                    "subject_level " +
                    "FROM courses " +
                    "LEFT JOIN users " +
                    "ON courses.course_teacher_id = users.user_id " +
                    "LEFT JOIN subjects " +
                    "ON courses.course_subject_id = subjects.subject_id";

    private static final String UPDATE_COURSE_BY_ID =
            "UPDATE courses " +
                    "SET course_title=?, " +
                    "course_subject_id=?, " +
                    "course_status=?, " +
                    "course_isAvailable=?, " +
                    "course_teacher_id=? " +
                    "WHERE course_id=?";

    private static final String INSERT_NEW_COURSE =
            "INSERT INTO courses " +
                    "(course_title, " +
                    "course_subject_id, " +
                    "course_status, " +
                    "course_isAvailable, " +
                    "course_teacher_id) " +
                    "VALUES (?,?,?,?,?)";


    @Override
    public List<CourseDto> findRelatedCourses(int teacherID) throws DaoException {

        List<CourseDto> courseDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_RELATED_COURSES)){
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                courseDtoList.add(courseDto);
            }
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to find all teacher related courses in DAO.", e);
            throw new DaoException("Fail to find all teacher related courses in DAO.", e);
        }

        return courseDtoList;
    }

    @Override
    public List<CourseDto> findAvailableCourses(int userId) throws DaoException {

        List<CourseDto> courseDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_AVAILABLE_COURSES_AND_RELATED_DATA)){
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                courseDtoList.add(courseDto);
            }
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Exception during find available courses process in DAO.", e);
            throw new DaoException("Exception during find available courses process in DAO.", e);
        }

        return courseDtoList;
    }

    @Override
    public List<CourseDto> findTakenCourses(int userId) throws DaoException {

        List<CourseDto> takenCourses = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try (PreparedStatement statement = proxyConnection.prepareStatement(FIND_TAKEN_COURSES_AND_RELATED_DATA)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                takenCourses.add(courseDto);
            }
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Exception during find taken courses process in DAO.", e);
            throw new DaoException("Exception during find taken courses process in DAO.", e);
        }

        return takenCourses;
    }

    @Override
    public boolean updateCourseById(int courseId, String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws DaoException {

        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_COURSE_BY_ID)){
            statement.setString(1, courseTitle);
            statement.setInt(2, subjectId);
            statement.setString(3, status);
            statement.setInt(4, isAvailable);
            if(teacherId == 0){
                statement.setNull(5, Types.NULL);
            } else {
                statement.setInt(5, teacherId);
            }
            statement.setInt(6, courseId);
            statement.executeUpdate();
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to update course by ID in DAO.", e);
            throw new DaoException("Fail to update course by ID in DAO.", e);
        }
        return true;
    }

    @Override
    public List<CourseDto> findAllCourses() throws DaoException {

        List<CourseDto> allCoursesDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_COURSES_AND_RELATED_DATA)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                allCoursesDtoList.add(courseDto);
            }
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Exception during find all courses process in DAO.", e);
            throw new DaoException("Exception during find all courses process in DAO.", e);
        }

        return allCoursesDtoList;
    }

    @Override
    public boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws DaoException {
        ProxyConnection connection = connectionThreadLocal.get();

        try(PreparedStatement statement = connection.prepareStatement(INSERT_NEW_COURSE)){
            statement.setString(1, courseTitle);
            statement.setInt(2, subjectId);
            statement.setString(3, status);
            statement.setInt(4, isAvailable);
            if (teacherId == 0){
                statement.setNull(5, Types.NULL);
            } else {
                statement.setInt(5, teacherId);
            }
            statement.executeUpdate();
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to insert new course in DAO.", e);
            throw new DaoException("Fail to insert new course in DAO.", e);
        }
        return true;
    }
}
