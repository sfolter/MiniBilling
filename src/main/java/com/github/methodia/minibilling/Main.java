package com.github.methodia.minibilling;

public class Main {
    public static void main(String[] args) throws Exception {
        String userDataPath="C:\\Users\\user\\Desktop\\New folder\\users.csv";
        String reportsDataPath="C:\\Users\\user\\Desktop\\New folder\\readings.csv";
        String pricesDataPath="C:\\Users\\user\\Desktop\\New folder\\prices.csv";
        final UsersFileReading usersFileReading = new UsersFileReading(userDataPath);
        usersFileReading.readToArrayList();
        PricesFileReader pricesFileReader=new PricesFileReader(pricesDataPath);
        for (int i = 0; i < usersFileReading.readToArrayList().size() ; i++) {
            User user= usersFileReading.readToArrayList().get(i);
            Prices prices=pricesFileReader.readToArrayList().get(i);
            System.out.println(prices);
        }

    }
}

