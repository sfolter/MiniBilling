package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.entity.Tax;

import java.util.List;

public interface TaxGenerator {

    List<Tax> generate();
}
