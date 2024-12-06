package io.runon.commons.collect;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.objects.JdbcObjects;
import lombok.Data;

/**
 * 수집기그룹과 수집기의 연결정보
 * @author macle
 */
@Data
@Table(name="collect_group_map")
public class CollectGroupMap {

    @PrimaryKey(seq = 1)
    @Column(name = "collect_group_id")
    String groupId;

    @PrimaryKey(seq = 2)
    @Column(name = "collect_id")
    String collectId;

    public static void update(String groupId, String collectId){
        CollectGroupMap map = new CollectGroupMap();
        map.setGroupId(groupId);
        map.setCollectId(collectId);
        JdbcObjects.insertIfNoData(map);
    }

    @Override
    public String toString(){
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create().toJson(this);
    }

}
