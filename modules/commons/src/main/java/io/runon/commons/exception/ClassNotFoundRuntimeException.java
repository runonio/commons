
package io.runon.commons.exception;

/**
 * ClassNotFoundException
 * @author macle
 */
public class ClassNotFoundRuntimeException extends RuntimeException{

    /**
     * 생성자
     */
    public ClassNotFoundRuntimeException(){
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public ClassNotFoundRuntimeException(ClassNotFoundException e){
        super(e);
    }

    /**
     * 생성자
     * @param message exception meesage
     */
    public ClassNotFoundRuntimeException(String message){
        super(message);
    }
}
