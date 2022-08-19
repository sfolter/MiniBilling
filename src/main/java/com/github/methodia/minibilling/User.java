package com.github.methodia.minibilling;

import java.util.List;

public record User(String name, String ref, List<Price> price, int priceListNumber) {

}
