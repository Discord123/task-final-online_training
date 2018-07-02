package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.exceptions.DaoException;

import java.util.List;

public interface SubjectDao {
    List<Subject> showAllSubjects() throws DaoException;
}
