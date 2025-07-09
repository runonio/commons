package io.runon.system;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="category_key_value")
public class CategoryKeyValue {

    @PrimaryKey(seq = 1)
    @Column(name = "category_id")
    String categoryId;

    @PrimaryKey(seq = 2)
    @Column(name = "data_key")
    String key;

    @Column(name = "data_value")
    String value;

    @Column(name = "meta_data")
    String metaData;


    @DateTime
    @Column(name = "updated_at")
    long updatedAt = System.currentTimeMillis();
}
