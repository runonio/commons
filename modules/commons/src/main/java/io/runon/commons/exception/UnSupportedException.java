package io.runon.commons.exception;
/**
 * @author macle
 */
public class UnSupportedException extends RuntimeException{

    public UnSupportedException(){
        super();
    }

    public UnSupportedException(String message){
        super(message);
    }
}
