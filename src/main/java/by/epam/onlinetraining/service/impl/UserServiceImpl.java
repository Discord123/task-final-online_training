package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.UserDaoImpl;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.UserService;
import by.epam.onlinetraining.util.MailSender;
import by.epam.onlinetraining.util.PasswordCreator;
import by.epam.onlinetraining.util.PasswordHasher;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger Logger = LogManager.getLogger(UserService.class);

    @Override
    public User login(String emailInput, String passwordInput) throws ServiceException {
        User user = null;

        String passwordHash = PasswordHasher.shaHashing(passwordInput);

        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(userDao);
            if(userDao.checkUserByEmail(emailInput)){
                user = userDao.findUserByEmailAndPassword(emailInput, passwordHash);
            }
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to process login.", e);
            throw new ServiceException("Fail to process login.", e);
        } finally {
            helper.endTransaction();
        }
        return user;
    }

    @Override
    public boolean deleteUserById(int userId) throws ServiceException {
        boolean isDeletedSuccessfully = false;
        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(userDao);
            isDeletedSuccessfully = userDao.deleteUserById(userId);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to process delete user by id in service.", e);
            throw new ServiceException("Fail to process delete user by id in service.", e);
        } finally {
            helper.endTransaction();
        }

        return isDeletedSuccessfully;
    }

    @Override
    public List<User> showAllTeachers() throws ServiceException {
        List<User> teachersList = null;
        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(userDao);
            teachersList = userDao.findAllTeachers();
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to get all teachers from dao", e);
            throw new ServiceException("Fail to get all teachers from dao", e);
        } finally {
            helper.endTransaction();
        }
        return teachersList;
    }

    @Override
    public boolean isEmailTaken(String emailInput) throws ServiceException {
        boolean isTaken = false;

        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(userDao);
            isTaken = userDao.checkUserByEmail(emailInput);
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to check email availability.", e);
            throw new ServiceException("Fail to check email availability.", e);
        } finally {
            helper.endTransaction();
        }
        return isTaken;
    }

    @Override
    public boolean singUp(String userEmail, String userPassword, String firstName, String lastName, String role) throws ServiceException {
        boolean isRegistered = false;
        String hashedPassword = PasswordHasher.shaHashing(userPassword);

        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(userDao);
            isRegistered = userDao.addUser(userEmail, hashedPassword, firstName, lastName, role);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to create new user during sign up.", e);
            throw new ServiceException("Fail to create new user during sign up.", e);
        } finally {
            helper.endTransaction();
        }
        return isRegistered;
    }

    @Override
    public boolean joinCourse(int courseId, int userId) throws ServiceException {
        Boolean isJoined = false;
        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try {
            helper.beginTransaction(userDao);
            isJoined = userDao.joinCourse(courseId, userId);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to join the course.", e);
            throw new ServiceException("Fail to join the course.", e);
        } finally {
            helper.endTransaction();
        }
        return isJoined;
    }

    @Override
    public boolean recoverPassword(String userEmail, String subject, String text) throws ServiceException {
        boolean isSent = false;
        String password = PasswordCreator.createPassword();
        UserDaoImpl userDao = new UserDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(userDao);
            boolean isUserExists = userDao.checkUserByEmail(userEmail);
            if(isUserExists) {
                String hashedPassword = PasswordHasher.shaHashing(password);
                userDao.updateUserPassword(userEmail, hashedPassword);

                MailSender mailSender = new MailSender();
                String completeMessage = text + password;
                mailSender.send(subject, completeMessage, userEmail);

                isSent = true;
            }
            helper.commit();
        } catch (DaoException | MessagingException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to update user password.", e);
            throw new ServiceException("Fail to update user password.", e);
        } finally {
            helper.endTransaction();
        }

        return isSent;
    }
}