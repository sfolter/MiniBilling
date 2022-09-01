package com.github.methodia.minibilling;

import java.util.List;

public record User(String name, String ref, int numberPricingList, List<Price> price, String currency) {

}
