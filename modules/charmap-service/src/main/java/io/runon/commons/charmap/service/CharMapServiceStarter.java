package io.runon.commons.charmap.service;

import com.seomse.commons.config.Config;
import com.seomse.commons.config.ConfigSet;
import com.seomse.commons.utils.FileUtil;
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

        String filePath = Config.getConfig("application.crypto.charmap.path","config/charmaps");

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
