
package io.runon.crawling.example;

import io.runon.commons.api.server.ApiServer;
import io.runon.crawling.CrawlingManager;

/**
 * server start
 * @author macle
 */
public class CrawlingServerStart {
    public static void main(String[] args) {

        ApiServer apiServer = new ApiServer(33001,"io.runon");
        apiServer.start();

        //noinspection ResultOfMethodCallIgnored
        CrawlingManager.getInstance();

    }

}
