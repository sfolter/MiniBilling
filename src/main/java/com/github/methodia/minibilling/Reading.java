package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Reading(LocalDateTime time, BigDecimal value, User user) {

}
