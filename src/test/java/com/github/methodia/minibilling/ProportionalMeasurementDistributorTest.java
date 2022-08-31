package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class ProportionalMeasurementDistributorTest {

    @Test
    void priceOverlapsMeasurementTest() {
        final BigDecimal measurementValue = new BigDecimal("200");

        final BigDecimal priceValue = new BigDecimal("1.4");
        final Price price = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 5, 1), priceValue);

        List<Price> prices = new ArrayList<>();
        prices.add(price);

        final Measurement measurement1 = getMeasurement1(measurementValue, prices);

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1),prices);

        final List<QuantityPricePeriod> qppCollection = proportionalMeasurementDistributor.distribute();
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
        final BigDecimal actualPrice = singleQpp.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice,
                "The quantity price period price must match the single price provided.");

    }

    @Test
    void partialOverlapTwoPrices() {

        final BigDecimal measurementValue = new BigDecimal("200");

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");

        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 20), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 21),
                LocalDate.of(2021, 5, 1), priceValue2);

        List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);

        final Measurement measurement1 = getMeasurement1(measurementValue, prices);

        int firstHalfDays = 14;
        int secondHalfDays = 25;
        int measurementDays = firstHalfDays + secondHalfDays;

        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays)
                .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP).multiply(measurementValue);
        BigDecimal secondQuantity = new BigDecimal(String.valueOf(measurement1.getValue())).subtract(firstQuantity);


        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1),prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final LocalDateTime price1End = price1.getEnd().atTime(21, 59, 59);
        final LocalDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart().plusHours(2),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the second quantity must match the second price.");

    }

    @Test
    void partialOverlapThreePrices() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final BigDecimal priceValue3 = new BigDecimal("4.00");

        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 10), priceValue1);

        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 11),
                LocalDate.of(2021, 3, 18), priceValue2);

        final Price price3 = new Price("gas", LocalDate.of(2021, 3, 19),
                LocalDate.of(2021, 4, 30), priceValue3);

        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);

        final Measurement measurement1 = getMeasurement1(measurementValue, prices);

        long OneThirdDays = measurement1.getStart().toLocalDate().until(price1.getEnd(), ChronoUnit.DAYS);
        long TwoThirdDays = price2.getStart().until(price2.getEnd(), ChronoUnit.DAYS);
        long measurementDays = 39;

        BigDecimal firstQuantity = BigDecimal.valueOf(OneThirdDays)
                .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP).multiply(measurementValue);
        BigDecimal secondQuantity = BigDecimal.valueOf(TwoThirdDays)
                .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        BigDecimal thirdQuantity = measurement1.getValue().subtract((firstQuantity.add(secondQuantity)));

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1),prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(3, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for the first one third  does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final LocalDateTime price1End = price1.getEnd().atTime(21, 59, 59);
        final LocalDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for the second one third does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay().minusHours(2);
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(price2.getEnd(), qpp2.getEnd().toLocalDate(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the second quantity must match the second price.");

        final QuantityPricePeriod qpp3 = qppList.get(2);
        Assertions.assertEquals(thirdQuantity, qpp3.getQuantity(),
                "Distributed quantity for the third one third does not match");
        final LocalDateTime price3AtStartOfDay = price3.getStart().atStartOfDay().minusHours(2);
        Assertions.assertEquals(price3AtStartOfDay, qpp3.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd().toLocalDate(), qpp3.getEnd().toLocalDate(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price3.getValue(), qpp3.getPrice(),
                "Price for the second quantity must match the second price.");

    }

    @Test
    void TwoPricesCoveringTheMeasurement() {

        final BigDecimal measurementValue = new BigDecimal("200");

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 20),
                LocalDate.of(2021, 3, 30), priceValue1);

        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 31),
                LocalDate.of(2021, 4, 14), priceValue2);

        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);

        final Measurement measurement1 = getMeasurement1(measurementValue, prices);

        long firstHalfDays = measurement1.getStart().toLocalDate().until(price1.getEnd(), ChronoUnit.DAYS);
        long measurementDays = 39;

        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays)
                .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP).multiply(measurementValue);
        BigDecimal secondQuantity = new BigDecimal(String.valueOf(measurement1.getValue())).subtract(firstQuantity);

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1),prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final LocalDateTime price1End = price1.getEnd().atTime(20, 59, 59);
        final LocalDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for second half does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay().minusHours(3);
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice(),
                "Price for the second quantity must match the second price.");
    }

    @Test
    void partialOverlapTwoMeasurementsAndTwoPrices() {

        final BigDecimal measurementValue1 = new BigDecimal("200");
        final BigDecimal measurementValue2 = new BigDecimal("300");

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");

        final Price price1 = new Price("gas", LocalDate.of(2021, 4, 20),
                LocalDate.of(2021, 5, 30), priceValue1);

        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 31),
                LocalDate.of(2021, 4, 14), priceValue2);

        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final Measurement measurement1 = getMeasurement1(measurementValue1, prices);
        final Measurement measurement2 = getMeasurement2(measurementValue2, prices);

        long firstHalfOfSecondMeasurement = measurement2.getStart().toLocalDate()
                .until(price1.getEnd(), ChronoUnit.DAYS);
        long measurementDays = 55;

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);

        BigDecimal firstQuantityForMeasurement2 = BigDecimal.valueOf(firstHalfOfSecondMeasurement)
                .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP)
                .multiply(measurement2.getValue());

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements,prices);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurementValue1, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price2.getValue(), qpp1.getPrice(),
                "Price for the first quantity must match the first price.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(firstQuantityForMeasurement2, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice(),
                "Price for the first quantity must match the first price.");

        final LocalDateTime price1End = price1.getEnd().atTime(20, 59, 59);
        final LocalDateTime qpp1End = qpp2.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice(),
                "Price for the first quantity must match the first price.");

    }


    @Test
    void testLocalDateCompareTo() {

        final LocalDate priceEndDate = LocalDate.of(2021, 3, 20);
        final LocalDate measurementEndDate = LocalDate.of(2021, 3, 21);
        System.out.println(priceEndDate.compareTo(measurementEndDate));
    }

    private Measurement getMeasurement1(BigDecimal measurementValue, List<Price> prices) {
        return new Measurement(LocalDateTime.of(2021, 3, 6, 13, 23),
                LocalDateTime.of(2021, 4, 14, 15, 32), measurementValue,
                new User("Test Testov", "ref", 1, prices,"EUR"));
    }

    private static Measurement getMeasurement2(BigDecimal measurementValue, List<Price> prices) {
        return new Measurement(LocalDateTime.of(2021, 4, 15, 13, 23),
                LocalDateTime.of(2021, 6, 10, 10, 32), measurementValue,
                new User("Test Testov", "ref", 2, prices,"EUR"));
    }



}
