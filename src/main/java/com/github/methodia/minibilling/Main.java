package com.github.methodia.minibilling;

import java.util.ArrayList;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) {
        String directoryClients = "C:\\Users\\user\\IdeaProjects\\MiniBilling1\\src\\main\\resources\\Client.csv";
        String directoryPrices = "C:\\Users\\user\\IdeaProjects\\MiniBilling1\\src\\main\\resources\\Prices.csv";
        ClientReader readerFile = new ClientReader();
        ArrayList<Client> arrayOfClients= readerFile.readClientsToList(directoryClients);
        for (int i = 0; i < arrayOfClients.size(); i++) {
            Client client =arrayOfClients.get(i);
            if (client.getName().equals("John")) {
                System.out.println(client.getReferenseNumber());
            }
        }
        PricesReader pricesReader=new PricesReader();
        ArrayList<Prices> arrayOfPrices=pricesReader.readPricesToList(directoryPrices);
        for (int i = 0; i < arrayOfPrices.size(); i++) {
            Prices price=arrayOfPrices.get(i);
            System.out.println(price);
        }
    }
}
