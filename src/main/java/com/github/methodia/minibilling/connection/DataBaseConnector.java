package com.github.methodia.minibilling.connection;

import java.sql.Connection;

public interface DataBaseConnector {
    public Connection createConnection(String user, String password, String databaseName);
}
