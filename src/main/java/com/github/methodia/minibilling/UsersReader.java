package com.github.methodia.minibilling;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UsersReader {

    Map<String, User> read() throws SQLException, ClassNotFoundException;
}
