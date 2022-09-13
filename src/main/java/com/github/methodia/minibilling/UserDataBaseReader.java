package com.github.methodia.minibilling;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Todor Todorov
 * @Date 09.09.2022
 * Methodia Inc.
 */
public class UserDataBaseReader implements UsersReader{

    PriceDataBaseReader priceDataBaseReader = new PriceDataBaseReader();
    @Override
    public Map<String, User> read() throws SQLException, ClassNotFoundException {
        Connection c = null;
        Class.forName("org.postgresql.Driver");

        c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        Statement stmt = c.createStatement();
        final TreeMap<String, User> userMap = new TreeMap<>();
        final List<User> userList = new ArrayList<>();
        int counter = 0;


        ResultSet rs = stmt.executeQuery("select * from public.users ;");
        while (rs.next()) {
            try {
                String name = rs.getString("name");
                String refNum = rs.getString("refNum");
                int priceListNumb = rs.getInt("priceListNumb");
                List<Price> priceList = priceDataBaseReader.read(priceListNumb);

                userList.add(new User(name, refNum, priceList, priceListNumb));
                userMap.put(refNum, userList.get(counter));
                counter++;



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        rs.close();

        stmt.close();

        c.close();
        return userMap;
    }

}
