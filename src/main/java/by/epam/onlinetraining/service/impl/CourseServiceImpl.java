package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.CoursesDao;
import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CourseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private static final Logger Logger = LogManager.getLogger(CourseServiceImpl.class);
    private static CoursesDao coursesDAO = DAOManager.getCoursesDao();

    @Override
    public List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try {
            courseDtoList = coursesDAO.findRelatedCourses(teacherId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Exception during finding teacher related courses process.", e);
            throw new ServiceException("Exception during finding teacher related courses process.", e);
        }

        return courseDtoList;
    }

    @Override
    public boolean updateCourse(int courseId, String courseTitle, int subjectId, String status,
                                int isAvailable, int teacherId) throws ServiceException {

        boolean isUpdated = false;
        try{
            isUpdated = coursesDAO.updateCourseById(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process update course service logic.", e);
            throw new ServiceException("Fail to process update course service logic.",e);
        }

        return isUpdated;
    }

    @Override
    public List<CourseDto> getAvailableCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try {
            courseDtoList = coursesDAO.findAvailableCourses(userId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        }
        return courseDtoList;
    }

    @Override
    public boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException {
        boolean isAdded = false;

        try{
            isAdded = coursesDAO.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process add course service logic.", e);
            throw new ServiceException("Fail to process add course service logic.",e);
        }

        return isAdded;
    }

    @Override
    public List<CourseDto> getTakenCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try {
            courseDtoList = coursesDAO.findTakenCourses(userId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Exception during showing taken courses service process", e);
            throw new ServiceException("Exception during showing taken courses service process", e);
        }

        return courseDtoList;

    }

    @Override
    public List<CourseDto> getAllCourses() throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try{
            courseDtoList = coursesDAO.findAllCourses();
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to show all courses in service.", e);
            throw new ServiceException("Fail to show all courses in service.",e);
        }

        return courseDtoList;
    }
}
