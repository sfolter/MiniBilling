package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

class ProportionalMeasurementDistributorTest {

    @Test
    void priceOverlapsMeasurementTest() {
        final BigDecimal measurementValue = new BigDecimal("200");

        final Measurement measurement1 = getMeasurement(measurementValue);

        final BigDecimal priceValue = new BigDecimal("1.50");
        final Price price = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 5, 1), priceValue);

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), Collections.singleton(price));


        final Collection<QuantityPricePeriod> qppCollection = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(1, qppCollection.size(),
                "Expecting only one measurement because no distribution is needed.");

        final QuantityPricePeriod singleQpp = qppCollection.iterator().next();
        final LocalDateTime qppStart = singleQpp.getStart();
        final LocalDateTime qppEnd = singleQpp.getEnd();
        final LocalDateTime measurement1Start = measurement1.getStart();
        final LocalDateTime measurement1End = measurement1.getEnd();
        Assertions.assertEquals(measurement1Start, qppStart,
                "Quantity price period start must match the measurement start.");
        Assertions.assertEquals(measurement1End, qppEnd, "Quantity price period end must match the measurement end.");
        final BigDecimal qppQuantity = singleQpp.getQuantity();
        final BigDecimal measurementQuantity = measurement1.getValue();
        Assertions.assertEquals(measurementQuantity, qppQuantity,
                "Measurement quantity and quantity price period quantity must match.");
        final BigDecimal expectedPrice = price.getValue();
        final Price actualPrice = singleQpp.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice,
                "The quantity price period price must match the single price provided.");

    }

    @Test
    void partialOverlapTwoPricesTest() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 20), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 21),
                LocalDate.of(2021, 5, 1), priceValue2);
        int firstHalfDays = 14;
        int secondHalfDays = 25;
        int measurementDays = firstHalfDays + secondHalfDays;
        BigDecimal firstQuantity = measurement1.getValue().divide(BigDecimal.valueOf(measurementDays), RoundingMode.HALF_DOWN)
                .multiply(BigDecimal.valueOf(firstHalfDays));
        BigDecimal secondQuantity = measurement1.getValue().subtract(firstQuantity);
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final LocalDateTime price1End = price1.getEnd().atTime(23, 59, 59);
        final LocalDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the second quantity must match the second price.");

    }

    @Test
    void testLocalDateCompareTo() {

        final LocalDate priceEndDate = LocalDate.of(2021, 03, 20);
        final LocalDate measurementEndDate = LocalDate.of(2021, 03, 21);
        final ArrayList<Object> objects = new ArrayList<>();
        System.out.println(priceEndDate.compareTo(measurementEndDate));
    }

    private static Measurement getMeasurement(BigDecimal measurementValue) {
        final Measurement measurement1 = new Measurement(LocalDateTime.of(2021, 3, 6, 13, 23),
                LocalDateTime.of(2021, 4, 14, 15, 32), measurementValue,
                new User("Test Testov", "ref", Collections.emptyList(),1));
        return measurement1;
    }

    @Test
    void partialOverlapThreePricesTest() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final BigDecimal priceValue3 = new BigDecimal("3.60");

        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 20), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 21),
                LocalDate.of(2021, 4, 1), priceValue2);
        final Price price3 = new Price("gas", LocalDate.of(2021, 4, 2),
                LocalDate.of(2021, 5, 1), priceValue3);
        long firstThirdDays = measurement1.getStart().until(price1.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long secondThirdDays = price2.getStart().atTime(00, 00, 00).until(price2.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long thirdThirdDays = price3.getStart().atTime(00, 00, 00).until(measurement1.getEnd(), ChronoUnit.DAYS);
        long measurementDays = firstThirdDays + secondThirdDays + thirdThirdDays;
        BigDecimal firstQuantity = measurement1.getValue().divide(BigDecimal.valueOf(measurementDays), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(firstThirdDays));
        BigDecimal secondQuantity = measurement1.getValue().divide(BigDecimal.valueOf(measurementDays), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(secondThirdDays));
        BigDecimal thirdQuantity = measurement1.getValue().subtract((firstQuantity.add(secondQuantity)));
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(3, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");


        final LocalDateTime price1End = price1.getEnd().atTime(23, 59, 59);
        final LocalDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for second half does not match");

        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the second quantity must match the second price.");


        final QuantityPricePeriod qpp3 = qppList.get(2);
        Assertions.assertEquals(thirdQuantity, qpp3.getQuantity(),
                "Distributed quantity for third half does not match");

        final LocalDateTime price3AtStartOfDay = price3.getStart().atStartOfDay();
        Assertions.assertEquals(price3AtStartOfDay, qpp3.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp3.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price3.getValue(), qpp3.getPrice(),
                "Price for the second quantity must match the second price.");
    }

    @Test
    void priceStartEqualsMeasurmentStartTest() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final Price price = new Price("gas", LocalDate.of(2021, 3, 6),
                LocalDate.of(2021, 4, 25), priceValue1);
        long priceDays = measurement1.getStart().until(price.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        BigDecimal priceQuantity = measurement1.getValue().divide(BigDecimal.valueOf(priceDays), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(priceDays));
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(1, qppList.size(),
                "Distribution is needed, expecting more than one QPP");


        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(priceQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(price.getStart(), qpp1.getStart().toLocalDate(),
                "Price date for the second measurement must match the second measurement start date.");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");


    }

    @Test
    void priceOverTwoMeasurmentsTest() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final Measurement measurement2 = new Measurement(LocalDateTime.of(2021, 4, 15, 13, 23),
                LocalDateTime.of(2021, 5, 25, 15, 32), measurementValue,
                new User("Test Testov", "ref", Collections.emptyList(),1));

        final BigDecimal priceValue = new BigDecimal("1.50");


        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 5, 30), priceValue);


        BigDecimal firstQuantity = measurement1.getValue();
        BigDecimal secondQuantity = measurement1.getValue();


        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);

        final Collection<Measurement> measurements = new LinkedList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements, prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(measurement2.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice(),
                "Price for the first quantity must match the first price.");


    }

    @Test
    void twoPricesTwoMeasurmentsTest() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final Measurement measurement2 = new Measurement(LocalDateTime.of(2021, 4, 15, 13, 23),
                LocalDateTime.of(2021, 5, 25, 15, 32), measurementValue,
                new User("Test Testov", "ref", Collections.emptyList(),1));

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");


        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 4, 14), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 4, 15),
                LocalDate.of(2021, 5, 30), priceValue2);


        BigDecimal firstQuantity = measurement1.getValue();
        BigDecimal secondQuantity = measurement2.getValue();

        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final Collection<Measurement> measurements = new LinkedList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements, prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(price2.getStart(), qpp2.getStart().toLocalDate(),
                "Price date for the second measurement must match the second measurement start date.");
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(measurement2.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the first quantity must match the first price.");

    }
}