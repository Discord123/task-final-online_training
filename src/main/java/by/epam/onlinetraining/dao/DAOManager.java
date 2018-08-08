package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.impl.*;

public final class DAOManager {

    private static final CoursesDaoImpl COURSES_DAO = new CoursesDaoImpl();
    private static final ReviewDaoImpl REVIEW_DAO = new ReviewDaoImpl();
    private static final SubjectDaoImpl SUBJECT_DAO = new SubjectDaoImpl();
    private static final TasksDaoImpl TASKS_DAO = new TasksDaoImpl();
    private static final UserDaoImpl USER_DAO = new UserDaoImpl();

    private DAOManager() {
    }

    public static CoursesDaoImpl getCoursesDao() {
        return COURSES_DAO;
    }

    public static ReviewDaoImpl getReviewDao() {
        return REVIEW_DAO;
    }

    public static SubjectDaoImpl getSubjectDao() {
        return SUBJECT_DAO;
    }

    public static TasksDaoImpl getTasksDao() {
        return TASKS_DAO;
    }

    public static UserDaoImpl getUserDao() {
        return USER_DAO;
    }
}
