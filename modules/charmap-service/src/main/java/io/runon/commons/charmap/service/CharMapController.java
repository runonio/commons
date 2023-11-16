package io.runon.commons.charmap.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.crypto.CharMap;
import com.seomse.crypto.CharMapManager;
import com.seomse.crypto.StringCrypto;
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
    public String getCharMaps() {
        try{
            CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
            return charMapDataManagement.getLastData();
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
            return "";
        }
    }


    @RequestMapping(value = "/charmap/id" , method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getCharMap(@RequestBody final String jsonValue) {
        try{


            JsonObject jsonObject = new Gson().fromJson(jsonValue, JsonObject.class);

            CharMapManager charMapManager = CharMapManager.getInstance();

            String id = jsonObject.get("id").getAsString();


            CharMap charMap = charMapManager.getCharMap(id);

            if(charMap == null){
                return "";
            }

            String str = CharMap.outMap(charMap.getMap());
            return StringCrypto.DEFAULT_256.enc(str);
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
            return "";
        }
    }

}
