package com.github.methodia.minibilling;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Readings {
    public File readings;

    public Readings(File readings) {
        this.readings = readings;
    }

    private ArrayList<Integer> number = new ArrayList<>();
    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<ZonedDateTime> data = new ArrayList<>();
    private ArrayList<Integer> price = new ArrayList<>();
    private ArrayList<BigDecimal> quantity = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateStart = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateEnd = new ArrayList<>();
    int counterReadings = 0;

    public Readings(ArrayList<Integer> number, ArrayList<String> product,
                    ArrayList<ZonedDateTime> data, ArrayList<Integer> price, ArrayList<BigDecimal> dateDiff) {
        this.number = number;
        this.product = product;
        this.data = data;
        this.price = price;
        this.quantity = dateDiff;
    }


    public void printRead() throws FileNotFoundException, ParseException {

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
                price.add(Integer.parseInt(i));
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

    public Integer getPrice(int i) {
        return this.price.get(i);
    }

    public Integer getCount() {
        return this.counterReadings / 4;
    }

    public void Quantity() {
        int counter = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 10; j < 20; j++) {
                if (number.get(i) == counter && number.get(i) == number.get(j)) {
                    counter++;
                    BigDecimal haveToPay = BigDecimal.valueOf(Math.max(price.get(i), price.get(j)) - Math.min(price.get(i), price.get(j)));
                    quantity.add(haveToPay);
                    dateStart.add(data.get(i));
                    dateEnd.add(data.get(j));
                    i = 0;
                }
            }
        }

    }

    public BigDecimal getAmount(int i) {
        return this.quantity.get(i).multiply(BigDecimal.valueOf(1.8));
    }

    public BigDecimal getQuantity(int i) {
        return this.quantity.get(i);
    }

    public ZonedDateTime getStartDate(int i) {
        return this.dateStart.get(i);
    }

    public ZonedDateTime getEndDate(int i) {
        return this.dateEnd.get(i);
    }

    public String monthBG(ZonedDateTime date) {

        String month = date.getMonth().toString();
//        DateTimeFormatter bulgarianMonthFormetter =
//                DateTimeFormatter.ofPattern("MMMM", new Locale("bg"));
//        String formattedMonth = date.format(bulgarianMonthFormetter);
//        return formattedMonth;
        switch (month) {
            case "JANUARY":
                month = "������";
                break;
            case "FEBRUARY":
                month = "��������";
                break;
            case "MARCH":
                month = "����";
                break;
            case "APRIL":
                month = "�����";
                break;
            case "MAY":
                month = "���";
                break;
            case "JUNE":
                month = "���";
                break;
            case "JULY":
                month = "���";
                break;
            case "AUGUST":
                month = "������";
                break;
            case "SEPTEMBER":
                month = "���������";
                break;
            case "OCTOBER":
                month = "��������";
                break;
            case "NOVEMBER":
                month = "�������";
                break;
            case "DECEMBER":
                month = "��������";
                break;
            default:
                break;

        }

        return month;
    }

    public Integer getYear(ZonedDateTime date) {
        int year = date.getYear();
        year = year % 100;
        return year;
    }
}


