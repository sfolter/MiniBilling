package com.github.methodia.minibilling;

import java.util.List;

public interface Measurements {
    List<Measurement> generate(User user, List<Reading> readings);
}
