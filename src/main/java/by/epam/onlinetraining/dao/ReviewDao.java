package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exception.DaoException;

import java.util.List;

public interface ReviewDao {
    boolean sendAnswer(int taskId, int userId, String answer) throws DaoException;
    boolean sendReview(int taskId, int userId, int mark, String review) throws DaoException;
    List<ReviewDto> findAllReviewsByTaskId(int taskId) throws DaoException;
}
