package io.runon.commons.keyword;

import io.runon.jdbc.annotation.DateTime;
import lombok.Data;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
/**
 * @author macle
 */
@Data
@Table(name="collect_group_keyword_map")
public class CollectGroupKeywordMap {

    @PrimaryKey(seq = 1)
    @Column(name = "collect_group_id")
    String collectGroupId;

    @PrimaryKey(seq = 2)
    @Column(name = "keyword_no")
    long keywordNumber;

    @DateTime
    @Column(name = "created_at")
    long time;
}
