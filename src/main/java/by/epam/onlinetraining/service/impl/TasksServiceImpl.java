package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dao.TasksDao;
import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.TasksService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TasksServiceImpl implements TasksService {
    private static final Logger Logger = LogManager.getLogger(TasksServiceImpl.class);
    private static TasksDao taskDAO = DAOManager.getTasksDao();

    @Override
    public List<TaskDto> getReceivedTasks(int userId) throws ServiceException {
        List<TaskDto> taskDtoList;

        try{
            taskDtoList = taskDAO.findReceivedTasks(userId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Exception during showing available courses service process", e);
            throw new ServiceException("Exception during showing available courses service process", e);
        }

        return taskDtoList;
    }

    @Override
    public List<Task> getCourseRelatedTasks(int courseId) throws ServiceException {
        List<Task> taskList;

        try {
            taskList = taskDAO.findTasksByCourseId(courseId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Fail to receive tasks by course ID.", e);
            throw new ServiceException("Fail to receive tasks by course ID.", e);
        }

        return taskList;
    }

    @Override
    public boolean addTask(int courseId, String taskName, String taskDescription) throws ServiceException {
        boolean isAdded = false;

        try{
            isAdded = taskDAO.addTask(courseId, taskName, taskDescription);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to add new task in service.", e);
            throw new ServiceException("Fail to add new task in service.", e);
        }

        return isAdded;
    }
}
