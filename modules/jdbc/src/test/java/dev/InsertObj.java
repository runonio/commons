package dev;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.FlagBoolean;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
/**
 * 개발용 테스트 소스
 * @author macle
 */
@Table(name="coingecko_news")
public class InsertObj {

    @PrimaryKey(seq = 1)
    @Column(name = "id")
    long id = 1;

    @Column(name = "title")
    String title = "test";

    @Column(name = "title_ko")
    String titleKo = "테스트";

    @Column(name = "body")
    String body;

    @Column(name = "link")
    String link;

    @FlagBoolean
    @Column(name = "is_translated")
    Boolean isTranslated = true;

}
