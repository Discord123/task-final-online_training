package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.service.ServiceManager;

public class LogoutCommand extends ActionCommand {
    private static final String INVALIDATE_SESSION_MARKER = "invalidate";
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";

    public LogoutCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        requestContent.cleanSession();
        requestContent.setSessionAttributes(INVALIDATE_SESSION_MARKER, Boolean.TRUE);

        return new ActionResult(LOGIN_PAGE_PATH, NavigationType.REDIRECT);
    }
}
