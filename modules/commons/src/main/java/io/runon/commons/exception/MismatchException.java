package io.runon.commons.exception;
/**
 * IOException
 * @author macle
 */
public class MismatchException extends RuntimeException {

    /**
     * 생성자
     */
    public MismatchException() {
        super();
    }

    /**
     * 생성자
     * @param e 예외
     */
    public MismatchException(Exception e) {
        super(e);
    }

    /**
     * 생성자
     * @param message exception message
     */
    public MismatchException(String message) {
        super(message);
    }
}