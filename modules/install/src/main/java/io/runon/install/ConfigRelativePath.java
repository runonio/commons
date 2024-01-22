package io.runon.install;

import io.runon.install.utils.FileUtils;
import io.runon.install.utils.XmlUtils;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * 다른 라이브러리를 최대한 참조하지 않기위해 직접 구현한 구현체 위주로 사용해야한다.
 * @author macle
 */
public class ConfigRelativePath {

    private String relativeConfigPath ="home-relative-config.xml";

    private String configPath = "config/config.xml";

    private final String homedirPath;
    public ConfigRelativePath(String homedirPath){
        this.homedirPath = homedirPath;
    }

    public void setRelativeConfigPath(String relativeConfigPath) {
        this.relativeConfigPath = relativeConfigPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void change(){

        File relativeConfigFile = new File(relativeConfigPath);
        if(!relativeConfigFile.isFile()){
            return;
        }
        Properties relative = new Properties();
        try( InputStream configInputStream = new FileInputStream(relativeConfigPath)){
            relative.loadFromXML(configInputStream);
            Set<Object> keys = relative.keySet();
            for(Object objKey : keys){
                String key = (String) objKey;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(relative.size() == 0){
            return;
        }

        File configFile = new File(configPath);
        if(!configFile.isFile()){
            return;
        }

        String configText = FileUtils.getFileContents( configFile ,"UTF-8");

        String dirSeparator ="/";
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            dirSeparator = "\\";
        }

        String homedirNext = this.homedirPath;

        if( !(homedirNext.endsWith("/") || homedirNext.endsWith("\\")) ){
            homedirNext = homedirNext + dirSeparator;
        }

        StringBuilder sb = new StringBuilder();
        String [] lines = configText.split("\n");
        for(String line : lines){
            KeyValue keyValue = XmlUtils.getKeyValue(line);
            if(keyValue == null){
                sb.append("\n").append(line);
            }else{
                if(relative.containsKey(keyValue.getKey())){
                    sb.append("\n").append("\t");
                    XmlUtils.appendLine(sb, keyValue.getKey(), homedirNext + relative.getProperty(keyValue.getKey()));
                }else{
                    sb.append("\n").append(line);
                }
            }
        }

        if(sb.length() > 0) {
            FileUtils.fileOutput(sb.substring(1), configPath, false);
            sb.setLength(0);
        }
    }


}
