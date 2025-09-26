
package io.runon.commons.apis.socket.server;

import io.runon.commons.apis.socket.ApiRequest;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * api 요청용 서버
 * 클라이언트가 서버의 api를 이용하는것이 아닌
 * 서버가 클라이언트의 api를 이용할떄 사용 한다.
 * api 요청이 들어왔을때 전달받는 핸들러
 *  클라이언트가 서버에 나를 컨트롤 해달라고 하는 경우에 사용
 *
 * @author macle
 */
@Slf4j
public class ApiRequestServer extends Thread{

	private ServerSocket serverSocket = null;
	
	private ExceptionHandler exceptionHandler;
	
	private final int port;
	
	private boolean isRun = true; 
	

	private final ApiRequestConnectHandler connectHandler;


	/**
	 * 생성자
	 * @param port int
	 * @param connectHandler ApiRequestConnectHandler
	 */
	public ApiRequestServer(int port, ApiRequestConnectHandler connectHandler){
		this.port = port;
		this.connectHandler = connectHandler;
	
	}
	
	private InetAddress inetAddress = null;

	/**
	 * InetAddress 설정
	 * 네트워크 카디를 지정 하는 경우
	 * @param inetAddress InetAddress
	 */
	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	@Override
	public void run(){
		//noinspection TryWithIdenticalCatches
		try{
			log.debug("request server start");
				
			
			if(serverSocket == null){
				
				if(inetAddress == null){
					serverSocket = new ServerSocket(port);
				
				}else{
					serverSocket = new ServerSocket(port, 50, inetAddress );
					
				}
			}
			
			
			log.debug("request server start port: " + port);
			
			while(isRun){								
				Socket socket = serverSocket.accept();	
				ApiRequest apiRequest = new ApiRequest(socket);
				connectHandler.connect(apiRequest);
			}
		}catch(java.net.BindException e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		
		log.debug("request server stop port: " + port);
	}
	
	
	
	/**
	 * api데몬 서버를 종료한다.
	 */
	public void stopServer(){
		isRun= false;
		//noinspection CatchMayIgnoreException
		try{
			serverSocket.close();
			serverSocket = null;
			
		}catch(Exception e){}
	}
}
