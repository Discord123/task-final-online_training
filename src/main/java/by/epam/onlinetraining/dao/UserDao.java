package by.epam.onlinetraining.dao;


import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.exceptions.DaoException;

import java.util.List;

public interface UserDao {
    User findUserByEmailAndPassword(String email, String password) throws DaoException;
    boolean checkUserByEmail(String email) throws DaoException;
    boolean addUser(String userEmail, String hashedPassword, String firstName, String lastName, String role) throws DaoException;
    boolean updateUserPassword(String email, String password) throws DaoException;
    boolean joinCourse(int courseId, int userId) throws DaoException;
    List<User> findAllTeachers() throws DaoException;
    boolean deleteUserById(int userID) throws DaoException;
}
