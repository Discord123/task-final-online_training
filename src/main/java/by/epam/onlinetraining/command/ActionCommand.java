package by.epam.onlinetraining.command;

import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.service.Service;

public abstract class ActionCommand {
    private static final String MESSAGE_SUCCESS_ATTRIBUTE = "actionSuccessful";
    private static final String MESSAGE_FAIL_ATTRIBUTE = "actionFail";
    private Service service;

    public ActionCommand() {
    }

    public ActionCommand(Service service){
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public abstract ActionResult execute(RequestContent requestContent) throws CommandException;

    protected void putMessageIntoSession(RequestContent requestContent, boolean isAdded, String successMessageKey, String failMessageKey) {
        if (isAdded){
            requestContent.setSessionAttributes(MESSAGE_SUCCESS_ATTRIBUTE, successMessageKey);
        } else {
            requestContent.setSessionAttributes(MESSAGE_FAIL_ATTRIBUTE, failMessageKey);
        }
    }
}