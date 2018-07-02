package by.epam.onlinetraining.pool;

import by.epam.onlinetraining.exceptions.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> connectionsQueue;

    public static ConnectionPool getInstance(){
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        initConnectionPool();
    }

    private void initConnectionPool() {
        connectionsQueue = new ArrayBlockingQueue<>(DatabaseConfig.poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch(SQLException e) {
            LOGGER.log(Level.FATAL, "JDBC driver does not registered", e);
            throw new RuntimeException("JDBC driver does not registered", e);
        }

        for (int i = 0; i < DatabaseConfig.poolSize; i++) {
            try {
                ProxyConnection connection = ConnectionCreator.createConnection();
                connectionsQueue.offer(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.log(Level.FATAL, "Connection does not created", e );
                throw new RuntimeException("Connection does not created", e);
            }
        }

        int size = connectionsQueue.size();

        if (size != DatabaseConfig.poolSize) {
            for (int i = 0; i < size; i++) {
                try {
                    ProxyConnection connection = ConnectionCreator.createConnection();
                    connectionsQueue.offer(connection);
                } catch (ConnectionPoolException e) {
                    LOGGER.log(Level.ERROR, "Creation connection error");
                }
            }

            size = connectionsQueue.size();
            if (size <= DatabaseConfig.poolSize/2) {
                LOGGER.log(Level.ERROR, "Not enough connection left.");
                throw new RuntimeException("Not enough connection left.");
            }
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionsQueue.take();
        } catch(InterruptedException e) {
            LOGGER.log(Level.ERROR, "Exception during getting connection", e);
        }
        return connection;
    }

    public void terminatePool() {

        for (int i = 0; i < connectionsQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionsQueue.take();
                connection.closeConnection();
            } catch (SQLException | InterruptedException e) {
                LOGGER.log(Level.ERROR, "Exception during pool termination", e);
            }
        }
        deregisterAllDrivers();
    }

    private void deregisterAllDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Deregister driver error");
        }
    }

    void releaseConnection(ProxyConnection connection) {
        connectionsQueue.offer(connection);
    }

}
