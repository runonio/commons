package io.runon.commons.keyword;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="collect_keyword_map")
public class CollectKeywordMap {
    @PrimaryKey(seq = 1)
    @Column(name = "collect_id")
    String collectId;

    @PrimaryKey(seq = 2)
    @Column(name = "keyword_no")
    long keywordNo;
}
