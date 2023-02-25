package io.runon.commons;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import lombok.Data;

/**
 * 수집기정보
 * @author macle
 */
@Data
@Table(name="collect")
public class Collect {

    @PrimaryKey(seq = 1)
    @Column(name = "collect_id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "collect_type")
    String type;

    @Column(name = "collect_path")
    String collectPath;

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
