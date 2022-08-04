package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class ProportionalMeasurementDistributor implements MeasurementPriceDistributor {

    private Collection<Measurement> measurements;
    private Collection<Price> prices;

    public ProportionalMeasurementDistributor(Collection<Measurement> measurements, Collection<Price> prices) {
        this.measurements = measurements;
        this.prices = prices;
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
        for (Measurement measurement : measurements) {
            final List<Price> pricesForMeasurement = filterPricesByMeasurementIntersection(measurement);
            LocalDateTime lastDateTime = measurement.getStart();
            BigDecimal currentQuantitySum = BigDecimal.ZERO;
            for (Price price : pricesForMeasurement) {
                final LocalDate priceEnd = price.getEnd();
                final LocalDate measurementEnd = measurement.getEnd().toLocalDate();
                final LocalDateTime qppStart = lastDateTime;
                final Price qppPrice = price;
                final long measurementDays = measurement.getStart().until(measurement.getEnd(), ChronoUnit.DAYS);

                if (priceEnd.compareTo(measurementEnd) >= 0) {
                    final LocalDateTime qppEnd = measurement.getEnd();
                    final BigDecimal qppQuantity = measurement.getValue().subtract(currentQuantitySum);

                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(qppStart, qppEnd, qppPrice,
                            qppQuantity);
                    quantityPricePeriods.add(quantityPricePeriod);
                } else {
                    final LocalDateTime qppEnd = price.getEnd().atTime(23, 59, 59);
                    final long qppPeriodDays = lastDateTime.until(qppEnd, ChronoUnit.DAYS);
                    BigDecimal qppQuantity = BigDecimal.valueOf(qppPeriodDays).divide(BigDecimal.valueOf(measurementDays),1, RoundingMode.HALF_UP)
                            .multiply(measurement.getValue());
                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(lastDateTime, qppEnd,
                            qppPrice, qppQuantity);
                    quantityPricePeriods.add(quantityPricePeriod);
                    lastDateTime = qppEnd.plusSeconds(1);
                    currentQuantitySum = currentQuantitySum.add(qppQuantity);
                }

            }
        }

        return quantityPricePeriods;
    }

    private List<Price> filterPricesByMeasurementIntersection(Measurement measurement) {
        final ArrayList<Price> filteredPrices = new ArrayList<>();
        final LocalDateTime measurementStart = measurement.getStart();
        final LocalDateTime measurementEnd = measurement.getEnd();
        for (Price price : prices) {
            final LocalDateTime priceStart = price.getStart().atTime(0, 0);
            final LocalDateTime priceEnd = price.getEnd().atTime(0, 0);
            if (measurementStart.isBefore(priceEnd.plusDays(1)) && measurementEnd.isAfter(
                    priceStart.plusDays(1))) {
                filteredPrices.add(price);
            }
        }
        return filteredPrices;
    }

}


