package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Todor Todorov
 * @Date 09.09.2022
 * Methodia Inc.
 */
public class PriceDataBaseReader {

    final ArrayList<Price> priceList = new ArrayList<>();

    public List<Price> read(final int priceListNum) throws SQLException, ClassNotFoundException {
        Connection c = null;
        Class.forName("org.postgresql.Driver");

        c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        Statement stmt = c.createStatement();


        ResultSet rs = stmt.executeQuery(
                "select * from public.pricelist where priceListNumb= " + priceListNum + ";");//TODO prepared statements
        while (rs.next()) {
            try {
                int priceListNumb = rs.getInt("priceListNumb");
                String product = rs.getString("product");
                LocalDate startDate = rs.getDate("startdate").toLocalDate();
                LocalDate endDate = rs.getDate("enddate").toLocalDate();
                BigDecimal price = rs.getBigDecimal("price");
                priceList.add(new Price(product, startDate, endDate, price));


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        rs.close();

        stmt.close();

        c.close();
        return priceList;
    }
}


