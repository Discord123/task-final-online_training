package by.epam.onlinetraining.service;

import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.exceptions.ServiceException;

import java.util.List;

public interface SubjectService extends Service {
    List<Subject> showAllSubjects() throws ServiceException;
}
