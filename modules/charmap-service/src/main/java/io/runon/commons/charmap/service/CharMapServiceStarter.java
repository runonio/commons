package io.runon.commons.charmap.service;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigSet;
import io.runon.commons.utils.FileUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

/**
 * @author macle
 */
@SpringBootApplication(scanBasePackages = {"io.runon"})
public class CharMapServiceStarter {

    public static void startApp(){
        int port = Config.getInteger("collect.content.port", 31315);
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", port);
        props.put("logging.config", ConfigSet.LOG_BACK_PATH);
        String [] springbootArgs = new String[0];

        CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();

        String filePath = Config.getConfig("application.crypto.charmap.path",ConfigSet.CONFIG_DIR_PATH+"/charmaps");

        if(!FileUtil.isFile(filePath)){
            charMapDataManagement.addRandomCharMap(0,4);
        }

        new SpringApplicationBuilder()
                .sources(CharMapServiceStarter.class)
                .properties(props)
                .run(springbootArgs);

    }



    public static void main(String[] args) {

        startApp();
    }
}
