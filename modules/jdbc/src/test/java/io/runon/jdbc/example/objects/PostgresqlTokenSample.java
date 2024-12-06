package io.runon.jdbc.example.objects;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;

/**
 * test 클래스
 * @author macle
 */
@Table(name="token")
public class PostgresqlTokenSample {


    @PrimaryKey(seq = 1)
    @Column(name = "token_id")
    long tokenId = 1L;

    @Column(name = "symbol")
     String symbol ="test";

    @Column(name = "name")
     String name = "test";

    @Column(name = "image_thumb")
     String imageThumb= "test";

    @DateTime
    @Column(name = "created_at")
    long createdAt =  System.currentTimeMillis();

    @DateTime
    @Column(name = "updated_at")
    long updatedAt = System.currentTimeMillis();

    @Column(name = "tag")
    PGobject tag;

    @Column(name = "market_cap")
    BigDecimal marketCap = new BigDecimal(0);

}
