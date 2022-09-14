package com.github.methodia.minibilling;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public interface ReadingsReader {

    Collection<Reading> read() throws SQLException, ClassNotFoundException;
}
