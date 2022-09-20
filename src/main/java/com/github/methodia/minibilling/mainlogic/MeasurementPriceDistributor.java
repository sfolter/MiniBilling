package com.github.methodia.minibilling.mainlogic;

import java.util.List;

public interface MeasurementPriceDistributor {

    List<QuantityPricePeriod> distribute();
}
