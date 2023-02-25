package io.runon.commons.collect;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
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
