//package com.github.methodia.minibilling.jdbcQueries;
//
//import com.github.methodia.minibilling.Price;
//import com.github.methodia.minibilling.User;
//
//
//import javax.xml.transform.Result;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class UserDataBaseRead {
//    public static List<User>userReader(Connection connection){
//        List<User>userList=new ArrayList<>();
//        Map<Integer, List<Price>> priceMap =PriceDataBaseRead.priceReader(connection).stream()
//                .collect(Collectors.groupingBy(Price::getNumberPriceList));
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("Select* from users");
//            ResultSet resultSet=preparedStatement.executeQuery();
//            while(resultSet.next()){
//            String name =resultSet.getString("name");
//            String ref=resultSet.getString("refferent_number");
//            int numPricingList=resultSet.getInt("number_priceList");
//            String currency=resultSet.getString("currency");
//            userList.add(new User(name,ref,numPricingList,priceMap.get(numPricingList),currency));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//   return userList; }
//}
