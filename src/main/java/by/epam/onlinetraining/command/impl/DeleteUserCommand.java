package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class DeleteUserCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(DeleteUserCommand.class);
    private static final String USER_ID_PARAM = "userid";
    private static final String DELETE_SUCCESS_MESSAGE_KEY = "message.admin.teacher-delete-success";
    private static final String DELETE_FAIL_MESSAGE_KEY = "message.admin.teacher-delete-fail";
    private static final String GET_PAGE_URL_PARAM = "/controller?command=showallteachers";

    public DeleteUserCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        String userIdLine = requestContent.getSingleRequestParameter(USER_ID_PARAM);
        int userId = Integer.parseInt(userIdLine);
        Map<String, Object> sessionAttributes = requestContent.getSessionAttributes();
        String locale =(String) sessionAttributes.get(SessionAttributes.LOCALE);
        UserService userReceiver = (UserService) getService();
        boolean isDeletedSuccessfully = false;
        try{
            isDeletedSuccessfully = userReceiver.deleteUserById(userId);
        } catch (ServiceException e){
            Logger.log(Level.FATAL, "Fail to execute delete user command.");
            throw new CommandException("Fail to execute delete user command.", e);
        }

        putMessageIntoSession(requestContent, isDeletedSuccessfully, DELETE_SUCCESS_MESSAGE_KEY, DELETE_FAIL_MESSAGE_KEY);

        return new ActionResult(GET_PAGE_URL_PARAM, NavigationType.REDIRECT);
    }
}
