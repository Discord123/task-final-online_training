package by.epam.onlinetraining.command.impl;


import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.SessionAttributes;
import by.epam.onlinetraining.constants.SignUpAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.entity.enums.Role;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.UserService;

import java.util.Map;

public class LoginCommand extends AbstractCommand {
    private static final String AUTHORIZATION_ATTRIBUTE = "authorization";
    private static final String FAIL_ATTRIBUTE = "actionFail";
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";
    private static final String LOGIN_PAGE = "/controller?command=getPage&expectedPage=login";
    private static final String ADMIN_PAGE = "/controller?command=getPage&expectedPage=adminpage";
    private static final String TEACHER_PAGE = "/controller?command=getPage&expectedPage=teacherpage";
    private static final String STUDENT_PAGE = "/controller?command=getPage&expectedPage=studentpage";

    public LoginCommand(UserService receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        User user = null;
        try {
            UserService userService = (UserService) getService();
            String emailInput = content.getSingleRequestParameter(SignUpAttributes.EMAIL_PARAM);
            String passwordInput = content.getSingleRequestParameter(SignUpAttributes.PASSWORD_PARAM);
            user = userService.login(emailInput, passwordInput);
        } catch (ServiceException e) {
            throw new CommandException("Exception during login command", e);
        }

        RequestResult requestResult = null;
        Map<String, Object> requestAttributes = content.getRequestAttributes();
        if(user != null){
            requestAttributes.put(AUTHORIZATION_ATTRIBUTE, Boolean.TRUE);
            content.setSessionAttributes(SessionAttributes.USER, user);
            Role userRole = user.getRole();
            if(userRole == Role.ADMIN){
                requestResult = new RequestResult(ADMIN_PAGE, NavigationType.REDIRECT);
            } else if (userRole == Role.TEACHER){
                requestResult = new RequestResult(TEACHER_PAGE, NavigationType.REDIRECT);
            } else {
                requestResult = new RequestResult(STUDENT_PAGE, NavigationType.REDIRECT);
            }
        } else {
            requestAttributes.put(AUTHORIZATION_ATTRIBUTE, Boolean.FALSE);
            content.setSessionAttributes(FAIL_ATTRIBUTE, MESSAGE_LOGIN_ERROR);
            requestResult = new RequestResult(LOGIN_PAGE, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
