package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.Price;
import com.github.methodia.minibilling.entity.PriceList;
import com.github.methodia.minibilling.entity.User;
import com.github.methodia.minibilling.mainlogic.ProportionalMeasurementDistributor;
import com.github.methodia.minibilling.mainlogic.QuantityPricePeriod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



class ProportionalMeasurementDistributorTest {

    @Test
    void priceOverlapsMeasurementTest() {

        final BigDecimal measurementValue = new BigDecimal("200");
        final BigDecimal priceValue = new BigDecimal("1.50");
        final Price price = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 1, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue);
        final List<Price> prices = new ArrayList<>();
        prices.add(price);

        final Measurement measurement1 = getMeasurement(measurementValue, prices);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1));

        final Collection<QuantityPricePeriod> qppCollection = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(1, qppCollection.size(),
                "Expecting only one measurement because no distribution is needed.");

        final QuantityPricePeriod singleQpp = qppCollection.iterator().next();
        final ZonedDateTime qppStart = singleQpp.getStart();
        final ZonedDateTime qppEnd = singleQpp.getEnd();
        final ZonedDateTime measurement1Start = measurement1.getStart();
        final ZonedDateTime measurement1End = measurement1.getEnd();
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
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 3, 20, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue1);
        final Price price2 = new Price("gas", ZonedDateTime.of(2021, 3, 21, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 1, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue2);
        final List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final Measurement measurement1 = getMeasurement(measurementValue, prices);
        final int firstHalfDays = 14;
        final int secondHalfDays = 25;
        final int measurementDays = firstHalfDays + secondHalfDays;
        final BigDecimal firstQuantity = BigDecimal.valueOf(firstHalfDays)
                .divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        final BigDecimal secondQuantity = measurement1.getValue().subtract(firstQuantity);

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1));

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final ZonedDateTime price1End = price1.getEnd();
        final ZonedDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        final ZonedDateTime price2AtStartOfDay = price2.getStart();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the second quantity must match the second price.");

    }


    private static Measurement getMeasurement(final BigDecimal measurementValue, List<Price> prices) {
        final Measurement measurement1 = new Measurement(ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 14, 15, 32, 0, 0, ZoneId.of("GMT")), measurementValue,
                new User("Test Testov", "ref", new PriceList(1), prices));
        return measurement1;
    }

    private static Measurement getSecondMeasurement(final BigDecimal measurementValue, List<Price> prices) {
        final Measurement measurement2 = new Measurement(ZonedDateTime.of(2021, 4, 15, 13, 23, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 14, 15, 32, 0, 0, ZoneId.of("GMT")), measurementValue,
                new User("Test Testov", "ref", new PriceList(1), prices));
        return measurement2;
    }

    @Test
    void partialOverlapThreePrices() {

        final BigDecimal measurementValue = new BigDecimal("200");
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final BigDecimal priceValue3 = new BigDecimal("2.50");
        final Price price1 = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 3, 20, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue1);
        final Price price2 = new Price("gas", ZonedDateTime.of(2021, 3, 21, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 1, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue2);
        final Price price3 = new Price("gas", ZonedDateTime.of(2021, 4, 2, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 10, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue3);
        final List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);
        final Measurement measurement1 = getMeasurement(measurementValue, prices);
        final int firstThirdDays = 14;
        final int secondThirdDays = 12;
        final int threeThirdDays = 13;
        final int measurementDays = firstThirdDays + secondThirdDays + threeThirdDays;
        final BigDecimal firstQuantity = BigDecimal.valueOf(firstThirdDays)
                .divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());
        final BigDecimal secondQuantity = BigDecimal.valueOf(secondThirdDays)
                .divide(BigDecimal.valueOf(measurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement1.getValue());

        final BigDecimal thirdQuantity = measurement1.getValue().subtract(firstQuantity.add(secondQuantity));

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1));

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(3, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(firstQuantity, qpp1.getQuantity(),
                "Distributed quantity for first third does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");

        final ZonedDateTime price1End = price1.getEnd();
        final ZonedDateTime qpp1End = qpp1.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(secondQuantity, qpp2.getQuantity(),
                "Distributed quantity for second third does not match");
        final ZonedDateTime price2AtStartOfDay = price2.getStart();
        Assertions.assertEquals(price2AtStartOfDay, qpp2.getStart(),
                "Quantity period start must match the period start of the price.");


        final ZonedDateTime price2End = price2.getEnd();
        final ZonedDateTime qpp2End = qpp2.getEnd();
        Assertions.assertEquals(price2End, qpp2End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the second quantity must match the second price.");

        final QuantityPricePeriod qpp3 = qppList.get(2);
        Assertions.assertEquals(thirdQuantity, qpp3.getQuantity(),
                "Distributed quantity for last third does not match");
        final ZonedDateTime price3AtStartOfDay = price3.getStart();
        Assertions.assertEquals(price3AtStartOfDay, qpp3.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement1.getEnd(), qpp3.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price3.getValue(), qpp3.getPrice().getValue(),
                "Price for the third quantity must match the third price.");

    }

    @Test
    void partialOverlapTwoMeasurments() {

        final BigDecimal measurement1Value = new BigDecimal("200");
        final BigDecimal measurement2Value = new BigDecimal("100");
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 20, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue1);
        final Price price2 = new Price("gas", ZonedDateTime.of(2021, 4, 21, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 25, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue2);
        final List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final Measurement measurement1 = getMeasurement(measurement1Value, prices);
        final Measurement measurement2 = getSecondMeasurement(measurement1Value, prices);

        final int firstHalfDays = 5;
        final int secondHalfDays = 24;
        final int secondMeasurementDays = firstHalfDays + secondHalfDays;
        final BigDecimal firstQuantityForMeasurement2 = BigDecimal.valueOf(firstHalfDays)
                .divide(BigDecimal.valueOf(secondMeasurementDays), 1, RoundingMode.HALF_UP)
                .multiply(measurement2.getValue());
        final BigDecimal secondQuantityForMeasurement2 = measurement2.getValue().subtract(firstQuantityForMeasurement2);
        //        final ArrayList<Price> prices = new ArrayList<>();
        //        prices.add(price1);
        //        prices.add(price2);
        final ArrayList<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(3, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getValue(), qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");


        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(firstQuantityForMeasurement2, qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice().getValue(),
                "Price for the first quantity must match the first price.");


        final ZonedDateTime price1End = price1.getEnd();
        final ZonedDateTime qpp1End = qpp2.getEnd();
        Assertions.assertEquals(price1End, qpp1End,
                "Quantity period end must match the period end of the price");
        Assertions.assertEquals(price1.getValue(), qpp2.getPrice().getValue(),
                "Price for the first quantity must match the first price.");

        final QuantityPricePeriod qpp3 = qppList.get(2);
        Assertions.assertEquals(secondQuantityForMeasurement2, qpp3.getQuantity(),
                "Distributed quantity for first half does not match");
        final ZonedDateTime price2AtStartOfDay = price2.getStart();
        Assertions.assertEquals(price2AtStartOfDay, qpp3.getStart(),
                "Quantity period start must match the period start of the price.");

        Assertions.assertEquals(measurement2.getEnd(), qpp3.getEnd(),
                "Quantity period end must match the end of the measurement.");
        Assertions.assertEquals(price2.getValue(), qpp3.getPrice().getValue(),
                "Price for the second quantity must match the second price.");
    }

    @Test
    void priceStartOverlapsMeasurementStart() {

        final BigDecimal measurementValue = new BigDecimal("200");
        final BigDecimal priceValue = new BigDecimal("1.50");
        final Price price = new Price("gas", ZonedDateTime.of(2021, 3, 6, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 1, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue);
        final List<Price> prices = new ArrayList<>();
        prices.add(price);
        final Measurement measurement1 = getMeasurement(measurementValue, prices);

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                Collections.singleton(measurement1));

        final Collection<QuantityPricePeriod> qppCollection = proportionalMeasurementDistributor.distribute();
        Assertions.assertEquals(1, qppCollection.size(),
                "Expecting only one measurement because no distribution is needed.");

        final QuantityPricePeriod singleQpp = qppCollection.iterator().next();
        final ZonedDateTime qppStart = singleQpp.getStart();
        final ZonedDateTime qppEnd = singleQpp.getEnd();
        final ZonedDateTime measurement1Start = measurement1.getStart();
        final ZonedDateTime measurement1End = measurement1.getEnd();
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
    void twoPricesOverlapTwoMeasurements() {

        final BigDecimal measurementValue1 = new BigDecimal("200");
        final BigDecimal measurementValue2 = new BigDecimal("100");
        final BigDecimal priceValue1 = new BigDecimal("1.50");
        final BigDecimal priceValue2 = new BigDecimal("3.50");
        final Price price1 = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 14, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue1);
        final Price price2 = new Price("gas", ZonedDateTime.of(2021, 4, 15, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 25, 23, 59, 59, 0, ZoneId.of("GMT")), priceValue2);

        final List<Price> prices = new ArrayList<>();
        prices.add(price1);
        prices.add(price2);
        final Measurement measurement1 = getMeasurement(measurementValue1, prices);
        final Measurement measurement2 = getSecondMeasurement(measurementValue2, prices);
        final ArrayList<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements);

        final List<QuantityPricePeriod> qppList = proportionalMeasurementDistributor.distribute();

        Assertions.assertEquals(2, qppList.size(),
                "Distribution is needed, expecting more than one QPP");

        final QuantityPricePeriod qpp1 = qppList.get(0);
        Assertions.assertEquals(measurement1.getValue(), qpp1.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement1.getStart(), qpp1.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price1.getValue(), qpp1.getPrice().getValue(),
                "Price for the first quantity must match the first price.");
        Assertions.assertEquals(measurement1.getEnd(), qpp1.getEnd(),
                "Quantity period end must match the end of the measurement.");


        final QuantityPricePeriod qpp2 = qppList.get(1);
        Assertions.assertEquals(measurement2.getValue(), qpp2.getQuantity(),
                "Distributed quantity for first half does not match");
        Assertions.assertEquals(measurement2.getStart(), qpp2.getStart(),
                "Measurement period start must match quantity period start.");
        Assertions.assertEquals(price2.getValue(), qpp2.getPrice().getValue(),
                "Price for the first quantity must match the first price.");
        Assertions.assertEquals(measurement2.getEnd(), qpp2.getEnd(),
                "Quantity period end must match the end of the measurement.");
    }
}