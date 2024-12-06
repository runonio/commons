package io.runon.commons.collect;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import lombok.Data;

/**
 * 수집기그룹 정보
 * @author macle
 */

@Data
@Table(name="collect_group")
public class CollectGroup {
    @PrimaryKey(seq = 1)
    @Column(name = "collect_group_id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "data_value")
    String dataValue;

    @Column(name = "description")
    String description;

    @DateTime
    @Column(name = "updated_at")
    long time;
    @Override
    public String toString(){
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create().toJson(this);
    }
}
