package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.pool.ConnectionPool;
import by.epam.onlinetraining.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionHelper {
    private final static Logger LOGGER = LogManager.getLogger(TransactionHelper.class);
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private ProxyConnection connection = connectionPool.getConnection();

    public void beginTransaction(AbstractDao dao, AbstractDao... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem when trying to set auto commit false.");
        }

        dao.setConnection(connection);
        for (AbstractDao daoIteration : daos) {
            daoIteration.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem when trying to set auto commit true.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Problem when trying to close.");
            }
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction commit false.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem when trying to rollback the transaction.", e);
        }
    }
}
