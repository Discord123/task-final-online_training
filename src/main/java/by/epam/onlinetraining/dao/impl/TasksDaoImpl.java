package by.epam.onlinetraining.dao.impl;

import by.epam.onlinetraining.dao.AbstractDao;
import by.epam.onlinetraining.dao.TasksDao;
import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exceptions.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TasksDaoImpl extends AbstractDao implements TasksDao {
    private static final Logger LOGGER = LogManager.getLogger(TasksDaoImpl.class);

    private static final String FIND_RECEIVED_TASKS =
            "SELECT * FROM tasks " +
                    "INNER JOIN students_has_tasks " +
                    "AS sht " +
                    "ON tasks_task_id = tasks.task_id " +
                    "WHERE sht.users_user_id=?";

    private static final String FIND_TASKS_BY_COURSE_ID =
            "SELECT * FROM tasks " +
            "WHERE task_course_id=?";

    private static final String ADD_NEW_TASK =
            "INSERT INTO tasks " +
                    "(task_name, task_description, task_course_id) " +
                    "VALUES (?, ?, ?)";

    @Override
    public List<Task> findTasksByCourseId(int courseId) throws DaoException {
        List<Task> taskList = new ArrayList<>();
        try (PreparedStatement statement = proxyConnection.prepareStatement(FIND_TASKS_BY_COURSE_ID)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = ResultSetParser.createTask(resultSet);
                taskList.add(task);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Fail to find tasks by course ID in DAO.", e);
            throw new DaoException("Fail to find tasks by course ID in DAO.", e);
        }
        return taskList;
    }

    @Override
    public List<TaskDto> findReceivedTasks(Integer userId) throws DaoException {
        List<TaskDto> receivedTaskDtoList = new ArrayList<>();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_RECEIVED_TASKS)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                TaskDto taskDto = ResultSetParser.createTaskDto(resultSet);
                receivedTaskDtoList.add(taskDto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Exception during find received tasks process in DAO.", e);
            throw new DaoException("Exception during find received tasks process in DAO.", e);
        }
        return receivedTaskDtoList;
    }

    @Override
    public boolean addTask(int courseId, String taskName, String taskDescription) throws DaoException {
        boolean isAdded = false;
        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_NEW_TASK)){
            statement.setString(1, taskName);
            statement.setString(2, taskDescription);
            statement.setInt(3, courseId);
            isAdded = statement.executeUpdate() != 0;
        } catch (SQLException e){
            LOGGER.log(Level.FATAL, "Fail to add new task in DAO.", e);
            throw new DaoException("Fail to add new task in DAO.", e);
        }
        return isAdded;
    }
}
