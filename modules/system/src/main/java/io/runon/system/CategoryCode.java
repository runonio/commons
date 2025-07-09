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
@Table(name="category_code")
public class CategoryCode {

    @PrimaryKey(seq = 1)
    @Column(name = "category_id")
    String categoryId;

    @PrimaryKey(seq = 2)
    @Column(name = "code")
    String code;

    @Column(name = "name_ko")
    String nameKo;

    @Column(name = "name_en")
    String nameEn;

    @Column(name = "meta_data")
    String metaData;

    @Column(name = "description")
    String description;

    @Column(name = "is_del")
    boolean isDel = false;

    @DateTime
    @Column(name = "updated_at")
    long updatedAt = System.currentTimeMillis();

}
