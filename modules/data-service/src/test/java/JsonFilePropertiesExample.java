import io.runon.commons.data.service.property.JsonFileProperties;
import io.runon.commons.data.service.property.JsonFilePropertiesManager;

/**
 * @author macle
 */
public class JsonFilePropertiesExample {
    public static void set(){
        JsonFileProperties jsonFileProperties = JsonFilePropertiesManager.getInstance().getJsonFileProperties("config/collect_time");
        jsonFileProperties.set("A", System.currentTimeMillis());
        jsonFileProperties.set("B", System.currentTimeMillis());
    }
    public static void view(){
        JsonFileProperties jsonFileProperties = JsonFilePropertiesManager.getInstance().getJsonFileProperties("config/collect_time");
        System.out.println(jsonFileProperties.getLong("A"));
        System.out.println(jsonFileProperties.getLong("B"));
    }

    public static void main(String[] args) {
        view();
    }
}
