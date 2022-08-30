package com.github.methodia.minibilling;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VatTest {

    @Test
    void vatGetters() {


        List<Integer> linesList = new ArrayList<>();
        linesList.add(1);

        Vat vat = new Vat(1, linesList, new BigDecimal("20"), "40", new BigDecimal("23"));

        Assertions.assertEquals(new BigDecimal("23"),vat.getAmount(),"Vat amount is incorrect.");
        Assertions.assertEquals(linesList,vat.getLines(),"Lines List is incorrect.");
    }
    }


