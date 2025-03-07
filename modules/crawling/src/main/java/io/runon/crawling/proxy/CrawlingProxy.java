

package io.runon.crawling.proxy;

import io.runon.commons.api.ApiCommunication;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.time.Times;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * CrawlingProxy remote proxy
 * @author macle
 */

@Slf4j
public class CrawlingProxy {
	

	private boolean isEnd = false;

	private int endCount = 0;

	private final Object lock = new Object();

	private ApiCommunication [] apiCommunicationArray;


	/**
	 * 생성자
	 * @param hostAddress String
	 * @param port int
	 * @param communicationCount final
	 * @throws IOException IOException
	 */
	public CrawlingProxy(String hostAddress, int port, final int communicationCount) throws  IOException{
		apiCommunicationArray = new ApiCommunication[communicationCount];
		for(int i=0 ; i<communicationCount ; i++) {
			Socket socket = new Socket(hostAddress, port);
			ApiCommunication apiCommunication = new ApiCommunication("com.seomse.crawling.proxy.api", socket);
			apiCommunication.setEndCallback(arg0 -> {
				synchronized (lock) {
					log.info("connect end");
					endCount++;
					if(endCount == communicationCount){
						isEnd = true;
					}
				}
			});

			apiCommunication.start();
			apiCommunicationArray[i] = apiCommunication;
		}
	}

	/**
	 * 종료여부
	 * @return boolean is end
	 */
	public boolean isConnect(){
		if(isEnd){
			return false;
		}

		if(apiCommunicationArray == null){
			return false;
		}

		for(ApiCommunication communication  : apiCommunicationArray){
			try {
				if(!communication.isConnect() ){
					return false;
				}

				if(System.currentTimeMillis() - communication.getLastConnectTime() > Times.HOUR_1){
					return false;
				}

			}catch(Exception e){
				log.error(ExceptionUtil.getStackTrace(e));
				return false;
			}
		}
		return true;
	}


	/**
	 * 종료
	 */
	public void disConnect(){
		if(apiCommunicationArray == null){
			return;
		}
		for(ApiCommunication communication  : apiCommunicationArray){
			try {
				communication.disConnect();
			}catch(Exception e){
				log.error(ExceptionUtil.getStackTrace(e));
			}
		}
		apiCommunicationArray = null;
		isEnd = true;
	}
}
