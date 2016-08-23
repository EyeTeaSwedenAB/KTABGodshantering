package com.peter.exceptions;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class ConnectionStartException extends Throwable{

    public ConnectionStartException(String message){
        super(message);
    }

}
