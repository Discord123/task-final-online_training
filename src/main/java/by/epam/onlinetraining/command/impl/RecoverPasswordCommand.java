package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.MessageManager;
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

public class RecoverPasswordCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(RecoverPasswordCommand.class);
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";
    private static final String RECOVERY_PAGE_PATH = "/controller?command=getPage&expectedPage=recovery";


    private static final String MESSAGE_MAIL_SUBJECT = "mail.message.subject";
    private static final String MESSAGE_MAIL_TEXT = "mail.message.text";

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_CHANGE_SUCCESS_MESSAGE = "message.password.change-success";
    private static final String PASSWORD_CHANGE_FAIL_MESSAGE = "message.password.change-fail";

    public RecoverPasswordCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isSent = false;
        String email = content.getSingleRequestParameter(EMAIL_PARAMETER);
        try{
            UserService userService = (UserService) getService();
            Map<String, Object> sessionAttributes = content.getSessionAttributes();
            String locale = (String) sessionAttributes.get(SessionAttributes.LOCALE);
            MessageManager messageManager = MessageManager.getManager(locale);
            String subject = messageManager.getMessage(MESSAGE_MAIL_SUBJECT);
            String text = messageManager.getMessage(MESSAGE_MAIL_TEXT);
            isSent = userService.recoverPassword(email, subject, text);
        } catch (ServiceException e){
            Logger.log(Level.FATAL, "Fail to recover password for " + email + ".");
            throw new CommandException("Fail to recover password for " + email + ".", e);
        }

        putMessageIntoSession(content, isSent, PASSWORD_CHANGE_SUCCESS_MESSAGE, PASSWORD_CHANGE_FAIL_MESSAGE);

        String targetPagePath = null;
        if(isSent){
            targetPagePath = LOGIN_PAGE_PATH;
        } else{
            targetPagePath = RECOVERY_PAGE_PATH;
        }

        return new ActionResult(targetPagePath, NavigationType.REDIRECT);
    }
}
