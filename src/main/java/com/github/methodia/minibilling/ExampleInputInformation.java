package com.github.methodia.minibilling;

import java.util.ArrayList;
import java.util.List;

public class ExampleInputInformation {
    public List<VatPercentage> vatPercentages(){
        List<VatPercentage> vatPercentages= new ArrayList<>();
        VatPercentage percentage1=new VatPercentage(60,20);
        VatPercentage percentage2=new VatPercentage(40,10);
        vatPercentages.add(percentage1);
        vatPercentages.add(percentage2);
        return vatPercentages;
    }
    public List<Vat> vatInfo(List<VatPercentage> percentages){
        List<Vat>vatInfo=new ArrayList<>();
        return vatInfo;
    }
}
