
package io.runon.commons.apis.socket.server;

import io.runon.commons.apis.socket.ApiCommunication;
import io.runon.commons.callback.ObjCallback;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * api 통신용 서버
 *
 * @author macle
 */
@Slf4j
public class ApiServer extends Thread {
	

	private ServerSocket serverSocket = null;
	
	private ExceptionHandler exceptionHandler;
	
	private final int port;
	
	private boolean isRun = true; 
	
	private String packageName;
	
	private final List<ApiCommunication> apiCommunicationList = new LinkedList<>();
	
	private final Object lock = new Object();

	private final ObjCallback endCallback = obj -> {
		ApiCommunication apiCommunication = (ApiCommunication) obj;
		synchronized (lock) {
			apiCommunicationList.remove(apiCommunication);
		}
	};
	
	/**
	 * 생성자
	 * @param port int service port
	 * @param packageName string default packageName
	 */
	public ApiServer(int port, String packageName){
		this.port = port;
		this.packageName = packageName;
	
	}
	
	private InetAddress inetAddress = null;


	/**
	 * inet address 설정 
	 * 네트워크를 지정할 떄
	 * @param inetAddress InetAddress
	 */
	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	@Override
	public void run(){
		//noinspection TryWithIdenticalCatches
		try{
			log.debug("api server start");
				
			
			if(serverSocket == null){
				
				if(inetAddress == null){
					serverSocket = new ServerSocket(port);
				
				}else{
					serverSocket = new ServerSocket(port, 50, inetAddress );
					
				}
			}
			
			
			log.debug("api server start port: " + port);
			
			while(isRun){								
				Socket communication_socket = serverSocket.accept();	
				ApiCommunication apiCommunication = new ApiCommunication(packageName, communication_socket);
				apiCommunication.setEndCallback(endCallback);
				synchronized (lock) {
					apiCommunicationList.add(apiCommunication);	
				}
				
				
				
					
				apiCommunication.start();		
			}
		}catch(java.net.BindException e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		
		log.debug("api server stop port: " + port);
	}
	
	/**
	 * 연결개수 얻기
	 * @return int apiCommunicationList size
	 */
	public int size() {
		return apiCommunicationList.size();
	}
	
	/**
	 * api데몬 서버를 종료한다.
	 */
	public void stopServer(){
		isRun= false;
		packageName= null;
		//noinspection CatchMayIgnoreException
		try{
			serverSocket.close();
			serverSocket = null;
			
		}catch(Exception e){}
	}
}