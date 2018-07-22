package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.TasksService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class TakeReceivedTasksCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(TakeReceivedTasksCommand.class);
    private static final String RECEIVED_TASKS_PATH = ConfigurationManager.getProperty("path.page.received-tasks");
    private static final String RECEIVED_TASKS_PARAM = "receivedTasks";

    public TakeReceivedTasksCommand() {
        super(ServiceManager.getTasksService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int userId = user.getId();
        try {
            TasksService tasksReceiver = (TasksService) getService();
            List<TaskDto> taskDtoList = tasksReceiver.getReceivedTasks(userId);
            content.setRequestAttributes(RECEIVED_TASKS_PARAM, taskDtoList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL, "Exception during show taken courses command");
            throw new CommandException("Exception during show taken courses command", e);
        }

        return new ActionResult(RECEIVED_TASKS_PATH, NavigationType.FORWARD);
    }
}
