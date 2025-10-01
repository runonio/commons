
package io.runon.collect.crawling;

import io.runon.commons.config.Config;
import io.runon.collect.crawling.service.ProxyNodePingService;

/**
 * CrawlingManager
 * @author macle
 */
public class CrawlingManager {

    private static class Singleton {
        private static final CrawlingManager instance = new CrawlingManager();
    }

    /**
     * CrawlingManager
     * @return CrawlingManager singleton instance
     */
    public static CrawlingManager getInstance(){
        return Singleton.instance;
    }

    public static final int DEFAULT_PORT = 33301;
    private final CrawlingServer crawlingServer;

    /**
     * 크롤링 서버
     */
    private CrawlingManager(){
        crawlingServer = new CrawlingServer(Config.getInteger("crawling.server.port", DEFAULT_PORT));
        if(Config.getBoolean("crawling.server.local.node.flag",true)) {
            crawlingServer.setLocalNode();
        }
        crawlingServer.startServer();
        ProxyNodePingService proxyNodePingService = new ProxyNodePingService(crawlingServer);
        proxyNodePingService.start();

    }

    /**
     * 싱글턴 인스턴스로 관리되는 서버 얻기
     * @return CrawlingServer
     */
    public CrawlingServer getServer(){
        return crawlingServer;
    }



}
