
package io.runon.crawling.proxy.api;

import io.runon.commons.api.ApiMessage;
import io.runon.commons.api.Messages;

/**
 * 연결 유지 용 ping
 * @author macle
 */
public class ProxyPing  extends ApiMessage {

    @Override
    public void receive(String message) {
        sendMessage(Messages.SUCCESS);
    }
}
