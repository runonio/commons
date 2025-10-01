
package io.runon.collect.crawling.node;

import io.runon.commons.callback.ObjCallback;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.collect.crawling.core.http.HttpMessage;
import io.runon.collect.crawling.exception.NodeEndException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * crawling node abstract
 * @author macle
 */
public abstract class CrawlingNode {

	private boolean isEnd = false;

	protected int seq;
	
	protected ObjCallback endCallback = null;
	
	/**
	 * 종료 call back 설정
	 * @param endCallback ObjCallback
	 */
	public void setEndCallback(ObjCallback endCallback) {
		this.endCallback = endCallback;
	}
	
	/**
	 * node 순번 얻기
	 * @return int seq
	 */
	public int getSeq() {
		return seq;
	}


	/**
	 * node 순번 설정
	 * @param nodeSeq int
	 */
	public void setSeq(int nodeSeq) {
		this.seq = nodeSeq;
	}
	
	protected ExceptionHandler exceptionHandler;
	/**
	 * 예외 핸들러 설정
	 * @param exceptionHandler ExceptionHandler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * 종료 여부
	 * @return boolean is end
	 */
	public boolean isEnd() {
		return isEnd;
	}
	

	/**
	 * 크롤링 노드 종료
	 */
	public void end() {
		isEnd = true;
		
		if(endCallback != null) {
			endCallback.callback(this);
		}
	}
	
	private final Map<String, Long> lastConnectTimeMap = new HashMap<>();
	/**
	 * 마지막 접속 time 얻기
	 * @param checkUrl String checkUrl
	 * @return Long LastConnectTime
	 */
	public Long getLastConnectTime(String checkUrl) {
		return lastConnectTimeMap.get(checkUrl);
	}
	
	/**
	 * 마지막 접속 time 업데이트
	 * @param checkUrl String checkUrl
	 */
	public void updateLastConnectTime(String checkUrl) {
		lastConnectTimeMap.put(checkUrl, System.currentTimeMillis());
	}

	public String getHttpScript(String url, String jsonOptionValue){
		return getHttpScript(url , new JSONObject(jsonOptionValue));
	}

	public HttpMessage getHttpMessage(String url, String jsonOptionValue){
		return getHttpMessage(url , new JSONObject(jsonOptionValue));
	}

	/**
	 * HttpUrlConnection 을 이용한 script 결과 얻기
	 * @param url String
	 * @param optionData JSONObject
	 * @return script String
	 */
	public abstract String getHttpScript(String url, JSONObject optionData) throws NodeEndException;

	public abstract HttpMessage getHttpMessage(String url, JSONObject optionData) throws NodeEndException ;


}
