package com.peter.exceptions;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class DatabaseHandlerNotInitializedException extends RuntimeException {

    public DatabaseHandlerNotInitializedException(String message) {
        super(message);
    }
}
