package com.github.methodia.minibilling;

import java.util.HashMap;
import java.util.List;


public interface ReadingReaderInterface {
    HashMap<String,List<Reading>> read();
}
