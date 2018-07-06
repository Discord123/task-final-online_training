package by.epam.onlinetraining.service;


import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.entity.User;

import java.util.List;

public interface UserService extends Service {
    User login(String emailInput, String passwordInput) throws ServiceException;
    boolean isEmailTaken(String emailInput) throws ServiceException;
    boolean singUp(String userEmail, String userPassword, String firstName, String lastName, String role) throws ServiceException;
    boolean joinCourse(int courseId, int userId ) throws ServiceException;
    List<User> showAllTeachers() throws ServiceException;
    boolean deleteUserById(int userId) throws ServiceException;
    boolean recoverPassword(String email, String subject, String text) throws ServiceException;
}
