

package io.runon.collect.crawling.apis;

import io.runon.commons.apis.socket.ApiMessage;
import io.runon.commons.apis.socket.Messages;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.collect.crawling.CrawlingManager;

/**
 * http script 얻기
 * @author macle
 */
public class GetHttpScript extends ApiMessage {

    @Override
    public void receive(String message) {
        try {
            sendMessage(CrawlingManager.getInstance().getServer().getHttpScript(message,1000L, message,null));
        }catch(Exception e){
            sendMessage(Messages.FAIL + ExceptionUtil.getStackTrace(e));
        }
    }
}