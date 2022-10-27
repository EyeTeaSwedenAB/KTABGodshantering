package com.peter.exceptions;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class ConnectionCloseException extends Throwable {

    public ConnectionCloseException(String message){
        super(message);
    }
}
