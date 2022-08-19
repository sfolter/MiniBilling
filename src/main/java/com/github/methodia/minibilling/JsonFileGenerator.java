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
    public void generateJsonFile(final JSONObject json, final String folderPath) throws ParseException, IOException {
        final JSONArray jsonArray = (JSONArray) json.get("lines");
        final HashMap<String, Object> jsonLines = (HashMap<String, Object>) jsonArray.toList().get(jsonArray.toList().size()-1);
        final String lineEnd = (String) jsonLines.get("lineEnd");
//        String lineEnd = lineEndJson.toString();
        final Date jud = new SimpleDateFormat("yy-MM").parse(lineEnd);
        final String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        final String[] splitDate = month.split("\\s+");
        final String monthInCyrilic = splitDate[1];
        final int year = Integer.parseInt(splitDate[2]) % 100;
        final String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
        final String fileWriter = json.get("documentNumber") + "-" + monthInUpperCase + "-" + year;
        final FileWriter file = new FileWriter(folderPath + "//" + fileWriter + ".json");
        file.write(json.toString(4));
        file.flush();
        file.close();


    }
}
