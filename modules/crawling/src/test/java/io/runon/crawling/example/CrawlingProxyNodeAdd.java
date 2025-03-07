
package io.runon.crawling.example;

import io.runon.crawling.proxy.CrawlingProxy;

/**
 * proxy node add
 * @author macle
 */
public class CrawlingProxyNodeAdd {
    public static void main(String [] args) {
        try {
            new CrawlingProxy("127.0.0.1", 33001, 1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
