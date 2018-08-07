package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dao.ReviewDao;
import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import by.epam.onlinetraining.service.util.ScriptSecurityChecker;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger Logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static ReviewDao reviewDAO = DAOManager.getReviewDao();

    @Override
    public boolean sendReview(int taskId, int userId, int mark, String review) throws ServiceException {
        boolean isSent = false;

        try{
            review = ScriptSecurityChecker.scriptCheck(review);
            isSent = reviewDAO.sendReview(taskId, userId, mark, review);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process sendReview logic.", e);
            throw new ServiceException("Fail to process sendReview logic.", e);
        }

        return isSent;
    }

    @Override
    public boolean sendAnswer(int taskId, int userId, String answer) throws ServiceException {
        boolean result = false;

        try{
            answer = ScriptSecurityChecker.scriptCheck(answer);
            result = reviewDAO.sendAnswer(taskId, userId, answer) ;
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Fail to send answer in service.", e);
            throw new ServiceException("Fail to send answer in service.", e);
        }

        return result;
    }

    @Override
    public List<ReviewDto> getReviewsByTaskId(int taskId) throws ServiceException {
        List<ReviewDto> reviewDtoList = null;

        try{
            reviewDtoList = reviewDAO.findAllReviewsByTaskId(taskId);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to receive all students by task id.", e);
            throw new ServiceException("Fail to receive all students by task id.",e);
        }

        return reviewDtoList;
    }


}
