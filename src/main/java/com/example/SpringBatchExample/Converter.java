package com.example.SpringBatchExample;

import java.math.BigDecimal;

public interface Converter {

    BigDecimal convertTo(String convertFrom, String convertTo, BigDecimal amount);
}
