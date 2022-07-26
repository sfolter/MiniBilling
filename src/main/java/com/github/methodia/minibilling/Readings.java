package com.github.methodia.minibilling;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class Readings {
    public File readings;

    public Readings(File readings) {
        this.readings = readings;
    }
    private ArrayList<Integer> number = new ArrayList<>();
    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<ZonedDateTime> data = new ArrayList<>();
    private ArrayList<Integer> quantityFullValue = new ArrayList<>();
    private ArrayList<BigDecimal> quantity = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateStart = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateEnd = new ArrayList<>();
    private int counterReadings = 0;

    public Readings(ArrayList<Integer> number, ArrayList<String> product,
                    ArrayList<ZonedDateTime> data, ArrayList<Integer> price, ArrayList<BigDecimal> dateDiff,
                    ArrayList<ZonedDateTime> dateStart, ArrayList<ZonedDateTime> dateEnd, int counter) {
        this.number = number;
        this.product = product;
        this.data = data;
        this.quantityFullValue = price;
        this.quantity = dateDiff;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.counterReadings = counter;
    }
    public void read() throws FileNotFoundException, ParseException {

        Scanner sc = new Scanner(this.readings);
        sc.useDelimiter(",|\\r\\n");
        int counter = 0;
        while (sc.hasNext()) {

            String i = sc.next();
            counter++;

            if (counter == 1) {
                number.add(Integer.parseInt(i));

            }
            if (counter == 2) {
                product.add(i);

            }
            if (counter == 3) {

                ZonedDateTime instant = ZonedDateTime.parse(i);
                data.add(instant);

            }
            if (counter == 4) {
                quantityFullValue.add(Integer.parseInt(i));
                counter = 0;
            }
            counterReadings++;
        }

    }
    public Integer getNumber(int i) {
        return this.number.get(i);
    }
    public String getProduct(int i) {
        return this.product.get(i);
    }
    public ZonedDateTime getData(int i) {
        return this.data.get(i);
    }
    public void Quantity() throws FileNotFoundException {
        int counter = 1;
        User user = new User(new File("C:\\Users\\user\\Desktop\\MiniBilling\\user.csv"));
        user.read();
        while (counter != user.getCount() + 1) {
            for (int i = 0; i < getCountReadings(); i++) {
                for (int j = i + 1; j < getCountReadings(); j++) {
                    if (number.get(i) == counter && number.get(i) == number.get(j)) {
                        counter++;
                        BigDecimal haveToPay = BigDecimal.valueOf(Math.max(quantityFullValue.get(i), quantityFullValue.get(j)) - Math.min(quantityFullValue.get(i), quantityFullValue.get(j)));
                        quantity.add(haveToPay);
                        dateStart.add(data.get(i));
                        dateEnd.add(data.get(j));
                        i = 0;

                    }
                }
            }
        }
    }


    public BigDecimal getQuantity(int i) {
        return this.quantity.get(i);
    }
    public BigDecimal getAmount(int i) throws FileNotFoundException {
        double rightPrice = 0;
        User user = new User(new File("C:\\Users\\user\\Desktop\\MiniBilling\\user.csv"));
        user.read();
        Prices prices = new Prices(new File("C:\\Users\\user\\Desktop\\MiniBilling\\prices.csv"));
        prices.read();

            //System.out.println(dateStart.get(0));


        return this.quantity.get(i).multiply(BigDecimal.valueOf(1.8));
    }
    public ZonedDateTime getStartDate(int i) {
        return this.dateStart.get(i);
    }
    public ZonedDateTime getEndDate(int i) {
        return this.dateEnd.get(i);
    }
    public String monthBG(ZonedDateTime date) {

       // String month = date.getMonth().toString();
        DateTimeFormatter bulgarianMonthFormetter =
                DateTimeFormatter.ofPattern("MMMM", new Locale("bg"));
        String formattedMonth = date.format(bulgarianMonthFormetter).substring(0, 1).toUpperCase()
                + date.format(bulgarianMonthFormetter).substring(1);
        return formattedMonth;
//        switch (month) {
//            case "JANUARY":
//                month = "Януари";
//                break;
//            case "FEBRUARY":
//                month = "Февруари";
//                break;
//            case "MARCH":
//                month = "Март";
//                break;
//            case "APRIL":
//                month = "Април";
//                break;
//            case "MAY":
//                month = "Май";
//                break;
//            case "JUNE":
//                month = "Юни";
//                break;
//            case "JULY":
//                month = "Юли";
//                break;
//            case "AUGUST":
//                month = "Август";
//                break;
//            case "SEPTEMBER":
//                month = "Септември";
//                break;
//            case "OCTOBER":
//                month = "Октомври";
//                break;
//            case "NOVEMBER":
//                month = "Ноември";
//                break;
//            case "DECEMBER":
//                month = "Декември";
//                break;
//            default:
//                break;
//
//        }
//
//        return month;
    }
    public Integer getYear(ZonedDateTime date) {
        int year = date.getYear();
        year = year % 100;
        return year;
    }
    public Integer getCountReadings(){
        return this.counterReadings/4;
    }
}


