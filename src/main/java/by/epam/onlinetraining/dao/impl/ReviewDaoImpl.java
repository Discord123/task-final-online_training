package by.epam.onlinetraining.dao.impl;

import by.epam.onlinetraining.dao.AbstractDao;
import by.epam.onlinetraining.dao.ReviewDao;
import by.epam.onlinetraining.dao.pool.ProxyConnection;
import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl extends AbstractDao implements ReviewDao {
    private static final Logger Logger = LogManager.getLogger(ReviewDaoImpl.class);

    private static final String UPDATE_ANSWER_IN_REWIEW =
            "UPDATE students_tasks " +
                    "SET task_answer=? " +
                    "WHERE users_user_id=? " +
                    "AND tasks_task_id=?";

    private static final String FIND_REVIEWS_AND_STUDENTS_BY_TASK_ID =
            "SELECT users_user_id, " +
                    "tasks_task_id, " +
                    "task_answer, " +
                    "task_review, " +
                    "task_mark, " +
                    "user_id, " +
                    "user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role, " +
                    "user_isDeleted " +
                    "FROM students_tasks " +
                    "AS sht " +
                    "INNER JOIN users " +
                    "ON sht.users_user_id = users.user_id " +
                    "WHERE sht.tasks_task_id=?";

    private static final String SEND_REVIEW =
            "UPDATE students_tasks " +
                    "SET task_mark=?, task_review=? " +
                    "WHERE users_user_id=? " +
                    "AND tasks_task_id=?";
    @Override
    public boolean sendReview(int taskId, int userId, int mark, String review) throws DaoException {

        boolean isSent = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(SEND_REVIEW)){
            statement.setInt(1, mark);
            statement.setString(2, review);
            statement.setInt(3, userId);
            statement.setInt(4, taskId);

            isSent = statement.executeUpdate() != 0;
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to send review in DAO.", e);
            throw new DaoException("Fail to send review in DAO.", e);
        }

        return isSent;
    }

    @Override
    public boolean sendAnswer(int taskId, int userId, String answer) throws DaoException {

        boolean result = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_ANSWER_IN_REWIEW)){
            statement.setString(1, answer);
            statement.setInt(2, userId);
            statement.setInt(3, taskId);

            if(statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Fail to send an answer in DAO.", e);
            throw new DaoException("Fail to send an answer in DAO.", e);
        }

        return result;
    }

    @Override
    public List<ReviewDto> findAllReviewsByTaskId(int taskId) throws DaoException {

        List<ReviewDto> reviewDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_REVIEWS_AND_STUDENTS_BY_TASK_ID)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ReviewDto reviewDto = ResultSetParser.createReviewUsersDto(resultSet);
                reviewDtoList.add(reviewDto);
            }
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Fail to find reviews and users by task id in DAO.", e);
            throw new DaoException("Fail to find reviews and users by task id in DAO.", e);
        }

        return reviewDtoList;
    }
}
