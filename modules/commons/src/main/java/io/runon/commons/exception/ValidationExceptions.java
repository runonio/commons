package io.runon.commons.exception;
/**
 * @author macle
 */
public class ValidationExceptions  extends RuntimeException{


    public ValidationExceptions(Exception e){
        super(e);
    }

    public ValidationExceptions(){
        super();
    }

    public ValidationExceptions(String message){
        super(message);
    }
}
