package com.github.methodia.minibilling.jdbcQueries;

import com.github.methodia.minibilling.Price;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PriceDataBaseRead {
    public static List<Price> priceReader(Connection connection){
            List<Price> priceList=new ArrayList<>();
            try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select* from prices");
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
            String product=resultSet.getString("product");
                LocalDate startDate=LocalDate.parse(resultSet.getString("beginning_date"));
                LocalDate endDate=LocalDate.parse(resultSet.getString("end_date"));
                BigDecimal price=resultSet.getBigDecimal("price");
                int numberPricingList=resultSet.getInt("numberpricelist");

                priceList.add(new Price(product,startDate,endDate,price, numberPricingList));

                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceList;
            //.stream().collect(Collectors.groupingBy(Price::getNumberPriceList));
         }
}

