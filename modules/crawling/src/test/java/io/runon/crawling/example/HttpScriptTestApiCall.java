
package io.runon.crawling.example;

import io.runon.commons.api.ApiRequests;

/**
 * http get test
 * @author macle
 */
public class HttpScriptTestApiCall {
    public static void main(String[] args) {

        System.out.println(ApiRequests.sendToReceiveMessage("127.0.0.1", 33001,"io.runon.crawling.apis","HttpScriptTestApi","https://codeday.me/ko/qa/20190706/982179.html"));
    }
}
