
package io.runon.commons.apis.socket.communication;

import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 문자열 푸시 
 * 대용량 메시지 전송을 위해 개발함
 * @author macle
 */
@Slf4j
public class StringPush {

	private final String hostAddress;
	private final int port;
	
	private Socket socket;			
	private OutputStreamWriter send;

	private ExceptionHandler exceptionHandler = null;


	/**
	 * 생성자
	 * @param socket Socket
	 * @throws IOException IOException
	 */
	public StringPush(Socket socket) throws IOException{
		this.socket = socket; 
		send = new OutputStreamWriter(socket.getOutputStream(), CommunicationDefault.CHAR_SET);
		this.hostAddress = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
		
	}
	
	/**
	 * 생성자
	 * @param hostAddress String hostAddress
	 * @param port int port
	 */
	public StringPush(String hostAddress, int port){
		this.hostAddress = hostAddress;
		this.port = port;
	}

	/**
	 * 예외 핸들러 설정
	 * @param exceptionHandler ExceptionHandler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * 서버와 연결한다.
	 * 문자열 전송이 완료되면 반드시 disconnet() 메소드를 호출해야 한다.
	 * @return boolean is connect
	 */
	public boolean connect(){
		if(socket == null || socket.isClosed()){
			try{
				socket = new Socket(hostAddress, port);
				send =  new OutputStreamWriter(socket.getOutputStream(), CommunicationDefault.CHAR_SET);
			}catch(Exception e){
				ExceptionUtil.exception(e, log, exceptionHandler);
				return false;
			}		
		}

		return true;

	}
	
	/**
	 * 연결을 해제한다. 다시 연결할때는 connect()메소드를 호출한다.
	 */
	public void disConnect(){
		
		try{
			if(send != null ){
				send.close();	
			}
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		send = null;
		try{
			if(socket != null && !socket.isClosed()){
				socket.close();		
			
			}
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		socket = null;
	}
	
	/**
	 * 서버에 문자열 형태의 메시지를 전송한다.
	 * @param msg String send message
	 * @return boolean send success flag
	 */
	public boolean sendMessage(String msg){	
		try{
			send.write(msg);
			send.flush();
		
			return true;
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
			return false;
		}
	}
	
}