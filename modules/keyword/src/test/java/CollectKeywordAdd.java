import com.seomse.jdbc.objects.JdbcObjects;
import io.runon.commons.keyword.CollectKeywordMap;
import io.runon.commons.keyword.Keywords;

/**
 * @author macle
 */
public class CollectKeywordAdd {

    public static void main(String[] args) {

        String collectId = "inven_search_crawling";
        String keyword ="주식";
        long number = Keywords.save(keyword, null).getNumber();

        CollectKeywordMap map = new CollectKeywordMap();
        map.setCollectId(collectId);
        map.setKeywordNo(number);
        JdbcObjects.insertIfNoData(map);
    }
}
