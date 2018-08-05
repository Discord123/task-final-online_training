package by.epam.onlinetraining.dao.impl;

import by.epam.onlinetraining.command.constant.EntityAttribute;
import by.epam.onlinetraining.dto.*;
import by.epam.onlinetraining.entity.*;
import by.epam.onlinetraining.entity.Language;
import by.epam.onlinetraining.entity.LanguageLevel;
import by.epam.onlinetraining.entity.Role;
import by.epam.onlinetraining.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;


public class ResultSetParser {
    private static final Logger Logger = LogManager.getLogger(ResultSetParser.class);

    static User createUser(ResultSet resultSet) throws DaoException{
        User user = null;
        try{
            Integer id = resultSet.getInt(EntityAttribute.USER_ID);
            String email = resultSet.getString(EntityAttribute.USER_EMAIL);
            String password = resultSet.getString(EntityAttribute.USER_PASSWORD);
            String firstName = resultSet.getString(EntityAttribute.USER_FIRST_NAME);
            String lastName = resultSet.getString(EntityAttribute.USER_LAST_NAME);
            String roleLine = resultSet.getString(EntityAttribute.USER_ROLE);
            String roleValue = roleLine.toUpperCase();
            Role role = Role.valueOf(roleValue);

            user = new User(id, email, password, firstName, lastName, role);

        }catch (SQLException e){
            Logger.log(Level.FATAL, "Can't create a user", e);
            throw new DaoException("Can't create a user", e);
        }
        return user;
    }

    static Course createCourse(ResultSet resultSet) throws DaoException{
        Course course = null;
        try{
            Integer id = resultSet.getInt(EntityAttribute.COURSE_ID);
            String title = resultSet.getString(EntityAttribute.COURSE_TITLE);
            int subjectId = resultSet.getInt(EntityAttribute.COURSE_SUBJECT_ID);
            String statusLine = resultSet.getString(EntityAttribute.COURSE_STATUS);
            String statusValue = statusLine.toUpperCase();
            Status status = Status.valueOf(statusValue);
            Boolean isAvailable = resultSet.getBoolean(EntityAttribute.COURSE_IS_AVAILABLE);
            int teacherId = resultSet.getInt(EntityAttribute.COURSE_TEACHER_ID);

            course = new Course(id, title, subjectId, status, isAvailable, teacherId);


        }catch (SQLException e){
            Logger.log(Level.FATAL, "Can't create a course", e);
            throw new DaoException("Can't create a course", e);
        }
        return course;
    }

    static Subject createSubject(ResultSet resultSet) throws DaoException{
        Subject subject = null;
        try{
            Integer id = resultSet.getInt(EntityAttribute.SUBJECT_ID);
            String languageLine = resultSet.getString(EntityAttribute.SUBJECT_LANGUAGE);
            String languageValue = languageLine.toUpperCase();
            Language language = Language.valueOf(languageValue);
            String languageLevelLine = resultSet.getString(EntityAttribute.SUBJECT_LEVEL);
            String languageLevelValue = languageLevelLine.toUpperCase();
            LanguageLevel level = LanguageLevel.valueOf(languageLevelValue);

            subject = new Subject(id, language, level);
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Can't create a subject", e);
            throw new DaoException("Can't create a subject", e);
        }
        return subject;
    }

    static Task createTask(ResultSet resultSet) throws DaoException{
        Task task = null;
        try{
            Integer id = resultSet.getInt(EntityAttribute.TASK_ID);
            String name = resultSet.getString(EntityAttribute.TASK_NAME);
            String description = resultSet.getString(EntityAttribute.TASK_DESCRIPTION);
            int courseId = resultSet.getInt(EntityAttribute.TASK_COURSE_ID);

            task = new Task(id, name, description, courseId);

        } catch (SQLException e){
            Logger.log(Level.FATAL, "Can't create a task", e);
            throw new DaoException("Can't create a task", e);
        }
        return task;
    }

    static Review createReview(ResultSet resultSet) throws DaoException {
        Review review = null;
        try{
            Integer userId = resultSet.getInt(EntityAttribute.REVIEW_USER_ID);
            Integer taskId = resultSet.getInt(EntityAttribute.REVIEW_TASK_ID);
            int mark = resultSet.getInt(EntityAttribute.REVIEW_MARK);
            String reviewText = resultSet.getString(EntityAttribute.REVIEW_TEXT);
            String answer = resultSet.getString(EntityAttribute.REVIEW_TASK_ANSWER);

            review = new Review(userId, taskId, answer, reviewText, mark);

        }catch (SQLException e){
            Logger.log(Level.FATAL, "Can't create a review", e);
            throw new DaoException("Can't create a review", e);
        }
        return review;
    }

    static ReviewDto createReviewUsersDto(ResultSet resultSet) throws DaoException{
        Review review = createReview(resultSet);
        User student = createUser(resultSet);

        return new ReviewDto(review, student);
    }

    static CourseDto createCourseDto(ResultSet resultSet) throws DaoException{
        Course course = createCourse(resultSet);
        Subject subject = null;
        User teacher = null;
        try{
            if (resultSet.getInt(EntityAttribute.SUBJECT_ID) != 0){
                subject = createSubject(resultSet);
            }
            if (resultSet.getInt(EntityAttribute.USER_ID) != 0){
                teacher = createUser(resultSet);
            }
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to create course DTO while parsing.", e);
            throw new DaoException("Fail to create course DTO while parsing.", e);
        }

        return new CourseDto(course, subject, teacher);
    }

    static TaskDto createTaskDto(ResultSet resultSet) throws DaoException{
        Task task = createTask(resultSet);
        Review review = createReview(resultSet);
        return  new TaskDto(task, review);
    }
}
