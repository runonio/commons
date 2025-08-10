package io.runon.crypto.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.runon.commons.crypto.CharMapDataManagement;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.crypto.CharMap;
import io.runon.commons.crypto.CharMapManager;
import io.runon.commons.crypto.StringCrypto;
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
public class CryptoController {


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
