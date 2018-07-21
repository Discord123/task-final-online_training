package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import by.epam.onlinetraining.service.ServiceManager;

import java.util.Map;


public class SendAnswerCommand extends ActionCommand {
    private static final String RECEIVED_TASKS_PAGE = "/controller?command=showreceivedtasks";
    private static final String SEND_SUCCESS_MESSAGE = "message.student.answer-send-success";
    private static final String SEND_FAIL_MESSAGE = "message.student.answer-send-fail";
    private static final String TASK_ID_PARAM = "taskid";
    private static final String ANSWER_PARAM = "answer";


    public SendAnswerCommand() {
        super(ServiceManager.getReviewService());
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
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
            throw new CommandException("Exception during processing send answer command.", e);
        }

        putMessageIntoSession(content, isSent, SEND_SUCCESS_MESSAGE, SEND_FAIL_MESSAGE);
        return new RequestResult(RECEIVED_TASKS_PAGE, NavigationType.REDIRECT);
    }
}
