package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.Service;
import by.epam.onlinetraining.service.TasksService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTaskCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCommand.class);
    private static final String COURSE_ID = "course_id";
    private static final String TASK_NAME = "task_name";
    private static final String TASK_DESCRIPTION = "task_description";
    private static final String ADD_SUCCESS_MESSAGE = "message.teacher.task-add-success";
    private static final String ADD_FAIL_MESSAGE = "message.teacher.task-add-fail";
    private static final String ADD_TASK_PAGE = "/controller?command=showteacherrelatedcourses&expectedpage=addtask";


    public AddTaskCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean isAdded = false;

        String courseIdLine = content.getSingleRequestParameter(COURSE_ID);
        int courseId = Integer.parseInt(courseIdLine);
        String taskName = content.getSingleRequestParameter(TASK_NAME);
        String taskDescription = content.getSingleRequestParameter(TASK_DESCRIPTION);

        try{
            TasksService tasksService = (TasksService) getService();
            isAdded = tasksService.addTask(courseId, taskName, taskDescription);
        } catch (ServiceException e) {
            LOGGER.log(Level.FATAL,"Exception during processing add task command.", e);
            throw new CommandException("Exception during processing add task command.", e);
        }

        putMessageIntoSession(content, isAdded, ADD_SUCCESS_MESSAGE, ADD_FAIL_MESSAGE);
        return new RequestResult(ADD_TASK_PAGE, NavigationType.REDIRECT);
    }
}
