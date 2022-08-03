package com.github.methodia.minibilling;

import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface MeasurementPriceDistributor {

    /**
     * @return list of the quantity price periods ordered in chronological order
     */
    List<QuantityPricePeriod> distribute();

}
