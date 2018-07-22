package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import by.epam.onlinetraining.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendReviewCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(SendReviewCommand.class);
    private static final String SHOW_REVIEWS_BY_TASK_ID_PAGE = "/controller?command=showreviewsbytaskid&taskid=";
    private static final String SEND_SUCCESS_MESSAGE = "message.teacher.review-sent-success";
    private static final String SEND_FAIL_MESSAGE = "message.teacher.review-sent-fail";

    private static final String TASK_ID_PARAM = "task_id";
    private static final String USER_ID_PARAM = "user_id";
    private static final String TASK_MARK_PARAM = "task_mark";
    private static final String TASK_REVIEW_PARAM = "task_review";


    public SendReviewCommand() {
        super(ServiceManager.getReviewService());
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean isSent = false;

        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);
        String userIdLine = content.getSingleRequestParameter(USER_ID_PARAM);
        int userId = Integer.parseInt(userIdLine);
        String markLine = content.getSingleRequestParameter(TASK_MARK_PARAM);
        int mark = Integer.parseInt(markLine);
        String taskReview = content.getSingleRequestParameter(TASK_REVIEW_PARAM);

        try{
            ReviewService reviewService = (ReviewService) getService();
            isSent = reviewService.sendReview(taskId, userId, mark, taskReview);
        } catch (ServiceException e){
            Logger.log(Level.FATAL,"Fail to execute send review command.");
            throw new CommandException("Fail to execute send review command.",e);
        }
        String showReviewsPage = SHOW_REVIEWS_BY_TASK_ID_PAGE + taskIdLine;

        putMessageIntoSession(content, isSent, SEND_SUCCESS_MESSAGE, SEND_FAIL_MESSAGE);

        return new RequestResult(showReviewsPage, NavigationType.REDIRECT);
    }
}
