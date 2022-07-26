package com.github.methodia.minibilling;

import org.json.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, NoSuchFieldException, IllegalAccessException {

        User user = new User(new File("C:\\Users\\user\\Desktop\\MiniBilling\\user.csv"));
        user.print();

        Readings readings = new Readings(new File("C:\\Users\\user\\Desktop\\MiniBilling\\readings.csv"));
        readings.printRead();
        readings.Quantity();


        Prices prices = new Prices(new File("C:\\Users\\user\\Desktop\\MiniBilling\\prices.csv"));
        prices.print();


        JSONObject jsonObject = new JSONObject();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();



        for (int i = 0; i < user.getCount(); i++) {
            int diff = user.getReference(i);
            int min = 10000;
            int max = 99999;

            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

            Field changeMap = jsonObject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonObject, new LinkedHashMap<>());
            changeMap.setAccessible(false);

            jsonObject.put("documentDate", dateFormat.format(cal.getTime()));
            jsonObject.put("documentNumber", random_int);
            jsonObject.put("consumer", user.getConsumer(i));
            jsonObject.put("reference", user.getPriceList(i));
            jsonObject.put("totalAmount", readings.getAmount(diff - 1));


            JSONArray courses = new JSONArray(
                    new String[]{"index: " + user.getReference(i), "quantity: " + readings.getQuantity(diff-1),
                            "lineStart: " + readings.getStartDate(diff - 1), "lineEnd: " + readings.getEndDate(diff - 1),
                            "product: " + readings.getProduct(i), "price: " + prices.getPrice(0),
                            "priceList: " + user.getPriceList(i), "amount: " + readings.getAmount(diff - 1)});

            jsonObject.put("lines", courses);




            String path = "C:\\Users\\user\\Desktop\\tasks\\MiniBillingNew\\src\\test\\";

            String folderName = user.getConsumer(i) + "-" + user.getReference(i);
            path = path + folderName;

            File file = new File(path);

            boolean bool = file.mkdir();

            String txtName = random_int + "-" + readings.monthBG(readings.getEndDate(i)) + "-" + readings.getYear(readings.getEndDate(i));


            File Dirfile = new File("C:\\Users\\user\\Desktop\\tasks\\MiniBillingNew\\src\\test\\" + folderName + "\\" + txtName + ".txt");
            Dirfile.getParentFile().mkdirs();
            Dirfile.createNewFile();
            Path pathTXT = Paths.get(Dirfile.getAbsolutePath());
            Files.write(pathTXT, jsonObject.toString(4).getBytes());


        }

    }

}
