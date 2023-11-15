package io.runon.commons.charmap.service;

import com.seomse.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author macle
 */
@Slf4j
@RestController
public class CharMapController {


    @RequestMapping(value = "/charmap/maps" , method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getCharMaps(@RequestBody final String jsonValue) {
        try{
            CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
            return charMapDataManagement.getLastData();
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
            return "";
        }
    }


}
