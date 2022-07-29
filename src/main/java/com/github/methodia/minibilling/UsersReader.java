package com.github.methodia.minibilling;

import java.util.Collection;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface UsersReader {

    Collection<Price> read();

}
