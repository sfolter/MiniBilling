package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class JsonFileGenerator {
    public void generateJsonFile(JSONObject json, String folderPath) throws ParseException, IOException {
        JSONArray jsonArray = (JSONArray) json.get("lines");
        HashMap<String, Object> jsonLines = (HashMap<String, Object>) jsonArray.toList().get(1);
        String lineEnd = (String) jsonLines.get("lineEnd");
        Date jud = new SimpleDateFormat("yy-MM").parse(lineEnd);
        String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        String[] splitDate = month.split("\\s+");
        String monthInCyrilic = splitDate[1];
        int year = Integer.parseInt(splitDate[2]) % 100;
        String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
        String fileWriter = (String) json.get("documentNumber") + "-" + monthInUpperCase + "-" + year;
        FileWriter file = new FileWriter(folderPath + "//" + fileWriter + ".json");
        file.write(json.toString(4));
        file.flush();
        file.close();


    }
}