package dev;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.objects.JdbcObjects;

import java.util.List;

@Table(name="coinmarketcap_coin")
public class CoinExchange {

    @PrimaryKey(seq = 1)
    @Column(name = "id")
    long id;

    @Column(name = "slug")
    String slug;

    @Column(name = "market_cap_rank")
    Integer marketCapRank;


    @Column(name = "exchanges")
    String exchanges;

    @DateTime
    @Column(name = "exchange_updated_at")
    Long exchangeUpdatedAt;

    public static void main(String[] args) {
        List<CoinExchange> coinExchangeList = JdbcObjects.getObjList(CoinExchange.class);
    }
}
