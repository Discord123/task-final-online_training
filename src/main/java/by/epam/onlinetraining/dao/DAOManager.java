package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.impl.*;

public final class DAOManager {

    private static final CoursesDao COURSES_DAO = new CoursesDaoImpl();
    private static final ReviewDao REVIEW_DAO = new ReviewDaoImpl();
    private static final SubjectDao SUBJECT_DAO = new SubjectDaoImpl();
    private static final TasksDao TASKS_DAO = new TasksDaoImpl();
    private static final UserDao USER_DAO = new UserDaoImpl();

    private DAOManager() {
    }

    public static CoursesDao getCoursesDao() {
        return COURSES_DAO;
    }

    public static ReviewDao getReviewDao() {
        return REVIEW_DAO;
    }

    public static SubjectDao getSubjectDao() {
        return SUBJECT_DAO;
    }

    public static TasksDao getTasksDao() {
        return TASKS_DAO;
    }

    public static UserDao getUserDao() {
        return USER_DAO;
    }
}
