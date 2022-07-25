package com.github.methodia.minibilling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 25.07.2022
 * Methodia Inc.
 */
public class ClientProductReadingPair {
    private Map<String, List<Reading>> clientRefToReading = new HashMap<>();
    private Map<String, List<Reading>> productKeyToReading = new HashMap<>();

    public ClientProductReadingPair(Map<String, List<Reading>> clientRefToReading, Map<String, List<Reading>> productKeyToReading) {
        this.clientRefToReading = clientRefToReading;
        this.productKeyToReading = productKeyToReading;
    }

    public Map<String, List<Reading>> getClientRefToReading() {
        return clientRefToReading;
    }

    public Map<String, List<Reading>> getProductKeyToReading() {
        return productKeyToReading;
    }
}
