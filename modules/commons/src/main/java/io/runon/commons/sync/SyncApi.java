

package io.runon.commons.sync;

import io.runon.commons.api.ApiMessage;
import io.runon.commons.api.Messages;
import io.runon.commons.utils.ExceptionUtil;

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
            communication.sendMessage(Messages.FAIL + ExceptionUtil.getStackTrace(e));
        }
    }
}
