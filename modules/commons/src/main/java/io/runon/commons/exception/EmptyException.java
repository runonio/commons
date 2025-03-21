

package io.runon.commons.exception;
/**
 * @author macle
 */
public class EmptyException extends RuntimeException{

	/**
	 * 생성자
	 */
	public EmptyException(){
		super();
	}

	/**
	 * 생성자
	 * @param e 예외
	 */
	public EmptyException(Exception e){
		super(e);
	}

	/**
	 * 생성자
	 * @param message exception meesage
	 */
	public EmptyException(String message){
		super(message);
	}
}