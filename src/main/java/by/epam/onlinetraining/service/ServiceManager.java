package by.epam.onlinetraining.service;

import by.epam.onlinetraining.service.impl.*;

public final class ServiceManager {

    private static final CourseService COURSES_SERVICE = new CourseServiceImpl();
    private static final ReviewService REVIEW_SERVICE = new ReviewServiceImpl();
    private static final SubjectService SUBJECT_SERVICE = new SubjectServiceImpl();
    private static final TasksService TASKS_SERVICE = new TasksServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    private ServiceManager() {
    }

    public static CourseService getCoursesService() {
        return COURSES_SERVICE;
    }

    public static ReviewService getReviewService() {
        return REVIEW_SERVICE;
    }

    public static SubjectService getSubjectService() {
        return SUBJECT_SERVICE;
    }

    public static TasksService getTasksService() {
        return TASKS_SERVICE;
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}
