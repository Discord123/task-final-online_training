package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.util.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import by.epam.onlinetraining.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class TakeTeacherRelatedCoursesCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(TakeTeacherRelatedCoursesCommand.class);
    private static final String RELATED_COURSES_ATTRIBUTE = "relatedCourses";

    private static final String RELATED_COURSES = "relatedcourses";
    private static final String RELATED_TASKS = "relatedtasks";
    private static final String ADD_TASK = "addtask";
    private static final String EXPECTED_PAGE_PARAMETER = "expectedpage";

    private static final String RELATED_COURSES_PAGE = PagePathManager.getProperty("path.page.relatedcourses");
    private static final String RELATED_TASKS_PAGE = PagePathManager.getProperty("path.page.relatedtasks");
    private static final String ADD_TASK_PAGE = PagePathManager.getProperty("path.page.addtask");

    public TakeTeacherRelatedCoursesCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int teacherId = user.getId();
        CoursesService coursesService = (CoursesService) getService();
        try {
            List<CourseDto> courseDtoList = coursesService.getRelatedCourses(teacherId);
            content.setSessionAttributes(RELATED_COURSES_ATTRIBUTE, courseDtoList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL, "Fail to show all courses related to the teacher.");
            throw new CommandException("Fail to show all courses related to the teacher.", e);
        }

        String page = parseExpectedPage(content);

        return new ActionResult(page, NavigationType.FORWARD);
    }

    private String parseExpectedPage(RequestContent content) {
        String expectedPath = content.getSingleRequestParameter(EXPECTED_PAGE_PARAMETER);
        String page = null;
        if (expectedPath != null){
            switch (expectedPath){
                case RELATED_COURSES:
                    page = RELATED_COURSES_PAGE;
                    break;
                case RELATED_TASKS:
                    page = RELATED_TASKS_PAGE;
                    break;
                case ADD_TASK:
                    page = ADD_TASK_PAGE;
                    break;
            }
        }
        return page;
    }
}
