package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.EntityAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.UserService;

import java.util.List;

public class ShowAllTeachersCommand extends ActionCommand {
    private static final String ALL_TEACHERS_PATH = ConfigurationManager.getProperty("path.page.allteachers");

    public ShowAllTeachersCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        try {
            UserService userReceiver = (UserService) getService();
            List<User> teachersList = userReceiver.showAllTeachers();
            requestContent.setSessionAttributes(EntityAttributes.ALL_TEACHERS_PARAM, teachersList);
        } catch (ServiceException e) {
            throw new CommandException("Exception during showing all teachers command", e);
        }

        return new RequestResult(ALL_TEACHERS_PATH, NavigationType.FORWARD);
    }
}
