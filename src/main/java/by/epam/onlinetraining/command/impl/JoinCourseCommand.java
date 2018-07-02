package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.Service;
import by.epam.onlinetraining.service.UserService;

import java.util.Map;

public class JoinCourseCommand extends AbstractCommand {
    private static final String COURSE_ID_PARAM = "course_id";
    private static final String TAKEN_COURSES_PATH = "/controller?command=showtakencourses";
    private static final String JOIN_SUCCESS_MESSAGE = "message.student.course-join-success";
    private static final String JOIN_FAIL_MESSAGE = "message.student.course-join-fail";

    public JoinCourseCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {

        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttributes.USER);
        int userId = user.getId();

        String courseIdLine = content.getSingleRequestParameter(COURSE_ID_PARAM);
        int courseId = Integer.parseInt(courseIdLine);

        UserService userReceiver = (UserService) getService();
        boolean isJoined = false;
        try {
            isJoined = userReceiver.joinCourse(courseId, userId);
        } catch (ServiceException e) {
            throw new CommandException("Fail to execute join course command.", e);
        }
        putMessageIntoSession(content, isJoined, JOIN_SUCCESS_MESSAGE, JOIN_FAIL_MESSAGE);

        return new RequestResult(TAKEN_COURSES_PATH, NavigationType.REDIRECT);
    }
}
