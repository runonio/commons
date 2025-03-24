

package io.runon.commons.service;

import io.runon.commons.config.Config;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 동기화 서비스
 * @author macle
 */
@Slf4j
public class SyncService extends Service {


    /**
     * 생성자
     */
    public SyncService()
    {
        setSleepTime(Config.getLong("sync.service.sleep.time", 3600000L));
        setDelayStartTime(Config.getLong("sync.service.sleep.time", 3600000L));
        setState(State.START);
    }

    @Override
    public void work() {
        try{
            SynchronizerManager synchronizerManager = SynchronizerManager.getInstance();
            if (Config.getBoolean("sync.service.flag", true)
                    && !synchronizerManager.isIng()) {
                synchronizerManager.sync();
            }
        }catch(Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }



}
