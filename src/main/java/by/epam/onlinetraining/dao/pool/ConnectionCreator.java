package by.epam.onlinetraining.dao.pool;

import by.epam.onlinetraining.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionCreator {
    private static final Logger Logger = LogManager.getLogger(ConnectionCreator.class);
    static ProxyConnection createConnection() throws ConnectionPoolException {
        try {
            String url = DatabaseConfig.url;
            String user = DatabaseConfig.user;
            String password = DatabaseConfig.password;
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            Logger.log(Level.FATAL, "Fail to create connection.", e);
            throw new ConnectionPoolException("Fail to create connection." , e);
        }
    }
}
