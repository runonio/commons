
package io.runon.install.exception;

import java.io.IOException;

/**
 * IOException
 * @author macle
 */
public class IORuntimeException extends RuntimeException{

    /**
     * 생성자
     */
    public IORuntimeException(){
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public IORuntimeException(IOException e){
        super(e);
    }

    /**
     * 생성자
     * @param message exception meesage
     */
    public IORuntimeException(String message){
        super(message);
    }
}
