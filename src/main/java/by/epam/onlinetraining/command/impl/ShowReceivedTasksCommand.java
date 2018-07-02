package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.dto.TaskDto;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.Service;
import by.epam.onlinetraining.service.TasksService;

import java.util.List;
import java.util.Map;

public class ShowReceivedTasksCommand extends AbstractCommand {

    private static final String RECEIVED_TASKS_PATH = ConfigurationManager.getProperty("path.page.received-tasks");
    private static final String RECEIVED_TASKS_PARAM = "receivedTasks";

    public ShowReceivedTasksCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int userId = user.getId();
        try {
            TasksService tasksReceiver = (TasksService) getService();
            List<TaskDto> taskDtoList = tasksReceiver.showReceivedTasks(userId);
            content.setRequestAttributes(RECEIVED_TASKS_PARAM, taskDtoList);
        } catch (ServiceException e) {
            throw new CommandException("Exception during show taken courses command", e);
        }

        return new RequestResult(RECEIVED_TASKS_PATH, NavigationType.FORWARD);
    }
}
