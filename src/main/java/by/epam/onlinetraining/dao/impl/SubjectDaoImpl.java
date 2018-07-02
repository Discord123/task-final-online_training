package by.epam.onlinetraining.dao.impl;

import by.epam.onlinetraining.dao.AbstractDao;
import by.epam.onlinetraining.dao.SubjectDao;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.exceptions.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl extends AbstractDao implements SubjectDao {
    private static final Logger LOGGER = LogManager.getLogger(SubjectDaoImpl.class);
    private static final String FIND_ALL_SUBJECTS = "SELECT * FROM subjects";

    @Override
    public List<Subject> showAllSubjects() throws DaoException {
        List<Subject> subjectList = new ArrayList<>();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_SUBJECTS)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Subject subject = ResultSetParser.createSubject(resultSet);
                subjectList.add(subject);
            }
        } catch (SQLException e){
            LOGGER.log(Level.FATAL, "Fail to get all subjects from dao.", e);
            throw new DaoException("Fail to get all subjects from dao.", e);
        }
        return subjectList;
    }
}
