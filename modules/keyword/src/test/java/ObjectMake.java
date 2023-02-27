
import com.seomse.commons.config.Config;
import com.seomse.jdbc.connection.ApplicationConnectionPool;
import com.seomse.jdbc.objects.JdbcObjects;

/**
 * @author macle
 */
public class ObjectMake {

    public static void main(String[] args) {

        Config.getConfig("");
        //noinspection ResultOfMethodCallIgnored
        ApplicationConnectionPool.getInstance();

        String tableName = "collect_group_keyword_map";
        System.out.println("class make info");
        System.out.println(JdbcObjects.makeObjectValue(tableName));

    }
}
