import com.seomse.commons.config.Config;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.jdbc.objects.JdbcObjects;
import io.runon.commons.keyword.CollectKeywordMap;

import java.util.List;

/**
 * @author macle
 */
public class CollectGroupKeywordMapping {
    public static void main(String[] args) {
        Config.getConfig("");

        String collectId ="daum_cafe_search_crawling";

        String groupId = "insurance";

        List<String> list = JdbcQuery.getStringList("select keyword_no from collect_group_keyword_map where collect_group_id='" + groupId +"'");

        for(String no : list){
            CollectKeywordMap map = new CollectKeywordMap();
            map.setCollectId(collectId);
            map.setKeywordNo(Long.parseLong(no));
            JdbcObjects.insertIfNoData(map);
        }
    }
}
