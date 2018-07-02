package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.dto.CourseDto;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import by.epam.onlinetraining.service.Service;

import java.util.List;
import java.util.Map;

public class ShowAvailableCoursesCommand extends AbstractCommand {

    private static final String AVAILABLE_COURSES_PATH = ConfigurationManager.getProperty("path.page.available-courses");
    private static final String AVAILABLE_COURSES_PARAM = "availableCourses";

    public ShowAvailableCoursesCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int userId = user.getId();
        try {
            CoursesService coursesService = (CoursesService) getService();
            List<CourseDto> courseDtoList = coursesService.showAvailableCourses(userId);
            content.setRequestAttributes(AVAILABLE_COURSES_PARAM, courseDtoList);
        } catch (ServiceException e) {
            throw new CommandException("Exception during show available courses command", e);
        }

        return new RequestResult(AVAILABLE_COURSES_PATH, NavigationType.FORWARD);
    }
}
