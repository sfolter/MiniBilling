package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProportionalMeasurementDistributor implements MeasurementPriceDistributor {

    private final Collection<Measurement> measurements;

    public ProportionalMeasurementDistributor(final Collection<Measurement> measurements) {
        this.measurements = measurements;

    }


    @Override
    public List<QuantityPricePeriod> distribute() {
        /*
        ще обходим межърмънтите
        за всеки межърмънт:
        1. ще вземем цените, които по някаквъ начин припокриват межърмънта
        2. определяме отделните периоди на които се разделя межърмънта
            - обхождаме цените
            - за всяка цена  взимаме крайната дата в зависимост от това дали влиза в периода на межърмънта
            - запазваме периода (QuantityPricePeriod) като гледаме само крайната дата на цената:
                - ако крайната дата на цената влиза в периода на межърмънта,
                    тогава правим QPP, който е с начало последната запазена дата и край - крайната дата на текущата цена
                    за цена на QPP взимаме текущата цена
                    за количеството
                        - определяме разликата между двете дати
                        - делим разликата на общия брой дни
                        - умножаваме по общото количество (на текущия межърмънт)
                 - ако крайната дата на цената НЕ влиза в периода на межърмънта или е равна на крайната дата на межърмънта,
                    тогава правим QPP,  който е с начало последната запазена дата и край крайната дата на межърмънта
                    за цена взимаме текущата цена
                    за количество
                        - взимаме сумираното количество до момента (ще си пазим събраното количество в променлива)
                        - от количеството на межърмънта изваждаме сумираното количество
        */

        final ArrayList<QuantityPricePeriod> quantityPricePeriods = new ArrayList<>();
        for (final Measurement measurement : measurements) {

            final PriceList prices = measurement.getUser().getPrices();

            final List<Price> pricesForMeasurement = filterPricesByMeasurementIntersection(
                    prices, measurement);
            LocalDateTime lastDateTime = measurement.getStart();
            BigDecimal currentQuantitySum = BigDecimal.ZERO;
            for (final Price price : pricesForMeasurement) {
                final LocalDateTime priceEnd = LocalDateTime.from(price.getEnd());
                final LocalDateTime measurementEnd = measurement.getEnd();
                final LocalDateTime qppStart = lastDateTime;
                final long measurementDays = measurement.getStart().until(measurement.getEnd(), ChronoUnit.DAYS);

                if (0 <= priceEnd.compareTo(measurementEnd)) {
                    final LocalDateTime qppEnd = measurement.getEnd();
                    final BigDecimal qppQuantity = measurement.getValue().subtract(currentQuantitySum);

                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(qppStart, qppEnd, price,
                            qppQuantity, measurement.getUser());
                    quantityPricePeriods.add(quantityPricePeriod);
                } else {
                    final LocalDateTime qppEnd =
                            LocalDateTime.from(price.getEnd()
                                    .withZoneSameInstant(ZoneId.of("Z")));
                    final long qppPeriodDays = lastDateTime.until(qppEnd, ChronoUnit.DAYS);
                    final BigDecimal qppQuantity = BigDecimal.valueOf(qppPeriodDays)
                            .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP)
                            .multiply(measurement.getValue());
                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(lastDateTime, qppEnd,
                            price, qppQuantity, measurement.getUser());
                    quantityPricePeriods.add(quantityPricePeriod);
                    lastDateTime = LocalDateTime.from(qppEnd.plusSeconds(1));
                    currentQuantitySum = currentQuantitySum.add(qppQuantity);
                }

            }
        }

        return quantityPricePeriods;
    }

    private static List<Price> filterPricesByMeasurementIntersection(final PriceList prices,
                                                                     final Measurement measurement) {

        final LocalDateTime measurementStart = measurement.getStart();
        final LocalDateTime measurementEnd = measurement.getEnd();

        final ArrayList<Price> filteredPrices = new ArrayList<>();
        final List<Price> priceList = prices.getPriceList();
        for (final Price price : priceList) {
            final LocalDateTime priceStart = LocalDateTime.from(price.getStart().minusHours(3));
            final LocalDateTime priceEnd = LocalDateTime.from(price.getEnd().minusHours(3));

            if (measurementStart.isBefore(priceEnd) && measurementEnd.isAfter(
                    priceStart)) {
                filteredPrices.add(price);
            }
        }
        return filteredPrices;
    }

}


