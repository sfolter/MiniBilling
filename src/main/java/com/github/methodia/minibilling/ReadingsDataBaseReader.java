package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Todor Todorov
 * @Date 09.09.2022
 * Methodia Inc.
 */
public class ReadingsDataBaseReader {

    final List<Reading> readingsList = new ArrayList<>();

    public Collection<Reading> read(final Map<String, User> userMap) throws SQLException, ClassNotFoundException {
        Connection c = null;
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from public.readings");//TODO prepared statements
        while (rs.next()) {
            try {
                String refNum = rs.getString("refNum");
                String product = rs.getString("product");
                ZonedDateTime time = rs.getTimestamp("date").toLocalDateTime().atZone(ZoneId.of("+2"));
                final ZonedDateTime parsedZonedDateTime = time.withZoneSameInstant(ZoneOffset.UTC);
                System.out.println(parsedZonedDateTime);
                BigDecimal measurment = rs.getBigDecimal("measurment");
                readingsList.add(new Reading(userMap.get(refNum), product, parsedZonedDateTime, measurment));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        rs.close();

        stmt.close();

        c.close();
        return readingsList;
    }

}
