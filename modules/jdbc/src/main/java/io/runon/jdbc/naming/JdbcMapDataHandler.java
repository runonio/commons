
package io.runon.jdbc.naming;


import java.util.Map;

/**
 * 한번에 select 된 데이터가 많을때 row 단위로 처리 해야 하는 경우
 * @author macle
 */
public interface JdbcMapDataHandler {

    /**
     * jdbc map data receive
     * @param data Map jdbc map data
     */
    void receive(Map<String, Object> data);
}
