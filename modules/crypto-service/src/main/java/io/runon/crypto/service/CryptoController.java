package io.runon.crypto.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.runon.commons.crypto.*;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @PostMapping(value = "/crypto/dec/bytes/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] decBytes(@RequestPart("file") MultipartFile file, @RequestPart("key") String message) throws IOException {

        // bytes 처리
        return Cryptos.decByte(file.getBytes(), message);
    }

    @RequestMapping(value = "/crypto/dec/text" , method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public String decBytes(@RequestBody final String jsonValue){
        JsonObject jsonObject = new Gson().fromJson(jsonValue, JsonObject.class);
        // bytes 처리
        return Cryptos.decStr(jsonObject.get("text").getAsString(), jsonObject.get("key").getAsString());
    }



}
