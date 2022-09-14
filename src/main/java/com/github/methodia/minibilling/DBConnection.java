package com.github.methodia.minibilling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private final String queryParameter;

    public DBConnection(final String queryParameter) {
        this.queryParameter = queryParameter;
    }

    public ResultSet createConnection() throws ClassNotFoundException, SQLException {
        final String url = "jdbc:postgresql://localhost:5432/postgres";
        final String name = "postgres";
        final String password = "12345";
        final String query = "select * from public." + queryParameter;
        Class.forName("org.postgresql.Driver");
        final Connection connection = DriverManager.getConnection(url, name, password);
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        final ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
