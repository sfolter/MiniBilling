package com.github.methodia.minibilling.jdbcQueries;


import com.github.methodia.minibilling.Reading;
import com.github.methodia.minibilling.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReadingsDataBaseRead  {
    public static List<Reading> readingsReader(Connection connection,List<User>userList){
        List<Reading>readingList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select* from readings");
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                String ref=resultSet.getString("referent_number");
                String product= resultSet.getString("product");
                String time=resultSet.getString("date");
                ZonedDateTime timeZDT = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.of("GMT"));
                LocalDateTime timeLDT = LocalDateTime.from(timeZDT);
                BigDecimal metrics=resultSet.getBigDecimal("metrics");
                User user=userList.stream().filter(user1 -> user1.getRef().equals(ref)).findFirst().orElse(null);
                readingList.add(new Reading(timeLDT,metrics,user));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return readingList; }
}
