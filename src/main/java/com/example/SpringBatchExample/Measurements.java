package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Reading;
import com.example.SpringBatchExample.models.User;

import java.util.List;

public interface Measurements {

    List<Measurement> generate(User user, List<Reading> readings);
}
