package com.github.methodia.minibilling;


import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadingsFileReader  implements FileReading{
    String path;
    final private Path resourceDirectory = Paths.get("src","test","resources","sample1","input");
    final private String readingsFilePath=resourceDirectory+"\\" +"readings.csv";
    public ReadingsFileReader() {
        this.path = readingsFilePath;

    }
//    Read from readings file the information about readings in Map data structure.
//    The boolean variable sets which to be the key of Map (if istrue-the key is Refferent number if != true- the key is product)
    public Map<String,List<Readings>> convertReadingsInformationToMap(Boolean istrue)throws Exception {
        String line = "";
        Map<String,List<Readings>> mapOfReadingInformation= new LinkedHashMap<>();
        try {
            BufferedReader br =new BufferedReader (new FileReader(path));
            while ((line = br.readLine()) != null)
            {

                String[] data = line.split(",");
                String referentNumber= data[0];
                String product= data[1];
                String time=data[2];
                ZonedDateTime instant=ZonedDateTime.parse(time);
                double price= Double.parseDouble(data[3]);
                if (istrue){
                    mapOfReadingInformation.putIfAbsent(referentNumber,new ArrayList<>());
                    mapOfReadingInformation.get(referentNumber).add(new Readings(referentNumber,product,instant,price));
                }else{
                    mapOfReadingInformation.putIfAbsent(product,new ArrayList<>());
                    mapOfReadingInformation.get(product).add(new Readings(referentNumber,product,instant,price));

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapOfReadingInformation; }
}
