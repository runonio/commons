package io.runon.collect;

import io.runon.jdbc.JdbcQuery;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.objects.JdbcObjects;
import lombok.Data;

/**
 * 수집기그룹과 수집기의 연결정보
 * @author macle
 */
@Data
@Table(name="collect_info")
public class CollectInfo {

    @PrimaryKey(seq = 1)
    @Column(name = "collect_id")
    String collectId;

    @PrimaryKey(seq = 2)
    @Column(name = "data_key")
    String dataKey;


    @Column(name = "collect_info")
    String collectInfo;

    @DateTime
    @Column(name = "collected_at")
    long time;

    public static void update(String collectId, String dataKey, String info){
        update(collectId, dataKey, info, true);
    }

    public static void update(String collectId, String dataKey, String info, boolean isNullUpdate){
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setCollectId(collectId);
        collectInfo.setDataKey(dataKey);
        collectInfo.setCollectInfo(info);
        collectInfo.setTime(System.currentTimeMillis());

        JdbcObjects.insertOrUpdate(collectInfo, isNullUpdate);
    }

    public static String getInfo(String collectId, String dataKey){
        return JdbcQuery.getResultOne("select collect_info from collect_info where collect_id='" + collectId +"' and data_key='" + dataKey + "'");

    }


}
