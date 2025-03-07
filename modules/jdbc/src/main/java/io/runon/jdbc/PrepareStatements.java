
package io.runon.jdbc;

import io.runon.jdbc.naming.JdbcDataType;

import java.util.HashMap;
import java.util.Map;

/**
 * PrepareStatementData 를 활용 하는 유틸성 클래서
 * @author macle
 */
public class PrepareStatements {
    /**
     * time 을 활용한 조건 생성
     * @param time long unix time
     * @return Map
     */
    public static Map<Integer, PrepareStatementData> newTimeMap(long time){

        Map<Integer, PrepareStatementData> prepareStatementDataMap = new HashMap<>();
        PrepareStatementData prepareStatementData = new PrepareStatementData();
        prepareStatementData.setData(time);
        prepareStatementData.setType(JdbcDataType.DATE_TIME);
        prepareStatementDataMap.put(1, prepareStatementData);

        return prepareStatementDataMap;

    }
}
