package com.github.methodia.minibilling;

import java.util.List;

public interface MeasurementPriceDistributor {

    List<QuantityPricePeriod> distribute();
}
