package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exception.DaoException;

import java.util.List;

public interface TasksDao {
    List<TaskDto> findReceivedTasks(Integer userId) throws DaoException;
    List<Task> findTasksByCourseId(int courseId) throws DaoException;
    boolean addTask(int courseId, String taskName, String taskDescription) throws DaoException;
}
