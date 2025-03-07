

package io.runon.crawling.node;

import io.runon.commons.api.ApiRequest;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.crawling.core.http.HttpMessage;
import io.runon.crawling.exception.NodeEndException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
/**
 * proxy node
 * @author macle
 */
@Slf4j
public class CrawlingProxyNode extends CrawlingNode {
	
	private final List<ProxyNodeRequest> requestList = new LinkedList<>();
	
	private final Object requestLock = new Object();
	
	private final String proxyNodeKey;
	
	/**
	 * 생성자
	 * @param proxyNodeKey String proxyNodeKey
	 */
	public CrawlingProxyNode(String proxyNodeKey){
		this.proxyNodeKey = proxyNodeKey;
	}
	
	/**
	 * ApiRequest(통신 socket) 추가
	 * @param request ApiRequest request
	 */
	public void addRequest(ApiRequest request) {
		ProxyNodeRequest proxyNodeRequest = new ProxyNodeRequest(this, request	);
		requestList.add(proxyNodeRequest);
	}
	
	@Override
	public void end() {
		synchronized (requestLock) {
			for(ProxyNodeRequest request : requestList) {
				try {
					request.disConnect();
				}catch(Exception e) {
					ExceptionUtil.exception(e, log, exceptionHandler);
				}
			}
			
			requestList.clear();
		}
		super.end();
	}
	
	@Override
	public String getHttpScript(String url, JSONObject optionData) {

		log.debug("proxy node seq: " + seq);
		
		ProxyNodeRequest minRequest = getMinRequest();
		return minRequest.getHttpScript(url, optionData);
	}

	/**
	 * 호출 대기가 가장적은 request 꺼내기
	 * @return ProxyNodeRequest
	 */
	private ProxyNodeRequest getMinRequest() {
		int size = requestList.size();
		if(size == 0) {
			throw new NodeEndException();
		}
		//추가될경우를 대비
		//제거되는경우는없음 
		ProxyNodeRequest minRequest = requestList.get(0);
		if(!minRequest.isConnect()){
			this.end();
			throw new NodeEndException();
		}

		int minWaitCount = minRequest.getWaitCount();

		for(int i=1 ; i<size ; i++) {
			ProxyNodeRequest request = requestList.get(i);
			if(minWaitCount > request.getWaitCount()) {
				minRequest = request;
				minWaitCount = request.getWaitCount();
			}
		}
		return minRequest;
	}

	/**
	 * node key 얻기
	 * @return String NodeKey
	 */
	public String getNodeKey() {
		return proxyNodeKey;
	}

	/**
	 * ping
	 * @return boolean
	 */
	public boolean ping(){
		ProxyNodeRequest [] requests;

		synchronized (requestLock) {
			requests = requestList.toArray( new ProxyNodeRequest[0]);
		}

		for(ProxyNodeRequest request : requests) {
			if(!request.ping()){
				return false;
			}
		}

		return true;
	}

	@Override
	public HttpMessage getHttpMessage(String url, JSONObject optionData) {

		log.debug("proxy node seq: " + seq);

		ProxyNodeRequest minRequest = getMinRequest();
		return minRequest.getHttpMessage(url, optionData);
	}
}
