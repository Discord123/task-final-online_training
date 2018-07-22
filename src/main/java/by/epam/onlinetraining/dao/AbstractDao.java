package by.epam.onlinetraining.dao;

import by.epam.onlinetraining.dao.pool.ProxyConnection;

public abstract class AbstractDao {

    protected ProxyConnection proxyConnection;

    void setConnection(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }
}
