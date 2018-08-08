package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.pool.ConnectionPool;
import by.epam.onlinetraining.dao.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager implements AutoCloseable {
    private final static org.apache.logging.log4j.Logger Logger = LogManager.getLogger(TransactionManager.class);
    private static TransactionManager instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static ThreadLocal<ProxyConnection> connectionThreadLocal = new ThreadLocal<ProxyConnection>();


    public static TransactionManager launchTransaction(AbstractDao... daos) throws SQLException {
        return launchTransaction(Isolation.REPEATABLE_READ, daos);
    }

    public static TransactionManager launchTransaction(Isolation isolationLevel, AbstractDao... daos) throws SQLException {
        TransactionManager instance = instantiate();
        initialize(daos);

        ProxyConnection connection = connectionThreadLocal.get();
        connection.setTransactionIsolation(isolationLevel.level);
        instance.beginTransaction();

        return instance;
    }

    public static TransactionManager launchQuery(AbstractDao... daos) throws SQLException {
        TransactionManager instance = instantiate();
        initialize(daos);
        ProxyConnection connection = connectionThreadLocal.get();
        connection.setReadOnly(true);
        return instance;
    }

    private static void initialize(AbstractDao... daos){
        ProxyConnection connection = connectionPool.getConnection();
        connectionThreadLocal.set(connection);

        for (AbstractDao dao : daos){
            dao.insertConnection(connection);
        }
    }

    private static TransactionManager instantiate(){
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TransactionManager();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void beginTransaction() {
        ProxyConnection connection = connectionThreadLocal.get();

        try {
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to set auto commit false.", e);
        }
    }

    private void commit() throws SQLException {
        try {
            ProxyConnection connection = connectionThreadLocal.get();
            connection.commit();
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Transaction commit false.", e);
            throw e;
        }
    }

    private void rollback() {
        try {
            ProxyConnection connection = connectionThreadLocal.get();
            connection.rollback();
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to rollback the transaction.", e);
        }
    }

    private void endTransaction() throws SQLException{
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to set auto commit true.");
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() throws SQLException {
        ProxyConnection connection = connectionThreadLocal.get();
        try {
            connection.close();
        } catch (SQLException e) {
            Logger.log(Level.ERROR, "Problem when trying to close.");
            throw e;
        }
    }

    @Override
    public void close() throws SQLException {
        ProxyConnection connection = connectionThreadLocal.get();
        if(connection.isReadOnly()){
            connection.setReadOnly(false);
            closeConnection();
            return;
        }

        try {
            commit();
        } catch (SQLException e) {
            rollback();
        } finally {
            endTransaction();
        }
    }

    public enum Isolation {
        NONE(0),
        READ_UNCOMMITTED(1),
        READ_COMMITTED(2),
        REPEATABLE_READ(4),
        SERIALIZABLE(8);

        private int level;

        Isolation(int level){
            this.level = level;
        }
    }

}


