

package io.runon.collect.crawling;

import io.runon.commons.apis.socket.server.ApiRequestConnectHandler;
import io.runon.commons.apis.socket.server.ApiRequestServer;
import io.runon.commons.callback.ObjCallback;
import io.runon.commons.handler.ExceptionHandler;
import io.runon.collect.crawling.core.http.HttpUrlConnManager;
import io.runon.collect.crawling.node.CrawlingLocalNode;
import io.runon.collect.crawling.node.CrawlingNode;
import io.runon.collect.crawling.node.CrawlingNodeMessage;
import io.runon.collect.crawling.node.CrawlingProxyNode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * CrawlingServer
 * @author macle
 */
@Slf4j
public class CrawlingServer {
	

	private static final CrawlingNode[] EMPTY_NODE_ARRAY = new CrawlingNode[0];
	
	private final ApiRequestServer requestServer;

	//순서정보 저장이 필요할 경우를 위한 list
	//메모리 저장용이라서 실제로는 사용되지않음. 실제사용되는건 node array
	private final List<CrawlingNode> nodeList = new LinkedList<>();
	
	private CrawlingNode [] nodeArray = EMPTY_NODE_ARRAY;

	private final Object lock = new Object();
	private final ObjCallback nodeEndCallback;
	
	private final HttpUrlConnManager httpUrlConnManager;

	private final Map<String, CrawlingProxyNode> proxyNodeMap;


	/**
	 * 생성자
	 * @param port int port
	 */
	public CrawlingServer(int port){
		
		proxyNodeMap = new Hashtable<>();
		nodeEndCallback = arg0 -> {
			CrawlingNode crawlingNode = (CrawlingNode)arg0;
			endNode(crawlingNode);
		};
		
		ApiRequestConnectHandler connectHandler = request -> {
			Socket socket = request.getSocket();
			InetAddress inetAddress = socket.getInetAddress();
			String nodeKey = inetAddress.getHostAddress() +"," + inetAddress.getHostName();
			CrawlingProxyNode crawlingProxyNode = proxyNodeMap.get(nodeKey);

			synchronized (lock) {

				boolean isNew = false;
				if (crawlingProxyNode == null) {
					crawlingProxyNode = new CrawlingProxyNode(nodeKey);
					proxyNodeMap.put(nodeKey, crawlingProxyNode);
					crawlingProxyNode.setExceptionHandler(exceptionHandler);
					ObjCallback endCallback = o -> endNode((CrawlingProxyNode)o);
					crawlingProxyNode.setEndCallback(endCallback);
					isNew = true;
				}
				crawlingProxyNode.addRequest(request);

				if (isNew) {
					nodeList.add(crawlingProxyNode);
					CrawlingNode [] array = nodeList.toArray(new CrawlingNode[0]);
					for (int i = 0; i < array.length; i++) {
						array[i].setSeq(i);
					}
					nodeArray = array;
					log.debug("new proxy node connect: " + nodeKey + ", node length: " + nodeArray.length);
				}
			}
		};

		requestServer = new ApiRequestServer(port, connectHandler);
	
		httpUrlConnManager = new HttpUrlConnManager(this);
	}
	
	private ExceptionHandler exceptionHandler;
	/**
	 * 예외 핸들러 설정
	 * @param exceptionHandler ExceptionHandler exceptionHandler
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * node 종료
	 * @param crawlingNode CrawlingNode crawlingNode
	 */
	public void endNode(CrawlingNode crawlingNode) {
		synchronized (lock) {

			if(nodeList.remove(crawlingNode)) {

				if(nodeList.size() == 0) {
					nodeArray = EMPTY_NODE_ARRAY;
				}else {
					CrawlingNode [] nodeArray = nodeList.toArray(new CrawlingNode[0]);
					
					for(int i=0 ; i<nodeArray.length ; i++) {
						nodeArray[i].setSeq(i);
					}
					this.nodeArray = nodeArray;

				}


				if(crawlingNode instanceof CrawlingProxyNode){
					CrawlingProxyNode crawlingProxyNode =(CrawlingProxyNode)crawlingNode;
					log.info("proxy node end: " + crawlingProxyNode.getNodeKey() +", " + crawlingProxyNode.getSeq());
					proxyNodeMap.remove(crawlingProxyNode.getNodeKey());
				}else{
					log.info("node end: " + crawlingNode.getSeq());


				}
			}

		}
	}
	
	private CrawlingLocalNode localNode = null;
	
	
	/**
	 * loacl node설정
	 */
	public void setLocalNode() {
		
		if(localNode != null) {
			log.error("local node already");
			return;
		}
		
		synchronized (lock) {
			//동기화 구간에서 한번더 체크 
			if(localNode != null) {
				log.error("local node already");
				return;
			}



			localNode = new CrawlingLocalNode();
			localNode.setEndCallback(nodeEndCallback);
			nodeList.add(localNode);
			nodeArray = nodeList.toArray(new CrawlingNode[0]);
			for(int i=0 ; i<nodeArray.length ; i++) {
				nodeArray[i].setSeq(i);
			}
			log.debug("local node add: " + nodeArray.length);
		}
	}
	
	/**
	 * 서버시작
	 */
	public void startServer() {
		requestServer.start();
	}
	
	/**
	 * 크롤링서버 종료
	 */
	public void stopServer() {
		//서버종료
		requestServer.stopServer();
		
		//모든노드 연결종료
		synchronized (lock) {
			
			for(CrawlingNode crawlingNode : nodeList) {
				crawlingNode.end();
			}
			nodeList.clear();
		}
		nodeArray = EMPTY_NODE_ARRAY;
	}
	
	/**
	 * HttpUrlConnection 을 이용한 script 결과 얻기
	 * @param check limit 체크 시간에 사용하는 키값
	 * @param connLimitTime long
	 * @param url String
	 * @param optionData JSONObject
	 * @return String script
	 */
	public String getHttpScript(String check, long connLimitTime, String url, JSONObject optionData) {
		return httpUrlConnManager.getHttpScript(check, connLimitTime, url, optionData);
	}

	/**
	 * HttpUrlConnection 을 활용하여 node 정보와 같이 script 얻기
	 * @param check limit 체크 시간에 사용하는 키값
	 * @param connLimitTime long
	 * @param url String
	 * @param optionData JSONObject
	 * @return String script
	 */
	public CrawlingNodeMessage getNodeMessage(String check, long connLimitTime, String url, JSONObject optionData){
		return httpUrlConnManager.getNodeMessage(check, connLimitTime, url, optionData);
	}



	/**
	 * 접속가능 node 배열얻기
	 * @return NodeArray
	 */
	public CrawlingNode [] getNodeArray() {
		return nodeArray;
	}

}
