package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.EntityAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.*;
import by.epam.onlinetraining.service.impl.SubjectServiceImpl;
import by.epam.onlinetraining.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Map;

public class EditCourseCommand extends ActionCommand {
    private static final String EDIT_COURSE_PAGE_PATH = ConfigurationManager.getProperty("path.page.editcourse");
    private static final String ALL_COURSES_PATH = "/controller?command=showallcourses";
    private static final String UPDATE_SUCCESS_MESSAGE = "message.admin.course-update-success";
    private static final String UPDATE_FAIL_MESSAGE = "message.admin.course-update-fail";

    public EditCourseCommand() {
        super(ServiceManager.getCoursesService());
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        RequestResult requestResult = null;

        Map<String, String[]> requestParameters = requestContent.getRequestParameters();
        String[] editedCourseName = requestParameters.get(EntityAttributes.COURSE_TITLE);
        if(editedCourseName == null){
            requestResult = putRequiredDataIntoSession(requestContent);
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

                CoursesService coursesService = (CoursesService) getService();
                isUpdated = coursesService.updateCourse(courseId, courseTitle, subjectId, status, isAvailable, teacherId);
            } catch (ServiceException e) {
                throw new CommandException("Fail to update course.", e);
            }

            putMessageIntoSession(requestContent, isUpdated, UPDATE_SUCCESS_MESSAGE, UPDATE_FAIL_MESSAGE);

            requestResult = new RequestResult(ALL_COURSES_PATH, NavigationType.REDIRECT);
        }

        return requestResult;
    }

    private RequestResult putRequiredDataIntoSession(RequestContent requestContent) throws CommandException {
        RequestResult requestResult;
        try {
            SubjectService subjectService = new SubjectServiceImpl();
            List<Subject> subjectList = subjectService.showAllSubjects();
            requestContent.setSessionAttributes(EntityAttributes.ALL_SUBJECTS_PARAM, subjectList);

            UserService userReceiver = new UserServiceImpl();
            List<User> teachersList = userReceiver.showAllTeachers();
            requestContent.setSessionAttributes(EntityAttributes.ALL_TEACHERS_PARAM, teachersList);

            requestResult = new RequestResult(EDIT_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            throw new CommandException("Fail to put required lists in the session", e);
        }
        return requestResult;
    }
}
