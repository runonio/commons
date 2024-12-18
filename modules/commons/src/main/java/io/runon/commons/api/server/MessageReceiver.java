
package io.runon.commons.api.server;
/**
 * 메시지 전달 받기.
 * 메시지를 정해진 크기 만큼 받고
 * 메시지 종료 여부를 전달 받을 떄 활용
 *
 * 대량 메시지를 전달 받기 위해 개발됨
 * @author macle
 */
public interface MessageReceiver {
	

	/**
	 * 메시지 받기
	 * @param message String receive message
	 */
	void receive(String message);
	
	/**
	 * 메시지받기 종료
	 */
	void end();
}
