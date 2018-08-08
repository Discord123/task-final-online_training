package by.epam.onlinetraining.dao.pool;

import by.epam.onlinetraining.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger Logger = LogManager.getLogger(ConnectionPool.class);
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
            insertConnectionsIntoQueue(connectionsQueue);
        } catch(SQLException e) {
            Logger.log(Level.FATAL, "Initialization of connection pool doesn't happened", e);
            throw new RuntimeException("Initialization of connection pool doesn't happened", e);
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionsQueue.take();
        } catch(InterruptedException e) {
            Logger.log(Level.ERROR, "Exception during getting connection", e);
        }
        return connection;
    }

    public void terminatePool() {

        for (int i = 0; i < connectionsQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionsQueue.take();
                connection.closeConnection();
            } catch (SQLException | InterruptedException e) {
                Logger.log(Level.ERROR, "Exception during pool termination", e);
            }
        }
    }

    public void releaseConnection(ProxyConnection connection) {
        connectionsQueue.offer(connection);
    }

    private void insertConnectionsIntoQueue(BlockingQueue<ProxyConnection> connectionsQueue) {
        for (int i = 0; i < DatabaseConfig.poolSize; i++) {
            try {
                ProxyConnection connection = ConnectionCreator.createConnection();
                connectionsQueue.offer(connection);
            } catch (ConnectionPoolException e) {
                Logger.log(Level.FATAL, "Connection does not created", e );
                throw new RuntimeException("Connection does not created", e);
            }
        }
    }
}
