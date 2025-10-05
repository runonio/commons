package io.runon.apis.http;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigSet;
import io.runon.commons.crypto.CharMapDataManagement;
import io.runon.commons.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

/**
 * @author macle
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"io.runon"})
public class HttpApis {

    public static void startApp(){
        int port = Config.getInteger("service.port",  31315);
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", port);
        props.put("logging.config", ConfigSet.LOG_BACK_PATH);
        String [] springbootArgs = new String[0];

        String size = Config.getConfig("spring.servlet.multipart.max-file-size");
        if(size != null){
            props.put("spring.servlet.multipart.max-file-size", size);
            props.put("spring.servlet.multipart.max-request-size", size);
        }

        if(Config.getBoolean("crypto.service.flag", false)){
            CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
            String filePath = Config.getConfig("crypto.charmap.path",ConfigSet.CONFIG_DIR_PATH+"/charmaps");
            if(!FileUtil.isFile(filePath)){
                charMapDataManagement.addRandomCharMap(0, (Config.getInteger("crypto.charmap.length", 10) -1));
            }
        }
        new SpringApplicationBuilder()
                .sources(HttpApis.class)
                .properties(props)
                .run(springbootArgs);
    }

    public static void main(String[] args) {
        startApp();
    }

}
