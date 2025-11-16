package io.runon.commons.service;

import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 동작 서비스 단위의 추상 쿨래스
 *
 * @author macle
 */
@Slf4j
public abstract class ServiceThrowExceptionLog extends Service{

    @Override
    public void work(){
        try{
            workThrowLog();
        }catch (Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public abstract void workThrowLog() throws Exception;
}
