

package io.runon.commons.service;

import io.runon.commons.apis.socket.ApiMessage;
import io.runon.commons.apis.socket.Messages;
import io.runon.commons.utils.ExceptionUtils;

/**
 * 동기화 api
 * @author macle
 */
public class SyncApi extends ApiMessage {


    @Override
    public void receive(String message) {
        try{
            SynchronizerManager synchronizerManager = SynchronizerManager.getInstance();
            synchronizerManager.sync();
            communication.sendMessage(Messages.SUCCESS);
        }catch(Exception e){
            communication.sendMessage(Messages.FAIL + ExceptionUtils.getStackTrace(e));
        }
    }
}
