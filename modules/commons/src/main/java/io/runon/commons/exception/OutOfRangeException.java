

package io.runon.commons.exception;

/**
 * 범위를 벗어나는 예외
 * @author macle
 */
public class OutOfRangeException extends RuntimeException {

    /**
     * 생성자
     */
    public OutOfRangeException() {
        super();
    }

    /**
     * 생성자
     *
     * @param e 예외
     */
    public OutOfRangeException(Exception e) {
        super(e);
    }

    /**
     * 생성자
     *
     * @param message exception meesage
     */
    public OutOfRangeException(String message) {
        super(message);
    }
}
