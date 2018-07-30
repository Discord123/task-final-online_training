package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.impl.*;

public final class DAOManager {

    private DAOManager() {
    }

    public static CoursesDaoImpl getCoursesDao() {
        return new CoursesDaoImpl();
    }

    public static ReviewDaoImpl getReviewDao() {
        return new ReviewDaoImpl();
    }

    public static SubjectDaoImpl getSubjectDao() {
        return new SubjectDaoImpl();
    }

    public static TasksDaoImpl getTasksDao() {
        return new TasksDaoImpl();
    }

    public static UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }
}
