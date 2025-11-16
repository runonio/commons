package io.runon.apis.boot;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigSet;
import io.runon.commons.crypto.CharMapDataManagement;
import io.runon.commons.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * spring scan을 app별로 정하기위해 패키지를 분리합니다.
 * @author macle
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {})
public class RunonBootApp {

    public static void start(){
        String [] scanBases = {
                "io.runon.apis.http"
        };

        start(scanBases);
    }

    public static void start(String[] scanBases ){
        int port = Config.getInteger("service.port", 30350);
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", port);
        props.put("logging.config", ConfigSet.LOG_BACK_PATH);

        String size = Config.getConfig("spring.servlet.multipart.max-file-size");
        if(size != null){
            props.put("spring.servlet.multipart.max-file-size", size);
            props.put("spring.servlet.multipart.max-request-size", size);
        }

        if(Config.getBoolean("crypto.service.flag", false)){
            CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
            String filePath = Config.getConfig("crypto.charmap.path",ConfigSet.CONFIG_DIR_PATH+"/charmaps");
            if(!FileUtils.isFile(filePath)){
                charMapDataManagement.addRandomCharMap(0, (Config.getInteger("crypto.charmap.length", 10) -1));
            }
        }


        String [] springbootArgs = new String[0];

        SpringApplication app = new SpringApplication(RunonBootApp.class);
        app.setDefaultProperties(props);

        for (int i = 0; i < scanBases.length; i++) {
            scanBases[i] = scanBases[i].trim();
        }

        app.addInitializers(applicationContext -> {
            if (applicationContext instanceof BeanDefinitionRegistry registry) {
                ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
                scanner.scan(scanBases);
            }
        });


        //boot 실행
        ApplicationContext ctx = app.run(springbootArgs);
        Map<String, Object> controllers = ctx.getBeansWithAnnotation(RestController.class);
        controllers.forEach((name, bean) -> log.debug("load controller: {}", bean.getClass().getName()));

        log.info("service port: " + port);
    }

    public static void main(String[] args) {
       start();
    }
}
