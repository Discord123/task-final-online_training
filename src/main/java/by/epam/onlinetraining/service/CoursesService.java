package by.epam.onlinetraining.service;

import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.ServiceException;

import java.util.List;

public interface CoursesService extends Service {
    List<CourseDto> getAvailableCourses(int userId) throws ServiceException;
    List<CourseDto> getAllCourses() throws ServiceException;
    List<CourseDto> getTakenCourses(int userId) throws ServiceException;
    boolean updateCourse(int courseId, String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException;
    boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException;
    List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException;
}
