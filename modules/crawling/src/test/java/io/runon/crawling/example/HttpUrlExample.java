package io.runon.crawling.example;

import io.runon.crawling.core.http.HttpMessage;
import io.runon.crawling.core.http.HttpUrl;
/**
 * @author macle
 */
public class HttpUrlExample {
    public static void main(String[] args) {
        HttpMessage message = HttpUrl.getMessage("https://www.naver.com/", HttpUrl.getChromeGetSimple("UTF-8"));
        System.out.println(message.getMessage());
    }
}
