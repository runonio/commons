
package io.runon.collect.crawling.service;

import io.runon.commons.config.Config;
import io.runon.commons.service.Service;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.time.Times;
import io.runon.collect.crawling.CrawlingServer;
import io.runon.collect.crawling.node.CrawlingNode;
import io.runon.collect.crawling.node.CrawlingProxyNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 프록시 노드 핑체크
 * @author macle
 */
@Slf4j
public class ProxyNodePingService extends Service {

    private final CrawlingServer crawlingServer;

    /**
     * 생성자
     */
    public ProxyNodePingService(CrawlingServer crawlingServer){

        this.crawlingServer = crawlingServer;

        setServiceId(ProxyNodePingService.class.getName());
        setState(State.START);

        //슬립타임 직접 컨트롤
        setSleepTime(null);
    }
    
    @Override
    public void work() {
        long cycleTime = Config.getLong("crawling.proxy.node.ping.cycle.time", Times.MINUTE_5);
        long startTime = System.currentTimeMillis();

        CrawlingNode[] nodeArray = crawlingServer.getNodeArray();

        for (CrawlingNode node : nodeArray){
            if(node instanceof CrawlingProxyNode){
                CrawlingProxyNode proxyNode = (CrawlingProxyNode)node;
                if(!proxyNode.ping()){
                    log.debug("ping fail disconnect node: " + proxyNode.getNodeKey());
                    crawlingServer.endNode(proxyNode);
                }
            }
        }

        long time = System.currentTimeMillis() - startTime;
        if(time < cycleTime){
            try {
                long sleepTime = cycleTime - time;
                log.debug("ping check sleep: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }else{
            log.debug("ping check not sleep");
        }
    }
}
