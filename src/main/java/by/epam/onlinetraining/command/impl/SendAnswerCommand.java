package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import by.epam.onlinetraining.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class SendAnswerCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(SendAnswerCommand.class);
    private static final String RECEIVED_TASKS_PAGE = "/controller?command=takereceivedtasks";
    private static final String SEND_SUCCESS_MESSAGE = "message.student.answer-send-success";
    private static final String SEND_FAIL_MESSAGE = "message.student.answer-send-fail";
    private static final String TASK_ID_PARAM = "taskid";
    private static final String ANSWER_PARAM = "answer";


    public SendAnswerCommand() {
        super(ServiceManager.getReviewService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isSent = false;
        Map<String, Object> sessionAttributes = content.getSessionAttributes();

        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int userId = user.getId();
        String answer = content.getSingleRequestParameter(ANSWER_PARAM);

        try{
            ReviewService reviewService = (ReviewService) getService();
            isSent = reviewService.sendAnswer(taskId, userId, answer);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Exception during processing send answer command.");
            throw new CommandException("Exception during processing send answer command.", e);
        }

        putMessageIntoSession(content, isSent, SEND_SUCCESS_MESSAGE, SEND_FAIL_MESSAGE);
        return new ActionResult(RECEIVED_TASKS_PAGE, NavigationType.REDIRECT);
    }
}
