package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.pool.ProxyConnection;

public abstract class AbstractDao {

    protected static ThreadLocal<ProxyConnection> connectionThreadLocal = new ThreadLocal<ProxyConnection>(){};

    public static void insertConnection(ProxyConnection connection){
        connectionThreadLocal.set(connection);
    }
}
