package by.epam.onlinetraining.dao.impl;


import by.epam.onlinetraining.dao.AbstractDao;
import by.epam.onlinetraining.dao.UserDao;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.entity.Role;
import by.epam.onlinetraining.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final Logger Logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String USER_ID = "user_id";
    private static final String USER_IS_DELETED = "user_isDeleted";

    private static final String DELETE_TEACHER_BY_ID =
        "CALL deleteTeacher(?);";

    private static final String FIND_ALL_USERS_BY_ROLE =
            "SELECT * FROM users " +
                    "WHERE user_role=? " +
                    "AND user_isDeleted!=1";

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM users " +
                    "WHERE user_email=? " +
                    "AND user_password=?";

    private static final String CHECK_USER_BY_EMAIL =
            "SELECT user_id, user_isDeleted " +
                    "FROM users " +
                    "WHERE user_email=?";

    private static final String ADD_COURSE_TO_USER =
            "INSERT INTO courses_has_students " +
                    "(courses_course_id, " +
                    "users_user_id) " +
                    "VALUES (?,?)";

    private static final String ADD_USER =
            "INSERT INTO users " +
                    "(user_email, " +
                    "user_password, " +
                    "first_name, " +
                    "last_name, " +
                    "user_role) " +
                    "VALUES (?,?,?,?,?)";

    private static final String UPDATE_USER_BY_ID =
            "UPDATE users" +
                    "SET user_email=?, " +
                    "user_password=?, " +
                    "first_name=?, " +
                    "last_name=?, " +
                    "WHERE user_id=?";

    private static final String UPDATE_USERS_PASSWORD =
            "UPDATE users " +
                    "SET user_password=? " +
                    "WHERE user_email=?";


    @Override
    public boolean deleteUserById(int userID) throws DaoException {
        boolean isDeleteSuccessfully = false;
        try(PreparedStatement statement = proxyConnection.prepareStatement(DELETE_TEACHER_BY_ID)){
            statement.setInt(1, userID);
            statement.executeUpdate();
            isDeleteSuccessfully=true;
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to delete user by id in DAO.", e);
            throw new DaoException("Fail to delete user by id in DAO.", e);
        }
        return isDeleteSuccessfully;
    }

    @Override
    public List<User> findAllTeachers() throws DaoException {
        List<User> teachersList = new ArrayList<>();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_USERS_BY_ROLE)){
            String teacherRoleLine = Role.TEACHER.toString();
            String role = teacherRoleLine.toLowerCase();
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                User teacher = ResultSetParser.createUser(resultSet);
                teachersList.add(teacher);
            }
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Exception while trying to find user by role.", e);
            throw new DaoException("Exception while trying to find user by role.", e);
        }
        return teachersList;
    }

    @Override
    public boolean joinCourse(int courseId, int userId) throws DaoException {
        boolean isJoined = false;
        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_COURSE_TO_USER)){
            statement.setInt(1, courseId);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();
            isJoined = (result != 0);
        } catch (SQLException e){
            Logger.log(Level.FATAL, "Exception while trying to join the course in DAO.", e);
            throw new DaoException("Exception while trying to join the course in DAO.", e);
        }
        return isJoined;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        User user = null;
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)){
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = ResultSetParser.createUser(resultSet);
            }

        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Exception while trying to find user by email and password in DAO.", e);
            throw new DaoException("Exception while trying to find user by email and password in DAO.", e);
        }
        return user;
    }

    @Override
    public boolean checkUserByEmail(String email) throws DaoException {
        boolean isFound = false;
        int userId = 0;
        boolean isDeleted = false;

        try(PreparedStatement statement = proxyConnection.prepareStatement(CHECK_USER_BY_EMAIL)){
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(USER_ID);
                isDeleted = (resultSet.getInt(USER_IS_DELETED) == 1);
            }
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Can't find user with email: " + email, e);
            throw new DaoException("Can't find user with email: " + email, e);
        }
        if (userId != 0 && !isDeleted) {
            isFound = true;
        }
        return isFound;
    }

    @Override
    public boolean addUser(String userEmail, String hashedPassword, String firstName, String lastName, String role) throws DaoException {
        boolean isUserAdded = false;

        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_USER)){
            statement.setString(1, userEmail);
            statement.setString(2, hashedPassword);
            statement.setString(3, firstName);
            statement.setString(4, lastName);

            String userRole;
            if (role.equalsIgnoreCase("teacher")){
            userRole = Role.TEACHER.toString();
            } else {
                userRole = Role.STUDENT.toString();
            }
            String userRoleLowerCase = userRole.toLowerCase();
            statement.setString(5, userRoleLowerCase);
            isUserAdded = statement.executeUpdate() != 0;
        }catch (SQLException e){
            Logger.log(Level.FATAL, "Fail to add new user in DAO.", e);
            throw new DaoException("Fail to add new user in DAO.", e);
        }
        return isUserAdded;
    }

    @Override
    public boolean updateUserPassword(String email, String password) throws DaoException {
        boolean isUpdated = false;
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_USERS_PASSWORD)) {
            statement.setString(1, password);
            statement.setString(2, email);

            if(statement.executeUpdate() != 0){
                isUpdated = true;
            }

        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Fail to update user password in DAO.", e);
            throw new DaoException("Fail to update user password in DAO.", e);
        }
        return isUpdated;
    }
}
