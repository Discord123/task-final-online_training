package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SignUpAttribute;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.UserService;
import by.epam.onlinetraining.service.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StudentSignUpCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(StudentSignUpCommand.class);
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";
    private static final String REGISTRATION_PAGE_PATH = "/controller?command=getPage&expectedPage=registration";

    private static final String MESSAGE_EMAIL_INVALID = "message.email.error-invalid";
    private static final String MESSAGE_EMAIL_TAKEN = "message.email.error-taken";
    private static final String MESSAGE_LAST_NAME_INVALID = "message.lastName.error";
    private static final String MESSAGE_FIRST_NAME_INVALID = "message.firstName.error";
    private static final String MESSAGE_PASSWORD_INVALID = "message.password.error";
    private static final String MESSAGE_PASSWORD_REPEAT_INVALID = "message.password.repeat.error";
    private static final String MESSAGE_SIGN_UP_SUCCESS = "message.sign-up-success";
    private static final String MESSAGE_SIGN_UP_FAIL = "message.sing-up-error";
    private static final String USER_ROLE = "student";

    private String email;
    private String password;
    private String checkPassword;
    private String firstName;
    private String lastName;
    private String signUpFailMessage = MESSAGE_SIGN_UP_FAIL;

    public StudentSignUpCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {

        email = content.getSingleRequestParameter(SignUpAttribute.EMAIL_PARAM);
        password = content.getSingleRequestParameter(SignUpAttribute.PASSWORD_PARAM);
        checkPassword = content.getSingleRequestParameter(SignUpAttribute.CHECK_PASSWORD_PARAM);
        firstName = content.getSingleRequestParameter(SignUpAttribute.FIRST_NAME_PARAM);
        lastName = content.getSingleRequestParameter(SignUpAttribute.LAST_NAME_PARAM);

        boolean isRegistered = false;
        UserService userService = (UserService) getService();

        try {
            boolean isParametersValid = isParametersValid(userService);
            if (isParametersValid) {
                isRegistered = userService.singUp(email, password, firstName, lastName, USER_ROLE);
            }
            content.setSessionAttributes("failEmail", email);
            content.setSessionAttributes("failFirstName", firstName);
            content.setSessionAttributes("failLastName", lastName);

            putMessageIntoSession(content, isRegistered, MESSAGE_SIGN_UP_SUCCESS, signUpFailMessage);

        } catch (ServiceException e) {
            Logger.log(Level.FATAL, "Problem during user sign up");
            throw new CommandException("Problem during user sign up", e);
        }

        String targetPagePath = null;
        if (isRegistered) {
            targetPagePath = LOGIN_PAGE_PATH;
        } else {
            targetPagePath = REGISTRATION_PAGE_PATH;
        }

        return new ActionResult(targetPagePath, NavigationType.REDIRECT);
    }

    private boolean isParametersValid(UserService userService) throws ServiceException {
        boolean isValid = false;
        if (!Validator.isEmailValid(email)) {
            signUpFailMessage = MESSAGE_EMAIL_INVALID;

        } else if (!Validator.isLastNameValid(lastName)) {
            signUpFailMessage = MESSAGE_LAST_NAME_INVALID;

        } else if (!Validator.isFirstNameValid(firstName)) {
            signUpFailMessage = MESSAGE_FIRST_NAME_INVALID;

        } else if (!Validator.isPasswordValid(password)) {
            signUpFailMessage = MESSAGE_PASSWORD_INVALID;

        } else if (!Validator.isPasswordRepeatValid(password, checkPassword)) {
            signUpFailMessage = MESSAGE_PASSWORD_REPEAT_INVALID;

        } else if (userService.isEmailTaken(email)) {
            signUpFailMessage = MESSAGE_EMAIL_TAKEN;

        } else {
            isValid = true;
        }
        return isValid;
    }
}
