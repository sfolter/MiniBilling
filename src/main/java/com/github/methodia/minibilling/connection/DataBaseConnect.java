package com.github.methodia.minibilling.connection;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnect{
    private static Connection connection;
    private static final String connectionString="jdbc:postgresql://localhost:5432/";

    private DataBaseConnect(){}


    public static Connection createConnection(String user, String password, String databaseName){
        Properties properties=new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);
        try {
            return DriverManager.getConnection(connectionString+databaseName,properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
