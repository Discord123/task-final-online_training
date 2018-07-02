package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exceptions.DaoException;

import java.util.List;
import java.util.Set;

public interface ReviewDao {
    boolean sendAnswer(int taskId, int userId, String answer) throws DaoException;
    boolean sendReview(int taskId, int userId, int mark, String review) throws DaoException;
    List<ReviewDto> findAllReviewsByTaskId(int taskId) throws DaoException;
}
