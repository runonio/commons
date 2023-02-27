package io.runon.commons.keyword;

import com.seomse.jdbc.annotation.DateTime;
import lombok.Data;
import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
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
