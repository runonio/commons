package io.runon.commons.data.service.property;

import java.util.HashMap;
import java.util.Map;

/**
 * @author macle
 */
public class JsonFilePropertiesManager {

    private static class Singleton {
        private static final JsonFilePropertiesManager instance = new JsonFilePropertiesManager();
    }
    public static JsonFilePropertiesManager getInstance(){
        return Singleton.instance;
    }

    private final Object lock = new Object();

    private final Map<String, JsonFileProperties> pathMap = new HashMap<>();


    public JsonFileProperties getJsonFileProperties(String filePath){
        synchronized (lock){
            JsonFileProperties jsonFileProperties = pathMap.get(filePath);
            if(jsonFileProperties == null){
                jsonFileProperties = new JsonFileProperties(filePath);
                pathMap.put(filePath, jsonFileProperties);
            }

            return jsonFileProperties;
        }
    }

}
