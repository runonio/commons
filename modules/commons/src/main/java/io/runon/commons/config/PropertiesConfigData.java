
package io.runon.commons.config;

import java.util.Properties;

/**
 * config data 기본형
 * 아무것도 설정되지 않았을때 (임의 메모리만 사용할 떄)
 * @author macle
 */
public class PropertiesConfigData extends ConfigData{
    
    private final Properties properties = new Properties();

    private int priority = ConfigSet.XML_FILE_PRIORITY+1;


    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String getConfig(String key) {
        return properties.getProperty(key);
    }

    @Override
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void put(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String remove(String key) {
        Object obj = properties.remove(key);

        if(obj == null){
            return null;
        }
        if(obj.getClass() == String.class){
            return (String) obj;
        }

        return obj.toString();
    }
}
