

package io.runon.commons.apis.socket;

/**
 * api 메시지
 * 이클래스의 구현체로 api 이벤트를 작성
 *
 * @author macle
 */
public abstract class ApiMessage {
	
	protected ApiCommunication communication;
	
	/**
	 * ApiCommunication 설정
	 * @param apiCommunication ApiCommunication
	 */
	public void setCommunication(ApiCommunication apiCommunication){
		this.communication = apiCommunication;
	}
		
	/**
	 * send message
	 * @param message String sendMessage
	 */
	public void sendMessage(String message) {
		communication.sendMessage(message);
	}
	
	/**
	 * receive message
	 * @param message String receiveMessage
	 */
	public abstract void receive(String message);
	
	
	
}