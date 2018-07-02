package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import by.epam.onlinetraining.service.Service;

import java.util.List;

public class ShowAllCoursesCommand extends AbstractCommand {
    private static final String ALL_COURSES_PATH = ConfigurationManager.getProperty("path.page.allcourses");
    private static final String ALL_COURSES_PARAM = "allCourses";

    public ShowAllCoursesCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        try {
            CoursesService coursesService = (CoursesService) getService();
            List<CourseDto> courseList = coursesService.showAllCourses();
            requestContent.setSessionAttributes(ALL_COURSES_PARAM, courseList);
        } catch (ServiceException e) {
            throw new CommandException("Exception during show all courses command", e);
        }

        return new RequestResult(ALL_COURSES_PATH, NavigationType.FORWARD);
    }
}
