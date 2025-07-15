package io.runon.commons.exception;
/**
 * SocketException
 * @author macle
 */
public class InterruptedRuntimeException extends RuntimeException{

    public InterruptedRuntimeException(InterruptedException e) {
        super(e);
    }

}
