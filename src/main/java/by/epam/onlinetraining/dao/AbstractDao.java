package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.pool.ProxyConnection;

public abstract class AbstractDao {

    protected ProxyConnection proxyConnection;

    void setConnection(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }
}
