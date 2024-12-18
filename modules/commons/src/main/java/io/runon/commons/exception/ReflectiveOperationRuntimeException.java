

package io.runon.commons.exception;


/**
 * ReflectiveOperationException
 * @author macle
 */
public class ReflectiveOperationRuntimeException  extends RuntimeException{
    /**
     * 생성자
     * @param e exception
     */
    public ReflectiveOperationRuntimeException(ReflectiveOperationException e){
        super(e);
    }
}
