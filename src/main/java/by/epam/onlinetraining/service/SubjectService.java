package by.epam.onlinetraining.service;

import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.exception.ServiceException;

import java.util.List;

public interface SubjectService extends Service {
    List<Subject> getAllSubjects() throws ServiceException;
}
