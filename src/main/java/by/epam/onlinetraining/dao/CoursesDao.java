package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.entity.Course;
import by.epam.onlinetraining.exceptions.DaoException;

import java.util.List;

public interface CoursesDao {
    List<CourseDto> findAvailableCourses(int userId) throws DaoException;
    List<CourseDto> findTakenCourses(int userId) throws DaoException;
    List<CourseDto> findAllCourses() throws DaoException;
    List<CourseDto> findRelatedCourses(int teacherID) throws DaoException;
    boolean updateCourseById(int courseId, String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws DaoException;
    boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId ) throws DaoException;
}
