package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.bundle.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.EntityAttribute;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddCourseCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(ActionCommand.class);
    private static final String ADD_COURSE_PAGE_PATH = PagePathManager.getProperty("path.page.addcourse");
    private static final String ADD_SUCCESS_MESSAGE_KEY = "message.admin.course-add-success";
    private static final String ADD_FAIL_MESSAGE_KEY = "message.admin.course-add-fail";
    private static final String ADD_COURSE_PARAM = "addCourse";
    private static final String GET_PAGE_URL_PARAM = "/controller?command=addcourse";

    public AddCourseCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        ActionResult actionResult = null;

        Map<String, String[]> requestParameters = requestContent.getRequestParameters();
        String[] addCourseParam = requestParameters.get(ADD_COURSE_PARAM);
        if(addCourseParam == null){
            actionResult = putRequiredDataIntoSession(requestContent);
        } else {
            boolean isAdded = processCourseAdding(requestContent);
            putMessageIntoSession(requestContent, isAdded, ADD_SUCCESS_MESSAGE_KEY, ADD_FAIL_MESSAGE_KEY);

            actionResult = new ActionResult(GET_PAGE_URL_PARAM, NavigationType.REDIRECT);
        }

        return actionResult;
    }

    private boolean processCourseAdding(RequestContent requestContent) throws CommandException {
        boolean isAdded;
        try {
            String courseTitle = requestContent.getSingleRequestParameter(EntityAttribute.COURSE_TITLE);
            String subjectIdLine = requestContent.getSingleRequestParameter(EntityAttribute.SUBJECT_ID);
            int subjectId = Integer.parseInt(subjectIdLine);
            String status = requestContent.getSingleRequestParameter(EntityAttribute.COURSE_STATUS);
            String isAvailableLine = requestContent.getSingleRequestParameter(EntityAttribute.COURSE_IS_AVAILABLE);
            int isAvailable = Integer.parseInt(isAvailableLine);

            String teacherIdLine = requestContent.getSingleRequestParameter(EntityAttribute.COURSE_TEACHER_ID);
            Integer teacherId = Integer.parseInt(teacherIdLine);

            CourseService courseService = (CourseService) getService();
            isAdded = courseService.addCourse(courseTitle, subjectId, status, isAvailable, teacherId);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Fail to add new course.", e);
            throw new CommandException("Fail to add new course.", e);
        }
        return isAdded;
    }

    private ActionResult putRequiredDataIntoSession(RequestContent requestContent) throws CommandException {
        ActionResult actionResult;
        try {
            SubjectService subjectService = ServiceManager.getSubjectService();
            List<Subject> subjectList = subjectService.getAllSubjects();
            requestContent.setSessionAttributes(EntityAttribute.ALL_SUBJECTS_PARAM, subjectList);

            UserService userService = ServiceManager.getUserService();
            List<User> teachersList = userService.getAllTeachers();
            requestContent.setSessionAttributes(EntityAttribute.ALL_TEACHERS_PARAM, teachersList);

            actionResult = new ActionResult(ADD_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Fail to put required lists in the session", e);
            throw new CommandException("Fail to put required lists in the session", e);
        }
        return actionResult;
    }
}
