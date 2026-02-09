package io.runon.collect.keyword;

import io.runon.jdbc.objects.JdbcObjects;

import java.sql.Connection;

/**
 * @author macle
 */
public class CollectGroupKeywords {

    public static void insert(String collectGroupId, long keywordNumber){
        CollectGroupKeywordMap collectGroupKeywordMap = new CollectGroupKeywordMap();
        collectGroupKeywordMap.collectGroupId = collectGroupId;
        collectGroupKeywordMap.keywordNumber = keywordNumber;
        collectGroupKeywordMap.time = System.currentTimeMillis();
        JdbcObjects.insertIfNoData(collectGroupKeywordMap);
    }

    public static void insert(Connection connection, String collectGroupId, long keywordNumber){
        CollectGroupKeywordMap collectGroupKeywordMap = new CollectGroupKeywordMap();
        collectGroupKeywordMap.collectGroupId = collectGroupId;
        collectGroupKeywordMap.keywordNumber = keywordNumber;
        collectGroupKeywordMap.time = System.currentTimeMillis();
        JdbcObjects.insertIfNoData(connection, collectGroupKeywordMap);
    }

}
