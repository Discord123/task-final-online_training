package by.epam.onlinetraining.service;

import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exception.ServiceException;

import java.util.List;

public interface TasksService extends Service {
    List<TaskDto> getReceivedTasks(int userId) throws ServiceException;
    List<Task> getCourseRelatedTasks(int courseId) throws ServiceException;
    boolean addTask(int courseId, String taskName, String taskDescription) throws ServiceException;
}
