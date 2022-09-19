package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.Tax;

import java.util.List;

public interface TaxGenerator {

    List<Tax> generate();
}
