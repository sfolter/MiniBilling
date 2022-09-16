package com.github.methodia.minibilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataBaseUserReader implements UsersReader {

    String queryParameter;

    public DataBaseUserReader(final String queryParameter) {
        this.queryParameter = queryParameter;
    }

    @Override
    public Map<String, User> read() throws SQLException, ClassNotFoundException {
        final DBConnection dbConnection = new DBConnection(queryParameter);
        final ResultSet resultSet = dbConnection.createConnection();
        final Map<String, User> userMap = new LinkedHashMap<>();
        final List<User> userList = new ArrayList<>();
        int counter = 0;
        while (resultSet.next()) {
            final DataBasePriceReader dataBasePriceReader = new DataBasePriceReader("prices");
            final List<Price> priceList = dataBasePriceReader.read();
            final String name = resultSet.getString("name");
            final String ref = resultSet.getString("ref");
            final int priceListNumber = resultSet.getInt("priceListNum");
            //            userList.add(new User(name, ref, priceListNumber, priceList));
            //            userMap.put(ref, userList.get(counter));
            counter++;

        }
        return userMap;
    }
}
