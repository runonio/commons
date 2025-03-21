
package io.runon.commons.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * xml로 읽어 오는 설정 정보
 * @author macle
 */
public class XmlFileConfigData extends ConfigData{

    private final Properties props;

    /**
     * 생성자
     * @param file 설정파일
     * @throws IOException exception
     */
    XmlFileConfigData(File file) throws IOException {
        props = new Properties();
        if(file.exists()) {

            InputStream configInputStream = null;
            //noinspection TryFinallyCanBeTryWithResources,CaughtExceptionImmediatelyRethrown
            try {
                configInputStream = new FileInputStream(file);
                props.loadFromXML(configInputStream);
            } catch (IOException e) {

                throw e;
            } finally {
                if (configInputStream != null) {
                    configInputStream.close();
                }
            }
        }
    }


    @Override
    public String getConfig(String key) {
        return props.getProperty(key);
    }

    @Override
    public boolean containsKey(String key) {
        return props.containsKey(key);
    }



    @Override
    public int getPriority() {
        return ConfigSet.XML_FILE_PRIORITY;
    }

    @Override
    public void put(String key, String value) {
        props.put(key, value);
    }

    @Override
    public String remove(String key) {
        Object obj =props.remove(key);

        if(obj == null){
            return null;
        }
        if(obj.getClass() == String.class){
            return (String) obj;
        }

        return obj.toString();
    }


}
