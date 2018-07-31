package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.util.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.EntityAttributes;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAllTeachersCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(ShowAllTeachersCommand.class);
    private static final String ALL_TEACHERS_PATH = PagePathManager.getProperty("path.page.allteachers");

    public ShowAllTeachersCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        try {
            UserService userReceiver = (UserService) getService();
            List<User> teachersList = userReceiver.getAllTeachers();
            requestContent.setSessionAttributes(EntityAttributes.ALL_TEACHERS_PARAM, teachersList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Exception during showing all teachers command");
            throw new CommandException("Exception during showing all teachers command", e);
        }

        return new ActionResult(ALL_TEACHERS_PATH, NavigationType.FORWARD);
    }
}
