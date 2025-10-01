
package io.runon.collect.crawling.node;

import io.runon.collect.crawling.core.http.HttpMessage;

/**
 * CrawlingNode, script
 * @author macle
 */
public class CrawlingNodeMessage {

    private final CrawlingNode crawlingNode;
    private final HttpMessage httpMessage;


    public CrawlingNodeMessage(CrawlingNode crawlingNode, HttpMessage message){
        this.crawlingNode = crawlingNode;
        this.httpMessage = message;

    }

    /**
     * @return CrawlingNode
     */
    public CrawlingNode getCrawlingNode() {
        return crawlingNode;
    }

    /**
     * @return script String
     */
    public String getScript() {
        return httpMessage.getMessage();
    }

    public HttpMessage getMessage() {
        return httpMessage;
    }


}
