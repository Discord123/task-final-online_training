package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.Service;
import by.epam.onlinetraining.service.UserService;

import java.util.Map;

public class DeleteUserCommand extends AbstractCommand {
    private static final String USER_ID_PARAM = "userid";
    private static final String DELETE_SUCCESS_MESSAGE_KEY = "message.admin.teacher-delete-success";
    private static final String DELETE_FAIL_MESSAGE_KEY = "message.admin.teacher-delete-fail";
    private static final String GET_PAGE_URL_PARAM = "/controller?command=showallteachers";

    public DeleteUserCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        String userIdLine = requestContent.getSingleRequestParameter(USER_ID_PARAM);
        int userId = Integer.parseInt(userIdLine);
        Map<String, Object> sessionAttributes = requestContent.getSessionAttributes();
        String locale =(String) sessionAttributes.get(SessionAttributes.LOCALE);
        UserService userReceiver = (UserService) getService();
        boolean isDeletedSuccessfully = false;
        try{
            isDeletedSuccessfully = userReceiver.deleteUserById(userId);
        } catch (ServiceException e){
            throw new CommandException("Fail to execute delete user command.", e);
        }

        putMessageIntoSession(requestContent, isDeletedSuccessfully, DELETE_SUCCESS_MESSAGE_KEY, DELETE_FAIL_MESSAGE_KEY);

        return new RequestResult(GET_PAGE_URL_PARAM, NavigationType.REDIRECT);
    }
}
