package io.runon.commons.keyword;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="keyword")
public class Keyword {
    @PrimaryKey(seq = 1)
    @Column(name = "keyword_no")
    Long number;

    @Column(name = "keyword")
    String keyword;

    @Column(name = "data_value")
    String dataValue;

    @Column(name = "checksum")
    String checksum;

    @DateTime
    @Column(name = "updated_at")
    Long time;
}
