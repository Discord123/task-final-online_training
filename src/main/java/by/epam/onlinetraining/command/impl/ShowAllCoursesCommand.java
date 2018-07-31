package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.util.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import by.epam.onlinetraining.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAllCoursesCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(ShowAllCoursesCommand.class);
    private static final String ALL_COURSES_PATH = PagePathManager.getProperty("path.page.allcourses");
    private static final String ALL_COURSES_PARAM = "allCourses";

    public ShowAllCoursesCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        try {
            CoursesService coursesService = (CoursesService) getService();
            List<CourseDto> courseList = coursesService.getAllCourses();
            requestContent.setSessionAttributes(ALL_COURSES_PARAM, courseList);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Exception during show all courses command");
            throw new CommandException("Exception during show all courses command", e);
        }

        return new ActionResult(ALL_COURSES_PATH, NavigationType.FORWARD);
    }
}
