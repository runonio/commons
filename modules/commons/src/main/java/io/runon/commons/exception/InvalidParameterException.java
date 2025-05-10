package io.runon.commons.exception;
/**
 * @author macle
 */
public class InvalidParameterException extends RuntimeException{

    /**
     * 생성자
     */
    public InvalidParameterException(){
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public InvalidParameterException(Exception e){
        super(e);
    }

    /**
     * 생성자
     * @param message exception meesage
     */
    public InvalidParameterException(String message){
        super(message);
    }
}
