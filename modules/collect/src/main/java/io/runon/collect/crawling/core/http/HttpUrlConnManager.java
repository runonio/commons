
package io.runon.collect.crawling.core.http;

import io.runon.commons.utils.ExceptionUtil;
import io.runon.collect.crawling.CrawlingServer;
import io.runon.collect.crawling.exception.NodeEndException;
import io.runon.collect.crawling.node.CrawlingNode;
import io.runon.collect.crawling.node.CrawlingNodeMessage;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpUrlConnection 으로 붇는정보 관리
 * server 에서 이용하는 HttpUrlConnection 이벤트 처리 클래스
 * 소스가 너무 가독성이 떨어지는 경우를 위해서 클래스 분리
 * @author macle
 */
@Slf4j
public class HttpUrlConnManager {
	

	private final CrawlingServer server;
	
	private final Map<String, CrawlingNode> lastNodeMap;
	
	private final Map<String, Object> lockMap;
	
	private final Object lock = new Object();

	/**
	 * 생성자
	 * @param server CrawlingServer
	 */
	public HttpUrlConnManager(CrawlingServer server) {
		this.server = server;
		lastNodeMap = new HashMap<>();
		lockMap = new HashMap<>();
	}

	private boolean isNodeNullLog = true;

	/**
	 * HttpUrlConnection 을 이용한 script 결과 얻기
	 * @param checkUrl String
	 * @param connLimitTime long
	 * @param url String
	 * @param optionData JSONObject
	 * @return String
	 */
	public String getHttpScript(String checkUrl, long connLimitTime, String url, JSONObject optionData) {

		CrawlingNodeMessage crawlingNodeScript = getNodeMessage(checkUrl, connLimitTime, url, optionData);
		if(crawlingNodeScript == null){
			return null;
		}

		return crawlingNodeScript.getScript();
	}

	/**
	 * CrawlingNodeScript 얻기
	 * CrawlingNode 와
	 * Script
	 * @param checkUrl String
	 * @param connLimitTime long
	 * @param url String
	 * @param optionData JSONObject
	 * @return CrawlingNodeScript
	 */
	public CrawlingNodeMessage getNodeMessage(String checkUrl, long connLimitTime, String url, JSONObject optionData) {

		CrawlingNode [] nodeArray = server.getNodeArray();
		if(nodeArray.length == 0) {
			if(isNodeNullLog) {
				log.error("node null...");
				isNodeNullLog = false;
			}
			return null;
		}

		log.debug("node length: " + nodeArray.length);

		isNodeNullLog = true;

		Object lockObj = lockMap.get(checkUrl);
		if(lockObj == null) {
			synchronized (lock) {
				lockObj = lockMap.computeIfAbsent(checkUrl, k -> new Object());
			}
		}

		CrawlingNode node;
		CrawlingNodeMessage crawlingNodeMessage = null;
		boolean isNodeExecute = true;
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (lockObj) {

			CrawlingNode lastNode = lastNodeMap.get(checkUrl);


			int nextSeq ;

			if(lastNode == null || lastNode.isEnd()) {
				nextSeq = 0;
			}else {
				nextSeq = lastNode.getSeq() + 1;

				if(nextSeq >= nodeArray.length) {
					nextSeq = 0;
				}
			}
			node =  nodeArray[nextSeq];

			Long time = node.getLastConnectTime(checkUrl);
			if(time != null) {
				long gap = System.currentTimeMillis() - time;

				if( gap < connLimitTime	) {
					int saveSeq = nextSeq;

					boolean isNodeChange = false;

					while(true) {
						nextSeq = nextSeq+1;
						if(nextSeq >= nodeArray.length) {
							nextSeq = 0;
						}

						if(saveSeq == nextSeq) {
							break;
						}

						Long checkTime = nodeArray[nextSeq].getLastConnectTime(checkUrl);
						if(checkTime == null ||  System.currentTimeMillis() - checkTime >= connLimitTime) {
							isNodeChange = true;
							node = nodeArray[nextSeq];
						}
					}

					if(!isNodeChange) {
						try {
							Thread.sleep(connLimitTime - gap);
						}catch(Exception e) {
							log.error(ExceptionUtil.getStackTrace(e));
						}
					}
				}

			}
			try {

				node.updateLastConnectTime(checkUrl);
				crawlingNodeMessage = new CrawlingNodeMessage(node, node.getHttpMessage(url, optionData));
				lastNodeMap.put(checkUrl, node);

			}catch(NodeEndException e) {
				log.debug("node end. other node request");
				isNodeExecute = false;
				server.endNode(node);
			}
		}

		if(!isNodeExecute){
			return getNodeMessage(checkUrl, connLimitTime, url, optionData);
		}

		return crawlingNodeMessage;
	}

}
