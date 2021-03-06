package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.*;
import by.epam.onlinetraining.dao.impl.CoursesDaoImpl;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CourseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourseServiceImpl implements CourseService {
    private static final Logger Logger = LogManager.getLogger(CourseServiceImpl.class);
    private static CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();

    @Override
    public List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try (TransactionManager tm = TransactionManager.launchQuery(coursesDAO)) {
            courseDtoList = coursesDAO.findRelatedCourses(teacherId);
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Exception during finding teacher related courses process.", e);
            throw new ServiceException("Exception during finding teacher related courses process.", e);
        }

        return courseDtoList;
    }

    @Override
    public boolean updateCourse(int courseId, String courseTitle, int subjectId, String status,
                                int isAvailable, int teacherId) throws ServiceException {

        try (TransactionManager tm = TransactionManager.launchTransaction(coursesDAO)) {
            coursesDAO.updateCourseById(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Fail to process update course service logic.", e);
            throw new ServiceException("Fail to process update course service logic.",e);
        }
        return true;
    }

    @Override
    public List<CourseDto> getAvailableCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try (TransactionManager tm = TransactionManager.launchQuery(coursesDAO)) {
            courseDtoList = coursesDAO.findAvailableCourses(userId);
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        }
        return courseDtoList;
    }

    @Override
    public boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException {

        try (TransactionManager tm = TransactionManager.launchTransaction(coursesDAO)) {
            coursesDAO.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Fail to process add course service logic.", e);
            throw new ServiceException("Fail to process add course service logic.",e);
        }
        System.out.println("CS 69");
        return true;
    }

    @Override
    public List<CourseDto> getTakenCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try (TransactionManager tm = TransactionManager.launchQuery(coursesDAO)) {
            courseDtoList = coursesDAO.findTakenCourses(userId);
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Exception during showing taken courses service process", e);
            throw new ServiceException("Exception during showing taken courses service process", e);
        }

        return courseDtoList;

    }

    @Override
    public List<CourseDto> getAllCourses() throws ServiceException {
        List<CourseDto> courseDtoList = null;

        try (TransactionManager tm = TransactionManager.launchQuery(coursesDAO)) {
            courseDtoList = coursesDAO.findAllCourses();
        } catch (SQLException | DaoException e) {
            Logger.log(Level.FATAL, "Fail to show all courses in service.", e);
            throw new ServiceException("Fail to show all courses in service.",e);
        }

        return courseDtoList;
    }
}
