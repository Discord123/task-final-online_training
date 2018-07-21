package by.epam.onlinetraining.service;

import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exception.ServiceException;

import java.util.List;

public interface ReviewService extends Service {
    boolean sendAnswer(int taskId, int userId, String answer) throws ServiceException;
    boolean sendReview(int taskId, int userId, int mark, String review) throws ServiceException;
    List<ReviewDto> showReviewsByTaskId(int taskId) throws ServiceException;

}
