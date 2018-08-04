package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.CoursesDaoImpl;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CoursesServiceImpl implements CoursesService {
    private static final Logger Logger = LogManager.getLogger(CoursesServiceImpl.class);

    @Override
    public List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try {
            helper.beginTransaction(coursesDAO);
            courseDtoList = coursesDAO.findRelatedCourses(teacherId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Exception during finding teacher related courses process.", e);
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
        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(coursesDAO);
            isUpdated = coursesDAO.updateCourseById(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to process update course service logic.", e);
            throw new ServiceException("Fail to process update course service logic.",e);
        } finally {
            helper.endTransaction();
        }
        return isUpdated;
    }

    @Override
    public List<CourseDto> getAvailableCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try {
            helper.beginTransaction(coursesDAO);
            courseDtoList = coursesDAO.findAvailableCourses(userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;
    }

    @Override
    public boolean addCourse(String courseTitle, int subjectId, String status, int isAvailable, int teacherId) throws ServiceException {
        boolean isAdded = false;

        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(coursesDAO);
            isAdded = coursesDAO.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to process add course service logic.", e);
            throw new ServiceException("Fail to process add course service logic.",e);
        }finally {
            helper.endTransaction();
        }
        return isAdded;
    }

    @Override
    public List<CourseDto> getTakenCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try {
            helper.beginTransaction(coursesDAO);
            courseDtoList = coursesDAO.findTakenCourses(userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Exception during showing taken courses service process", e);
            throw new ServiceException("Exception during showing taken courses service process", e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;

    }

    @Override
    public List<CourseDto> getAllCourses() throws ServiceException {
        List<CourseDto> courseDtoList = null;

        CoursesDaoImpl coursesDAO = DAOManager.getCoursesDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(coursesDAO);
            courseDtoList = coursesDAO.findAllCourses();
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to show all courses in service.", e);
            throw new ServiceException("Fail to show all courses in service.",e);
        } finally {
            helper.endTransaction();
        }
        return courseDtoList;
    }
}
