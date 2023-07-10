import com.seomse.commons.config.Config;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.jdbc.objects.JdbcObjects;
import io.runon.commons.keyword.CollectKeywordMap;

import java.util.List;

/**
 * @author macle
 */
public class CollectKeywordMapping {
    public static void main(String[] args) {
        Config.getConfig("");
        List<String> noList = JdbcQuery.getStringList("select keyword_no from keyword");
        String collectId = "youtube_crawling";
        for(String no : noList){
            CollectKeywordMap map = new CollectKeywordMap();
            map.setCollectId(collectId);
            map.setKeywordNo(Long.parseLong(no));
            JdbcObjects.insertIfNoData(map);
        }
    }
}
