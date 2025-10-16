package io.runon.apis.http;

import io.runon.commons.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author macle
 */
@Slf4j
@RestController
public class CommonsController {
    @GetMapping(value = "/commons/config" )
    public String getConfig(@RequestParam("key") String key) {
        return Config.getConfig(key);
    }

}
