package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionHelper {
    private final static Logger Logger = LogManager.getLogger(TransactionHelper.class);

    public static void beginTransaction(ProxyConnection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to set auto commit false.");
        }
    }

    public static void endTransaction(ProxyConnection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to set auto commit true.");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.log(Level.ERROR, "Problem when trying to close.");
            }
        }
    }

    public static void commit(ProxyConnection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Transaction commit false.", e);
        }
    }

    public static void rollback(ProxyConnection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to rollback the transaction.", e);
        }
    }
}
