package com.github.methodia.minibilling;

import java.util.List;

public interface MeasurementPriceDistributor {

    /**
     * @return list of the quantity price periods ordered in chronological order
     */
    List<QuantityPricePeriod> distribute();

}
