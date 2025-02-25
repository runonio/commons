
package io.runon.commons.api;

import io.runon.commons.api.communication.SendToReceive;
import io.runon.commons.callback.ObjCallback;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
/**
 * api 통신
 *
 * @author macle
 */
@Slf4j
public class ApiCommunication extends Thread{

	public static final char DEFAULT_PACKAGE = 'D';
	public static final char CUSTOM_PACKAGE = 'C';

	private final SendToReceive sendToReceive;
	private boolean flag = true;
	
	private final String defaultPackageName;
	
	private final long createTime;
	
	private ObjCallback endCallback = null;
	
	private ExceptionHandler exceptionHandler = null;

	private int maxLogLength = 150;


	/**
	 * 생성자
	 * @param defaultPackageName string default package name
	 * @param socket Socket
	 * @throws IOException IOException
	 */
	public ApiCommunication(String defaultPackageName, Socket socket) throws  IOException {
		sendToReceive = new SendToReceive(socket);
		createTime = System.currentTimeMillis();
		
		this.defaultPackageName = defaultPackageName;
	
	}
	
	/**
	 * 로그 최대문자 길이 설정
	 * @param maxLogLength int
	 */
	public void setMaxLogLength(int maxLogLength) {
		this.maxLogLength = maxLogLength;
	}



	/**
	 * 생성 time 얻기
	 * @return long time
	 */
	public long getCreateTime() {
		return createTime;
	}
	
	
	/**
	 * 종료 핸들러
	 * @param endCallback ObjCallback
	 */
	public void setEndCallback(ObjCallback endCallback) {
		this.endCallback = endCallback;
	}

	/**
	 * 예외 핸들러설정
	 * @param exceptionHandler ExceptionHandler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	@Override
	public void run() {
		
		try {
			while(flag){
				
				String message  = sendToReceive.receive();
				if(message == null){
					flag = false;
					disConnect();
					break;
				}
				
				readMessage(message);
				
			}
		}catch(Exception e) {
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		try{
			if(endCallback != null){
				endCallback.callback(this);
			}
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		
	}

	/**
	 * message read
	 * @param message String message
	 */
	private void readMessage(String message) {

		if(message.length() > maxLogLength){
			log.debug("readMessage: " + message.substring(0 , maxLogLength) + ".. +" + message.length() + "characters.");
		} else {
			log.debug("readMessage: " + message);
		}


		char packageType = message.charAt(0);
		message = message.substring(1);
		
		String className ;
		String messageCode ;
		
		if(packageType == DEFAULT_PACKAGE){
			
			int idx = message.indexOf(",");
			messageCode = message.substring(0, idx);
			className =  defaultPackageName +"."+messageCode;
			message = message.substring(idx+1);
		}else{
			
			int idx = message.indexOf(",");
			String packageName = message.substring(0, idx);
			
			int next = idx+1;
			
			idx = message.indexOf(",", next);
			messageCode = message.substring(next, idx);
			className =  packageName +"."+messageCode;
			message = message.substring(idx+1);
		}	
			
		try {
			Class<?> apiMessageClass = Class.forName(className);
			ApiMessage apiMessage = (ApiMessage)apiMessageClass.newInstance();
			apiMessage.setCommunication(this);
			apiMessage.receive(message);
		} catch (Exception e1) {
			sendMessage(ExceptionUtil.getStackTrace(e1));
			ExceptionUtil.exception(e1, log, exceptionHandler);

		}

	}
	
	/**
	 * send message
	 * null 이나 빈값이 들어오면 전달하지 않는다.
	 * @param message String sendMessage
	 * @return boolean message send success fail
	 */
	public boolean sendMessage(String message){
		if(message == null){
			return false;
		}

		if(!sendToReceive.isConnect()){
			log.error("message send Fail(Not Connected) : " + message);
			return false;
		}

		log.debug(getSendMessageLog(message, maxLogLength));
		return sendToReceive.send(message);
	}

	/**
	 * send message log 얻기
	 * @param message String sendMessage
	 * @param maxLogLength int maxLogLength
	 * @return String SendMessageLog
	 */
	public static String getSendMessageLog(String message, int maxLogLength){
		String log ;
		if(message.length() > maxLogLength){
			log = "sendMessage: " + message.substring(0 , maxLogLength) + ".. +" + message.length() + "characters.";
		}else{
			log = message;
		}

		return log;
	}

	/**
	 * connect check
	 * @return boolean connect check flag
	 */
	public boolean isConnect(){
		return sendToReceive.isConnect();
	}

	/**
	 * 연결종료
	 */
	public void disConnect() {
		flag = false;
		sendToReceive.disConnect();
	}

	/**
	 * 마지막 연결 시간 얻기
	 * @return long (time)
	 */
	public long getLastConnectTime() {
		return sendToReceive.getLastConnectTime();
	}

}