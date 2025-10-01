
package io.runon.collect.crawling.proxy.api;

import io.runon.commons.apis.socket.ApiMessage;
import io.runon.commons.apis.socket.Messages;

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
