package com.github.methodia.minibilling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientPair {
    HashMap<String, List<Report>> referenceReport =new HashMap<>();
    public HashMap<String,List<Report>> makeReferenceReportMap(ArrayList<Report> array){
        HashMap<String,List<Report>> referenceReport =new HashMap<>();

        for (int i = 0; i < array.size() ; i++) {
            if (referenceReport.containsKey(array.get(i).getRefernceNumber())) {
                final List<Report> reportList= referenceReport.get(array.get(i).getRefernceNumber());
                  reportList.add(array.get(i));
            }else {
                final ArrayList<Report> reports= new ArrayList<>();
                reports.add(array.get(i));
                referenceReport.put(array.get(i).getRefernceNumber(), reports);
            }
        }
        return referenceReport;
    }
}
