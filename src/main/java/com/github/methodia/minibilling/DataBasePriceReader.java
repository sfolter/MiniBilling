package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class DataBasePriceReader {

    private final String queryParameter;

    public DataBasePriceReader(final String queryParameter) {
        this.queryParameter = queryParameter;
    }

    public List<Price> read() throws SQLException, ClassNotFoundException {
        final List<Price> prices = new LinkedList<>();
        final DBConnection dbConnection = new DBConnection(queryParameter);
        final ResultSet resultSet = dbConnection.createConnection();
        while (resultSet.next()) {
            final String product = resultSet.getString("productName");
            final LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
            final LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
            final BigDecimal value = resultSet.getBigDecimal("price");
            prices.add(new Price(product, startDate, endDate, value));
        }
        return prices;
    }
}
