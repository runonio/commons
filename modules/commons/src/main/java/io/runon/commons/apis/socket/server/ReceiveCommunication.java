
package io.runon.commons.apis.socket.server;

import io.runon.commons.apis.socket.communication.StringReceive;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * 메시지 전달 받는 통신
 * 메시지를 정해진 크기 만큼 받고
 * 메시지 종료 여부를 전달 받을 떄 활용
 * 대량 메시지를 전달 받기 위해 개발됨
 * @author macle
 */
@Slf4j
public class ReceiveCommunication extends Thread{

	private final StringReceive stringReceive;
	
	private ExceptionHandler exceptionHandler;

	/**
	 * 생성자
	 * @param socket Socket
	 * @param bufSize int
	 * @throws IOException IOException
	 */
	ReceiveCommunication(Socket socket, int bufSize) throws  IOException{
		stringReceive = new StringReceive(socket, bufSize);
	}


	/**
	 * ExceptionHandler 설정
	 * @param exceptionHandler ExceptionHandler 예외 핸들링
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}


	private MessageReceiver messageReceiver = null;
	
	@Override
	public void run(){
		
		StringBuilder sb = new StringBuilder();
		while(true){
			
			try{
				String message = stringReceive.receive();
				if(message == null){
					break;
				}
				if(messageReceiver == null){
					sb.append(message);
					String classSearch = sb.toString();
					int index = classSearch.indexOf(',');
					
					if(index ==-1){
						continue;
					}
					messageReceiver = (MessageReceiver)Class.forName(classSearch.substring(0, index)).newInstance();
					
					if(index != classSearch.length()-1){
						messageReceiver.receive(classSearch.substring(index+1));
					}					
					sb.setLength(0);

				}else{
					messageReceiver.receive(message);	
				}
				
				
			
			}catch(Exception e){
				ExceptionUtil.exception(e, log, exceptionHandler);
				break;
			}
		}
		try{
			if(messageReceiver != null){
				messageReceiver.end();
			}
		}catch(Exception e){
			ExceptionUtil.exception(e, log, exceptionHandler);
		}
		stringReceive.disConnect();
	}

}
