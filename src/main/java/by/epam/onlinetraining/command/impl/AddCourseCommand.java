package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.constants.EntityAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.CoursesService;
import by.epam.onlinetraining.service.Service;
import by.epam.onlinetraining.service.SubjectService;
import by.epam.onlinetraining.service.UserService;
import by.epam.onlinetraining.service.impl.SubjectServiceImpl;
import by.epam.onlinetraining.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddCourseCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCommand.class);
    private static final String ADD_COURSE_PAGE_PATH = ConfigurationManager.getProperty("path.page.addcourse");
    private static final String ADD_SUCCESS_MESSAGE_KEY = "message.admin.course-add-success";
    private static final String ADD_FAIL_MESSAGE_KEY = "message.admin.course-add-fail";
    private static final String ADD_COURSE_PARAM = "addCourse";
    private static final String GET_PAGE_URL_PARAM = "/controller?command=addcourse";

    public AddCourseCommand(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        RequestResult requestResult = null;

        Map<String, String[]> requestParameters = requestContent.getRequestParameters();
        String[] addCourseParam = requestParameters.get(ADD_COURSE_PARAM);
        if(addCourseParam == null){
            requestResult = putRequiredDataIntoSession(requestContent);
        } else {
            boolean isAdded = processCourseAdding(requestContent);
            putMessageIntoSession(requestContent, isAdded, ADD_SUCCESS_MESSAGE_KEY, ADD_FAIL_MESSAGE_KEY);

            requestResult = new RequestResult(GET_PAGE_URL_PARAM, NavigationType.REDIRECT);
        }

        return requestResult;
    }

    private boolean processCourseAdding(RequestContent requestContent) throws CommandException {
        boolean isAdded;
        try {
            String courseTitle = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_TITLE);
            String subjectIdLine = requestContent.getSingleRequestParameter(EntityAttributes.SUBJECT_ID);
            int subjectId = Integer.parseInt(subjectIdLine);
            String status = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_STATUS);
            String isAvailableLine = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_IS_AVAILABLE);
            int isAvailable = Integer.parseInt(isAvailableLine);

            String teacherIdLine = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_TEACHER_ID);
            Integer teacherId = Integer.parseInt(teacherIdLine);

            CoursesService coursesService = (CoursesService) getService();
            isAdded = coursesService.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (ServiceException e) {
            LOGGER.log(Level.FATAL,"Fail to add new course.", e);
            throw new CommandException("Fail to add new course.", e);
        }
        return isAdded;
    }

    private RequestResult putRequiredDataIntoSession(RequestContent requestContent) throws CommandException {
        RequestResult requestResult;
        try {
            SubjectService subjectService = new SubjectServiceImpl();
            List<Subject> subjectList = subjectService.showAllSubjects();
            requestContent.setSessionAttributes(EntityAttributes.ALL_SUBJECTS_PARAM, subjectList);

            UserService userService = new UserServiceImpl();
            List<User> teachersList = userService.showAllTeachers();
            requestContent.setSessionAttributes(EntityAttributes.ALL_TEACHERS_PARAM, teachersList);

            requestResult = new RequestResult(ADD_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            LOGGER.log(Level.FATAL,"Fail to put required lists in the session", e);
            throw new CommandException("Fail to put required lists in the session", e);
        }
        return requestResult;
    }
}
