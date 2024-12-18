
package io.runon.commons.api.server;

import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 메시지 전달 통신 서버.
 * 메시지를 정해진 크기 만큼 받고
 * 메시지 종료 여부를 전달 받을 떄 활용
 * 대량 메시지를 전달 받기 위해 개발됨
 * @author macle
 */
@Slf4j
public class ReceiveServer extends Thread{

	private ServerSocket serverSocket = null;
	private boolean isService = true;
	
	private boolean isEnd = false;
	
	private final int port;
	private InetAddress inetAddress = null;
	
	private int bufferSize = 10240;
	
	private ExceptionHandler exceptionHandler;
	

	/**
	 * 생성자
	 * @param port int
	 */
	public ReceiveServer(int port){
		this.port = port;
	}

	/**
	 * 예외 핸들러설정
	 * @param  exceptionHandler exceptionHandler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * bufferSize 얻기
	 * @return int bufferSize
	 */
	public int getBufferSize() {
		return bufferSize;
	}


	/**
	 * bufferSize 설정 기본 10240
	 * @param bufferSize int
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	/**
	 * inetAddress 설정
	 * @param inetAddress InetAddress
	 */
	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}
	
	
	@Override
	public void run(){
	
		try{
			if(serverSocket == null){
				
				if(inetAddress == null){
					serverSocket = new ServerSocket(port);
				
				}else{
					serverSocket = new ServerSocket(port, 50, inetAddress );
					
				}
			}
			
			while(isService){
				
				try{
					Socket socket = serverSocket.accept();	
					ReceiveCommunication receiveCommunication = new ReceiveCommunication(socket, bufferSize);
					receiveCommunication.setExceptionHandler(exceptionHandler);
					receiveCommunication.start();
				}catch(Exception e){
					ExceptionUtil.exception(e, log, exceptionHandler);
				}
			}
			
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		
		isEnd = true;
		
	}
	
	/**
	 * 서비스 종료 
	 */
	public void stopService(){
		isService = false;
		//noinspection CatchMayIgnoreException
		try{
			if(serverSocket != null){
				serverSocket.close();
				serverSocket = null;
			}
		}catch(Exception e){}
	}

	
	/**
	 * 종료 여부 얻기
	 * @return boolean isEnd
	 */
	public boolean isEnd() {
		return isEnd;
	}
}
