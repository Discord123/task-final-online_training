package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.util.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.Task;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.TasksService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeCourseRelatedTasksCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(TakeCourseRelatedTasksCommand.class);
    private static final String TASKS_PAGE_PATH = PagePathManager.getProperty("path.page.relatedtasks");
    private static final String COURSE_ID_PARAM = "course_id";
    private static final String RELATED_TASKS_ATTR = "relatedTasks";

    public TakeCourseRelatedTasksCommand() {
        super(ServiceManager.getTasksService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String courseIdLine =  content.getSingleRequestParameter(COURSE_ID_PARAM);
        Integer courseId = Integer.parseInt(courseIdLine);

        TasksService tasksService = (TasksService) getService();
        try{
            List<Task> taskList = tasksService.getCourseRelatedTasks(courseId);
            content.setSessionAttributes(RELATED_TASKS_ATTR, taskList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Fail to show course related tasks.");
            throw new CommandException("Fail to show course related tasks.", e);
        }

        return new ActionResult(TASKS_PAGE_PATH, NavigationType.FORWARD);
    }
}
