package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.CoursesDaoImpl;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exceptions.DaoException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CoursesServiceImpl implements CoursesService {
    private static final Logger LOGGER = LogManager.getLogger(CoursesServiceImpl.class);

    @Override
    public List<CourseDto> showRelatedCourses(int teacherId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(coursesDao);
            courseDtoList = coursesDao.findRelatedCourses(teacherId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.FATAL, "Exception during finding teacher related courses process.", e);
            throw new ServiceException("Exception during finding teacher related courses process.", e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;
    }

    @Override
    public boolean updateCourse(int courseId, String courseTitle, int subjectId, String status,
                                int isAvailable, int teacherId) throws ServiceException {

        boolean isUpdated = false;
        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(coursesDao);
            isUpdated = coursesDao.updateCourseById(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            LOGGER.log(Level.FATAL, "Fail to process update course service logic.", e);
            throw new ServiceException("Fail to process update course service logic.",e);
        } finally {
            helper.endTransaction();
        }
        return isUpdated;
    }

    @Override
    public List<CourseDto> showAvailableCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(coursesDao);
            courseDtoList = coursesDao.findAvailableCourses(userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;
    }

    @Override
    public boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException {
        boolean isAdded = false;
        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(coursesDao);
            isAdded = coursesDao.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            LOGGER.log(Level.FATAL, "Fail to process add course service logic.", e);
            throw new ServiceException("Fail to process add course service logic.",e);
        }finally {
            helper.endTransaction();
        }
        return isAdded;
    }

    @Override
    public List<CourseDto> showTakenCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(coursesDao);
            courseDtoList = coursesDao.findTakenCourses(userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.FATAL, "Exception during showing taken courses service process", e);
            throw new ServiceException("Exception during showing taken courses service process", e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;

    }

    @Override
    public List<CourseDto> showAllCourses() throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDao = new CoursesDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(coursesDao);
            courseDtoList = coursesDao.findAllCourses();
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            LOGGER.log(Level.FATAL, "Fail to show all courses in service.", e);
            throw new ServiceException("Fail to show all courses in service.",e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;
    }
}
