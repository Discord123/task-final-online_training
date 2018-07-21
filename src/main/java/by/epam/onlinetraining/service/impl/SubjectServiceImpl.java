package by.epam.onlinetraining.service.impl;

import by.epam.onlinetraining.dao.TransactionHelper;
import by.epam.onlinetraining.dao.impl.SubjectDaoImpl;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.exception.DaoException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.SubjectService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private static final Logger Logger = LogManager.getLogger(SubjectServiceImpl.class);

    @Override
    public List<Subject> showAllSubjects() throws ServiceException {
        List<Subject> subjectList = null;
        SubjectDaoImpl subjectDao = new SubjectDaoImpl();
        TransactionHelper helper = new TransactionHelper();
        try{
            helper.beginTransaction(subjectDao);
            subjectList = subjectDao.showAllSubjects();
            helper.commit();
        } catch (DaoException e){
            helper.rollback();
            Logger.log(Level.FATAL, "Fail to show all subjects in service.", e);
            throw new ServiceException("Fail to show all subjects in service.", e);
        }finally {
            helper.endTransaction();
        }
        return subjectList;
    }
}
