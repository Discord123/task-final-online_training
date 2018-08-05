package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.bundle.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.EntityAttributes;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.*;
import by.epam.onlinetraining.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class EditCourseCommand extends ActionCommand {
    private static final Logger Logger = LogManager.getLogger(EditCourseCommand.class);
    private static final String EDIT_COURSE_PAGE_PATH = PagePathManager.getProperty("path.page.editcourse");
    private static final String ALL_COURSES_PATH = "/controller?command=takeallcourses";
    private static final String UPDATE_SUCCESS_MESSAGE = "message.admin.course-update-success";
    private static final String UPDATE_FAIL_MESSAGE = "message.admin.course-update-fail";

    public EditCourseCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        ActionResult actionResult = null;

        Map<String, String[]> requestParameters = requestContent.getRequestParameters();
        String[] editedCourseName = requestParameters.get(EntityAttributes.COURSE_TITLE);
        if(editedCourseName == null){
            actionResult = putRequiredDataIntoSession(requestContent);
        } else {
            boolean isUpdated = false;
            try {
                String idLine = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_ID);
                int courseId = Integer.parseInt(idLine);
                String courseTitle = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_TITLE);
                String subjectIdLine = requestContent.getSingleRequestParameter(EntityAttributes.SUBJECT_ID);
                String status = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_STATUS);
                int subjectId = Integer.parseInt(subjectIdLine);
                String isAvailableLine = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_IS_AVAILABLE);
                int isAvailable = Integer.parseInt(isAvailableLine);
                String teacherIdLine = requestContent.getSingleRequestParameter(EntityAttributes.COURSE_TEACHER_ID);
                int teacherId = Integer.parseInt(teacherIdLine);

                CourseService courseService = (CourseService) getService();
                isUpdated = courseService.updateCourse(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
            } catch (ServiceException e) {
                Logger.log(Level.FATAL,"Fail to update course.");
                throw new CommandException("Fail to update course.", e);
            }

            putMessageIntoSession(requestContent, isUpdated, UPDATE_SUCCESS_MESSAGE, UPDATE_FAIL_MESSAGE);

            actionResult = new ActionResult(ALL_COURSES_PATH, NavigationType.REDIRECT);
        }

        return actionResult;
    }

    private ActionResult putRequiredDataIntoSession(RequestContent requestContent) throws CommandException {
        ActionResult actionResult;
        try {
            SubjectService subjectService = ServiceManager.getSubjectService();
            List<Subject> subjectList = subjectService.getAllSubjects();
            requestContent.setSessionAttributes(EntityAttributes.ALL_SUBJECTS_PARAM, subjectList);

            UserService userReceiver = new UserServiceImpl();
            List<User> teachersList = userReceiver.getAllTeachers();
            requestContent.setSessionAttributes(EntityAttributes.ALL_TEACHERS_PARAM, teachersList);

            actionResult = new ActionResult(EDIT_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Fail to put required lists in the session");
            throw new CommandException("Fail to put required lists in the session", e);
        }
        return actionResult;
    }
}
