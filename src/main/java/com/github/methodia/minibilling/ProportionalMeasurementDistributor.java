package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProportionalMeasurementDistributor implements MeasurementPriceDistributor {

    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public ProportionalMeasurementDistributor(final Collection<Measurement> measurements, final Collection<Price> prices) {
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
        for (final Measurement measurement : measurements) {
            final List<Price> pricesForMeasurement = filterPricesByMeasurementIntersection(measurement);


            LocalDateTime lastDateTime = measurement.start();

            BigDecimal currentQuantitySum = BigDecimal.ZERO;
            for (final Price price : pricesForMeasurement) {
                final LocalDate priceEnd = price.getEndDate();
                final LocalDate measurementEnd = measurement.end().toLocalDate();

                final LocalDateTime qppStart = lastDateTime;

                final String qppProduct = price.getProduct();

                final BigDecimal qppPrice = price.getPrice();
                final long measurementDays = measurement.start().until(measurement.end(), ChronoUnit.DAYS);

                if (0 <= priceEnd.compareTo(measurementEnd)) {
                    final LocalDateTime qppEnd = measurement.end();
                    final BigDecimal qppQuantity = measurement.value().subtract(currentQuantitySum);
                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(qppStart, qppEnd, qppPrice,
                            qppProduct,
                            qppQuantity);
                    quantityPricePeriods.add(quantityPricePeriod);
                } else {
                    final LocalDateTime qppEnd = LocalDateTime.from(price.getEndDate().atTime(23, 59, 59)
                            .atZone(ZoneId.of("Europe/Sofia")).withZoneSameInstant(ZoneId.of("Z")));

                    final long qppPeriodDays = lastDateTime.until(qppEnd, ChronoUnit.DAYS);
                    final BigDecimal qppQuantity = BigDecimal.valueOf(qppPeriodDays)
                            .divide(BigDecimal.valueOf(measurementDays), 3, RoundingMode.HALF_UP)
                            .multiply(measurement.value());
                    final QuantityPricePeriod quantityPricePeriod = new QuantityPricePeriod(lastDateTime, qppEnd,
                            qppPrice, qppProduct, qppQuantity);
                    quantityPricePeriods.add(quantityPricePeriod);
                    lastDateTime = qppEnd.plusSeconds(1);
                    currentQuantitySum = currentQuantitySum.add(qppQuantity);
                }

            }
        }

        return quantityPricePeriods;
    }

    private List<Price> filterPricesByMeasurementIntersection(final Measurement measurement) {
        final ArrayList<Price> filteredPrices = new ArrayList<>();
        final LocalDateTime measurementStart = measurement.start();
        final LocalDateTime measurementEnd = measurement.end();
        for (final Price price : prices) {
            final LocalDateTime priceStart = price.getStartDate().atTime(0, 0);
            final LocalDateTime priceEnd = price.getEndDate().atTime(0, 0);
            if (measurementStart.isBefore(priceEnd.plusDays(1)) && measurementEnd.isAfter(
                    priceStart.plusDays(1))) {
                filteredPrices.add(price);
            }
        }
        return filteredPrices;
    }

}

