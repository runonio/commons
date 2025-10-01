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
@Table(name="category")
public class Category {

    @PrimaryKey(seq = 1)
    @Column(name = "category_id")
    String categoryId;

    @Column(name = "category_type")
    String categoryType;

    @Column(name = "name_ko")
    String nameKo;

    @Column(name = "name_en")
    String nameEn;

    @Column(name = "description")
    String description;

    @Column(name = "meta_data")
    String metaData;

    @Column(name = "is_del")
    boolean isDel = false;

    @DateTime
    @Column(name = "updated_at")
    long updatedAt  = System.currentTimeMillis();
}
