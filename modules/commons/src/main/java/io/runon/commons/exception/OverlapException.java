

package io.runon.commons.exception;

/**
 * 중복 예외
 * @author macle
 */
public class OverlapException extends RuntimeException{

    /**
     * 생성자
     */
    public OverlapException(){
        super();
    }


    /**
     * 생성자
     * @param e 예외
     */
    public OverlapException(Exception e){
        super(e);
    }
    
    /**
     * 생성자
     * @param message exception meesage
     */
    public OverlapException(String message){
        super(message);
    }
}
