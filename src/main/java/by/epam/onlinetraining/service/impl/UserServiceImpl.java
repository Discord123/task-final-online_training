package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.DAOManager;
import by.epam.onlinetraining.dao.UserDao;
import by.epam.onlinetraining.dto.StatisticDTO;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.UserService;
import by.epam.onlinetraining.service.util.MailSender;
import by.epam.onlinetraining.service.util.PasswordCreator;
import by.epam.onlinetraining.service.util.PasswordHasher;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger Logger = LogManager.getLogger(UserService.class);
    private static UserDao userDAO = DAOManager.getUserDao();

    @Override
    public User login(String emailInput, String passwordInput) throws ServiceException {
        User user = null;

        String passwordHash = PasswordHasher.shaHashing(passwordInput);

        try{
            if(userDAO.checkUserByEmail(emailInput)){
                user = userDAO.findUserByEmailAndPassword(emailInput, passwordHash);
            }
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process login.", e);
            throw new ServiceException("Fail to process login.", e);
        }

        return user;
    }

    @Override
    public StatisticDTO getStatistic() throws ServiceException {
        StatisticDTO statisticDTO = null;

        try{
            statisticDTO = userDAO.getStatistic();
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process create statistic.", e);
            throw new ServiceException("Fail to process create statistic.", e);
        }

        return statisticDTO;
    }

    @Override
    public boolean deleteUserById(int userId) throws ServiceException {
        boolean isDeletedSuccessfully = false;

        try{
            isDeletedSuccessfully = userDAO.deleteUserById(userId);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to process delete user by id in service.", e);
            throw new ServiceException("Fail to process delete user by id in service.", e);
        }

        return isDeletedSuccessfully;
    }

    @Override
    public List<User> getAllTeachers() throws ServiceException {
        List<User> teachersList = null;

        try{
            teachersList = userDAO.findAllTeachers();
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Fail to getInstance all teachers from dao", e);
            throw new ServiceException("Fail to getInstance all teachers from dao", e);
        }

        return teachersList;
    }

    @Override
    public boolean isEmailTaken(String emailInput) throws ServiceException {
        boolean isTaken = false;

        try{
            isTaken = userDAO.checkUserByEmail(emailInput);
        } catch (DaoException e){
            Logger.log(Level.FATAL, "Fail to check email availability.", e);
            throw new ServiceException("Fail to check email availability.", e);
        }

        return isTaken;
    }

    @Override
    public boolean singUp(String userEmail, String userPassword, String firstName, String lastName, String role) throws ServiceException {
        boolean isRegistered = false;
        String hashedPassword = PasswordHasher.shaHashing(userPassword);

        try {
            isRegistered = userDAO.addUser(userEmail, hashedPassword, firstName, lastName, role);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Fail to create new user during sign up.", e);
            throw new ServiceException("Fail to create new user during sign up.", e);
        }

        return isRegistered;
    }

    @Override
    public boolean joinCourse(int courseId, int userId) throws ServiceException {
        Boolean isJoined = false;

        try {
            isJoined = userDAO.joinCourse(courseId, userId);
        } catch (DaoException e) {
            Logger.log(Level.FATAL, "Fail to join the course.", e);
            throw new ServiceException("Fail to join the course.", e);
        }

        return isJoined;
    }

    @Override
    public boolean recoverPassword(String userEmail, String subject, String text) throws ServiceException {
        boolean isSent = false;
        String password = PasswordCreator.createPassword();

        try{
            boolean isUserExists = userDAO.checkUserByEmail(userEmail);
            if(isUserExists) {
                String hashedPassword = PasswordHasher.shaHashing(password);
                userDAO.updateUserPassword(userEmail, hashedPassword);

                MailSender mailSender = new MailSender();
                String completeMessage = text + password;
                mailSender.send(subject, completeMessage, userEmail);

                isSent = true;
            }
        } catch (DaoException | MessagingException e){
            Logger.log(Level.FATAL, "Fail to update user password.", e);
            throw new ServiceException("Fail to update user password.", e);
        }

        return isSent;
    }
}