package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataBaseReadingReader implements ReadingsReader {

    private final String queryParameter;

    private final Map<String, User> userMap;

    public DataBaseReadingReader(final String queryParameter, final Map<String, User> userMap) {
        this.queryParameter = queryParameter;
        this.userMap = userMap;
    }

    @Override
    public Collection<Reading> read() throws SQLException, ClassNotFoundException {
        final List<Reading> readingsList = new ArrayList<>();
        final DBConnection dbConnection = new DBConnection(queryParameter);
        final ResultSet resultSet = dbConnection.createConnection();
        while (resultSet.next()) {
            final String time = resultSet.getDate("date") + "T" + resultSet.getTime("date") + "+02:00";
            final ZonedDateTime parsedTime = ZonedDateTime.parse(time,
                            DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    .withZoneSameInstant(ZoneId.of("GMT"));
            final BigDecimal pokazanie = resultSet.getBigDecimal("pokazanie");
            final String userRef = resultSet.getString("user_ref");
            final String product = resultSet.getString("product");
            readingsList.add(new Reading(parsedTime, pokazanie, userMap.get(userRef), product));

        }
        return readingsList;
    }
}
