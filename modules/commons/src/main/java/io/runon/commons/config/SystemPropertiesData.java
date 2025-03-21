
package io.runon.commons.config;

/**
 * 시스템 설정 정보
 * @author macle
 */
public class SystemPropertiesData extends ConfigData{

    @Override
    public void put(String key, String value) {
        System.setProperty(key, value);
    }

    @Override
    public String remove(String key) {
        //시스템 설정은 삭제하지 않음
        return null;
    }

    @Override
    public String getConfig(String key) {
        return System.getProperties().getProperty(key);
    }

    @Override
    public boolean containsKey(String key) {
        return System.getProperties().containsKey(key);
    }



    @Override
    public int getPriority() {
        return ConfigSet.SYSTEM_PROPERTIES_PRIORITY;
    }
}
