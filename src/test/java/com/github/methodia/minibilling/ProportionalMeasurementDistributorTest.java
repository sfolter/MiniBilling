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
        final BigDecimal actualPrice = singleQpp.getPrice().getValue();
        Assertions.assertEquals(expectedPrice, actualPrice,
                "The quantity price period price must match the single price provided.");

    }

    @Test
    void partialOverlapTwoPrices() {
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
        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays).divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
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
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");
        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the second quantity must match the second price.");

    }

    @Test
    void partialOverlapThreePrices() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);

        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final BigDecimal priceValue3 = new BigDecimal("4.00");
        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 10), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 3, 11),
                LocalDate.of(2021, 3, 20), priceValue2);
        final Price price3 = new Price("gas", LocalDate.of(2021, 3, 21),
                LocalDate.of(2021, 4, 30), priceValue3);
        long firstThreeDays = measurement1.getStart().until(price1.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long secondThreeDays = price2.getStart().atTime(00, 00, 00).until(price2.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long thirdThreeDays = price3.getStart().atTime(00, 00, 00).until(measurement1.getEnd(), ChronoUnit.DAYS);
        long measurementDays = firstThreeDays + secondThreeDays + thirdThreeDays;
        BigDecimal firstQuantity = BigDecimal.valueOf(firstThreeDays).divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        BigDecimal secondQuantity = BigDecimal.valueOf(secondThreeDays).divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        BigDecimal thirdQuantity = measurement1.getValue().subtract(firstQuantity).subtract(secondQuantity);
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
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");
        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for second half does not match");
        final LocalDateTime price2AtStartOfDay = price2.getStart().atStartOfDay();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the second quantity must match the second price.");
        final QuantityPricePeriod qpp3 = qppList.get(2);
        Assertions.assertEquals(thirdQuantity, qpp3.getQuantity(),
                "Distributed quantity for third half does not match");
        final LocalDateTime price3AtStartOfDay = price3.getStart().atStartOfDay();
        Assertions.assertEquals(price3AtStartOfDay, qpp3.getStart(),
                "Quantity period start must match the period start of the price.");
        Assertions.assertEquals(price3.getValue(), qpp3.getPrice().getValue(),
                "Price for the third quantity must match the third price.");
        Assertions.assertEquals(measurement1.getEnd(), qpp3.getEnd(),
                "Quantity period end must match the end of the measurement.");

    }

    @Test
    void EqualsMeasurementAndPricePeriodStart() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final BigDecimal priceValue = new BigDecimal("1.50");
        final Price price = new Price("gas", LocalDate.of(2021, 3, 6),
                LocalDate.of(2021, 4, 20), priceValue);
        long pricePeriod = measurement1.getStart().until(price.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long measurementDays = pricePeriod;
        BigDecimal priceQuantity = BigDecimal.valueOf(pricePeriod).divide(BigDecimal.valueOf(measurementDays), 0, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), prices);
        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(1, qppList.size(),
                "Expecting only one QPP");
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price.getValue(), qpp1.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(priceQuantity, qpp1.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");
    }

    @Test
    void TwoMeasurements() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final Measurement measurement2 = new Measurement(LocalDateTime.of(2021, 4, 15, 13, 23),
                LocalDateTime.of(2021, 5, 12, 15, 32), measurementValue,
                new User("Test Testovv", "ref", 1, Collections.emptyList()));
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 4, 14), priceValue1);
        final Price price2 = new Price("gas", LocalDate.of(2021, 4, 15),
                LocalDate.of(2021, 5, 20), priceValue2);
        long firstHalfDays = measurement1.getStart().until(price1.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long secondHalfDays = price2.getStart().atTime(00, 00, 00).until(measurement2.getEnd(), ChronoUnit.DAYS);
        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays).divide(BigDecimal.valueOf(firstHalfDays), RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        BigDecimal secondQuantity = BigDecimal.valueOf(secondHalfDays).divide(BigDecimal.valueOf(secondHalfDays), RoundingMode.HALF_UP)
                .multiply(measurement2.getValue());
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
                "Expecting only one QPP");
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");
        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(measurement2.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
    }

    @Test
    void OnePriceTwoMeasurements() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final Measurement measurement2 = new Measurement(LocalDateTime.of(2021, 4, 15, 13, 23),
                LocalDateTime.of(2021, 5, 12, 15, 32), measurementValue,
                new User("Test Testovv", "ref", 1, Collections.emptyList()));
        final BigDecimal priceValue1 = new BigDecimal("1.50");

        final Price price1 = new Price("gas", LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 5, 14), priceValue1);

        long firstHalfDays = measurement1.getStart().until(price1.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        long secondHalfDays = price1.getStart().atTime(00, 00, 00).until(measurement2.getEnd(), ChronoUnit.DAYS);
        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays).divide(BigDecimal.valueOf(firstHalfDays), RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        BigDecimal secondQuantity = BigDecimal.valueOf(secondHalfDays).divide(BigDecimal.valueOf(secondHalfDays), RoundingMode.HALF_UP)
                .multiply(measurement2.getValue());
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price1);

        final Collection<Measurement> measurements = new LinkedList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements, prices);
        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(2, qppList.size(),
                "Expecting only one QPP");
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");
        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(measurement2.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
    }

    @Test
    void EqualsStartAndEnd() {
        final BigDecimal measurementValue = new BigDecimal("200");
        final Measurement measurement1 = getMeasurement(measurementValue);
        final BigDecimal priceValue = new BigDecimal("1.50");
        final Price price = new Price("gas", LocalDate.of(2021, 3, 6),
                LocalDate.of(2021, 4, 14), priceValue);
        long firstHalfDays = measurement1.getStart().until(price.getEnd().atTime(23, 59, 59), ChronoUnit.DAYS);
        BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays).divide(BigDecimal.valueOf(firstHalfDays), RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        final ArrayList<Price> prices = new ArrayList<>();
        prices.add(price);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1), prices);
        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();
        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price.getStart(), qpp1.getStart().toLocalDate(),
                "Price period start must match quantity period start.");
        Assertions.assertEquals(price.getValue(), qpp1.getPrice().getValue(),
                "Price for the quantity must match the price.");
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity does not match");
        Assertions.assertEquals(price.getEnd(), qpp1.getEnd().toLocalDate(),
                "Quantity period end must match the end of the price.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");
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
                new User("Test Testov", "ref", 1, Collections.emptyList()));
        return measurement1;
    }

}