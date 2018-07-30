package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.impl.*;

public class DAOManager {

    public static final CoursesDaoImpl COURSES_DAO = new CoursesDaoImpl();
    public static final ReviewDaoImpl REVIEW_DAO = new ReviewDaoImpl();
    public static final SubjectDaoImpl SUBJECT_DAO = new SubjectDaoImpl();
    public static final TasksDaoImpl TASKS_DAO = new TasksDaoImpl();
    public static final UserDaoImpl USER_DAO = new UserDaoImpl();

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
