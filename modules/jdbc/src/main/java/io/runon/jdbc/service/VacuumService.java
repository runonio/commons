package io.runon.jdbc.service;

import io.runon.commons.service.Service;
import io.runon.commons.utils.ExceptionUtils;
import io.runon.commons.utils.time.Times;
import io.runon.jdbc.JdbcQuery;
import lombok.extern.slf4j.Slf4j;

/**
 * 시퀀스 생성기
 * @author macle
 */
@Slf4j
public class VacuumService extends Service {

    private final String [] tableNames;


    public VacuumService(String [] tableNames){
        setDelayStartTime(Times.HOUR_1);
        setSleepTime(Times.HOUR_12);
        setState(State.START);
        this.tableNames = tableNames;
    }

    @Override
    public void work() {
        if(tableNames == null || tableNames.length == 0){
            log.debug("service stop " + getServiceId() + " table size 0");
            setState(State.STOP);
            return;
        }

        for(String tableName : tableNames){
            try{
                JdbcQuery.execute("vacuum " + tableName);
            }catch(Exception e){
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

}
