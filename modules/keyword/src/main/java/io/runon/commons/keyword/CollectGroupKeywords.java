package io.runon.commons.keyword;

import com.seomse.jdbc.objects.JdbcObjects;

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
}
