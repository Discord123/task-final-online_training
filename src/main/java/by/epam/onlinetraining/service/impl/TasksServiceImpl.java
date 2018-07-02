package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.TasksDaoImpl;
import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exceptions.DaoException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.TasksService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TasksServiceImpl implements TasksService {
    private static final Logger LOGGER = LogManager.getLogger(TasksServiceImpl.class);

    @Override
    public List<TaskDto> showReceivedTasks(int userId) throws ServiceException {
        List<TaskDto> taskDtoList;

        TasksDaoImpl tasksDao = new TasksDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(tasksDao);
            taskDtoList = tasksDao.findReceivedTasks(userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        } finally {
            helper.endTransaction();
        }
        return taskDtoList;
    }

    @Override
    public List<Task> showCourseRelatedTasks(int courseId) throws ServiceException {
        List<Task> taskList;

        TasksDaoImpl tasksDao = new TasksDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(tasksDao);
            taskList = tasksDao.findTasksByCourseId(courseId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.FATAL, "Fail to receive tasks by course ID.", e);
            throw new ServiceException("Fail to receive tasks by course ID.", e);
        } finally {
            helper.endTransaction();
        }
        return taskList;
    }

    @Override
    public boolean addTask(int courseId, String taskName, String taskDescription) throws ServiceException {
        boolean isAdded = false;

        TasksDaoImpl tasksDao = new TasksDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(tasksDao);
            isAdded = tasksDao.addTask(courseId, taskName, taskDescription);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            LOGGER.log(Level.FATAL, "Fail to add new task in service.", e);
            throw new ServiceException("Fail to add new task in service.", e);
        } finally {
            helper.endTransaction();
        }
        return isAdded;
    }
}
