package io.runon.commons.exception;
/**
 * @author macle
 */
public class AlreadyException  extends RuntimeException{

    /**
     * 생성자
     */
    public AlreadyException(){
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public AlreadyException(ClassNotFoundException e){
        super(e);
    }

    /**
     * 생성자
     * @param message exception meesage
     */
    public AlreadyException(String message){
        super(message);
    }
}

