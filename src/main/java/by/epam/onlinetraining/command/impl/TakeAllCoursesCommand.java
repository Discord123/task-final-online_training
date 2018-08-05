package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.bundle.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CourseService;
import by.epam.onlinetraining.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeAllCoursesCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(TakeAllCoursesCommand.class);
    private static final String ALL_COURSES_PATH = PagePathManager.getProperty("path.page.allcourses");
    private static final String ALL_COURSES_PARAM = "allCourses";

    public TakeAllCoursesCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        try {
            CourseService courseService = (CourseService) getService();
            List<CourseDto> courseList = courseService.getAllCourses();
            requestContent.setSessionAttributes(ALL_COURSES_PARAM, courseList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Exception during show all courses command");
            throw new CommandException("Exception during show all courses command", e);
        }

        return new ActionResult(ALL_COURSES_PATH, NavigationType.FORWARD);
    }
}
