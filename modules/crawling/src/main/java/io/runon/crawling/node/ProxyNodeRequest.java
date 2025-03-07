
package io.runon.crawling.node;

import com.google.gson.Gson;
import io.runon.commons.api.ApiRequest;
import io.runon.commons.api.Messages;
import io.runon.commons.config.Config;
import io.runon.crawling.core.http.HttpMessage;
import io.runon.crawling.exception.NodeEndException;
import io.runon.crawling.exception.ProxyMessageFailException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * proxy node request
 * @author macle
 */
@Slf4j
public class ProxyNodeRequest {

	
	private int waitCount = 0;
	
	private final Object lock = new Object();

	private final ApiRequest request;
	
	private final CrawlingProxyNode crawlingProxyNode;
	
	/**
	 * 생성자
	 * @param crawlingProxyNode CrawlingProxyNode
	 * @param request ApiRequest
	 */
	ProxyNodeRequest(CrawlingProxyNode crawlingProxyNode
			, ApiRequest request ){
		this.crawlingProxyNode = crawlingProxyNode;
		this.request = request;

		Long proxyNodeWaitTimeOut = Config.getLong("crawling.proxy.wait.time.out");


		if(proxyNodeWaitTimeOut != null) {
			request.setWaitTimeOut(proxyNodeWaitTimeOut);
		}
	}

	/**
	 * http script get
	 * @param url String
	 * @param optionData JSONObject
	 * @return String script
	 */
	public String getHttpScript(String url, JSONObject optionData) {

		log.debug("request http url: " + url);

		String result ;
		
		
		try {
			JSONObject messageObj = new JSONObject();
			messageObj.put("url", url);
			messageObj.put("option_data", optionData);
			addWait();
			synchronized (lock) {
				result = request.sendToReceiveMessage("HttpScript", messageObj.toString());
			}
			removeWait();
			if(result.startsWith(Messages.SUCCESS)) {
				result = result.substring(Messages.SUCCESS.length());
				return result;
						
			}else if(result.startsWith(Messages.FAIL)) {
				result= result.substring(Messages.FAIL.length());
				log.error(result);
//				throw new NodeEndException();

			}else if(result.equals(ApiRequest.CONNECT_FAIL)) {
				crawlingProxyNode.end();
				throw new NodeEndException();
			}
				
		}catch(Exception e) {
			throw new NodeEndException();
		}

		return result;
	}

	private final Object waitLock = new Object();

	private void addWait(){
		synchronized (waitLock){
			waitCount++;
		}
	}
	private void removeWait(){
		synchronized (waitLock){
			waitCount-- ;
			//코딩 실수할까봐 방어코드
			if(waitCount < 0) {
				waitCount = 0;
			}
		}
	}

	public HttpMessage getHttpMessage(String url, JSONObject optionData){
		log.debug("request http url: " + url);
		String result ;
		try {
			JSONObject messageObj = new JSONObject();
			messageObj.put("url", url);
			messageObj.put("option_data", optionData);
			addWait();
			synchronized (lock) {
				result = request.sendToReceiveMessage("HttpMessage", messageObj.toString());
			}
			removeWait();
			if(result.startsWith(Messages.SUCCESS)) {
				Gson gson = new Gson();
				result = result.substring(Messages.SUCCESS.length());
				return gson.fromJson(result, HttpMessage.class);

			}else if(result.startsWith(Messages.FAIL)) {
				result= result.substring(Messages.FAIL.length());
				throw new ProxyMessageFailException(result);

			}else if(result.equals(ApiRequest.CONNECT_FAIL)) {
				crawlingProxyNode.end();
				throw new NodeEndException();
			}else{
				throw new ProxyMessageFailException(result);
			}

		}catch(Exception e) {
			throw new NodeEndException();
		}

	}

	/**
	 * wait count get
	 * @return int waitLength
	 */
	public int getWaitCount() {
		return waitCount;
	}
	
	/**
	 * 연결 종료
	 */
	void disConnect() {
		request.disConnect();	
	}

	/**
	 * @return boolean is connect
	 */
	public boolean isConnect(){
		return request.isConnect();
	}

	/**
	 * ping
	 * @return boolean
	 */
	public boolean ping(){
		String result;
		synchronized (lock){
			result = request.sendToReceiveMessage("ProxyPing","");
		}
		return result.startsWith(Messages.SUCCESS);

	}

}
