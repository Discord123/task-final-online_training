package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.service.Service;

public class LogoutCommand extends AbstractCommand {
    private static final String INVALIDE_SESSION_MARKER = "invalidate";
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";

    public LogoutCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        requestContent.cleanSession();
        requestContent.setSessionAttributes(INVALIDE_SESSION_MARKER, Boolean.TRUE);

        return new RequestResult(LOGIN_PAGE_PATH, NavigationType.REDIRECT);
    }
}
