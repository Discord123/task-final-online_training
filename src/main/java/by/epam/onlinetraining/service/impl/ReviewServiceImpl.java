package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.ReviewDaoImpl;
import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger Logger = LogManager.getLogger(ReviewServiceImpl.class);

    @Override
    public boolean sendReview(int taskId, int userId, int mark, String review) throws ServiceException {
        boolean isSent = false;

        ReviewDaoImpl reviewDAO = DAOManager.getReviewDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(reviewDAO);
            review = scriptCheck(review);
            isSent = reviewDAO.sendReview(taskId, userId, mark, review);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to process sendReview logic.", e);
            throw new ServiceException("Fail to process sendReview logic.", e);
        } finally {
            helper.endTransaction();
        }

        return isSent;
    }

    @Override
    public boolean sendAnswer(int taskId, int userId, String answer) throws ServiceException {
        boolean result = false;

        ReviewDaoImpl reviewDAO = DAOManager.getReviewDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(reviewDAO);
            result = reviewDAO.sendAnswer(taskId, userId, answer) ;
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to send answer in service.", e);
            throw new ServiceException("Fail to send answer in service.", e);
        } finally {
            helper.endTransaction();
        }
        return result;
    }

    @Override
    public List<ReviewDto> getReviewsByTaskId(int taskId) throws ServiceException {
        List<ReviewDto> reviewDtoList = null;

        ReviewDaoImpl reviewDAO = DAOManager.getReviewDao();
        TransactionHelper helper = TransactionHelper.get();
        try{
            helper.beginTransaction(reviewDAO);
            reviewDtoList = reviewDAO.findAllReviewsByTaskId(taskId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to receive all students by task id.", e);
            throw new ServiceException("Fail to receive all students by task id.",e);
        } finally {
            helper.endTransaction();
        }
        return reviewDtoList;
    }

    private String scriptCheck(String review) {
        review = review.replace("<script>", "(= ");
        review = review.replace("</script>", " =)");
        return review;
    }
}
