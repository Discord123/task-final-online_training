package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.TasksService;

import java.util.List;

public class ShowCourseRelatedTasksCommand extends ActionCommand {

    private static final String TASKS_PAGE_PATH = ConfigurationManager.getProperty("path.page.relatedtasks");
    private static final String COURSE_ID_PARAM = "course_id";
    private static final String RELATED_TASKS_ATTR = "relatedTasks";

    public ShowCourseRelatedTasksCommand() {
        super(ServiceManager.getTasksService());
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String courseIdLine =  content.getSingleRequestParameter(COURSE_ID_PARAM);
        Integer courseId = Integer.parseInt(courseIdLine);

        TasksService tasksService = (TasksService) getService();
        try{
            List<Task> taskList = tasksService.showCourseRelatedTasks(courseId);
            content.setSessionAttributes(RELATED_TASKS_ATTR, taskList);
        } catch (ServiceException e) {
            throw new CommandException("Fail to show course related tasks.", e);
        }

        return new RequestResult(TASKS_PAGE_PATH, NavigationType.FORWARD);
    }
}
