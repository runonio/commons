

package io.runon.commons.exception;

import java.text.ParseException;

/**
 * ParseException Runtime 처리
 * @author macle
 */
public class ParseRuntimeException extends RuntimeException{

    /**
     * 생성자
     */
    public ParseRuntimeException(){
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public ParseRuntimeException(ParseException e){
        super(e);
    }

    /**
     * 생성자
     * @param message exception meesage
     */
    public ParseRuntimeException(String message){
        super(message);
    }


}
