package io.runon.jdbc.utils;

import io.runon.commons.utils.ExceptionUtils;
import io.runon.commons.utils.time.Times;
import io.runon.jdbc.JdbcQuery;
import io.runon.jdbc.PrepareStatementData;
import io.runon.jdbc.PrepareStatements;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 시간정보를 활용한 데이터 삭제
 * @author macle
 */
@Slf4j
public class DataDeleteUseTime {

    private final String [] tables;
    private final String timeColumn;

    public String afterTableQuery;


    public DataDeleteUseTime(String [] tables, String timeColumn){
        this.tables = tables;
        this.timeColumn = timeColumn;
    }

    public long delete(int day){
        long time = System.currentTimeMillis() - ((long)day * Times.DAY_1);
        delete(time);
        return time;
    }

    private final Set<String> errorTableSet = new HashSet<>();

    public void delete(long time){

        for(String table : tables){

            try {

                Map<Integer, PrepareStatementData> prepareStatementDataMap = PrepareStatements.newTimeMap(time);
                JdbcQuery.execute("delete from " + table + " where " + timeColumn + " < ?", prepareStatementDataMap);
                if (afterTableQuery != null) {
                    JdbcQuery.execute(afterTableQuery + " " + table);
                }

                errorTableSet.remove(table);

            }catch (Exception e){
                //반복 오류로그 방지용
                if(errorTableSet.contains(table)){
                    continue;
                }
                errorTableSet.add(table);
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public void setAfterTableQuery(String afterTableQuery) {
        this.afterTableQuery = afterTableQuery;
    }

}
