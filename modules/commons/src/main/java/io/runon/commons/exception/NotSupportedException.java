package io.runon.commons.exception;
/**
 * @author macle
 */
public class NotSupportedException extends RuntimeException{

    public NotSupportedException(){
        super();
    }

    public NotSupportedException(String message){
        super(message);
    }
}
