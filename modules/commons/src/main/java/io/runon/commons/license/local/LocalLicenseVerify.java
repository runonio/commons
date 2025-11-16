package io.runon.commons.license.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.runon.commons.utils.ExceptionUtils;
import io.runon.commons.utils.FileUtils;
import io.runon.commons.utils.time.YmdUtils;

import io.runon.commons.crypto.HashConfusionCryptos;
import io.runon.commons.license.LicenseUtils;
import io.runon.commons.license.LicenseVerify;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 로컬 라이센스 검증
 * @author macle
 */
@Slf4j
public class LocalLicenseVerify {


    public static LicenseVerify verifyToPath(String FilePath, String key, Map<String,String > addDataMap){
        String encMessage = FileUtils.getFileContents(new File(FilePath),"UTF-8");
        return verify(encMessage, key, addDataMap);
    }

    public static LicenseVerify verify(String encMessage, String key, Map<String,String > addDataMap){
        try {
            String jsonText = HashConfusionCryptos.decStr(key, encMessage);
            return verify(jsonText, addDataMap);
        }catch (Exception e){
            return LicenseVerify.DECRYPTION_ERROR;
        }
    }

    public static LicenseVerify verify(String jsonText, Map<String,String > addDataMap){

        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
            //체크섬 체크

            String checkSum = jsonObject.remove("check_sum").getAsString();

            long time = jsonObject.get("created_at").getAsLong();
            String jsonValue = gson.toJson(jsonObject);

            String check = LocalLicense.getCheckSum(jsonValue, time);
            if(!checkSum.equals(check)){
                return LicenseVerify.DATA_VALIDATION_ERROR;
            }

            if(jsonObject.has("os_uuid")){
                String osId = jsonObject.get("os_uuid").getAsString();

                String osName = System.getProperty("os.name");
                if(osName == null){
                    return LicenseVerify.OS_UUID_ERROR;
                }

                osName = osName.toLowerCase();

                if(!osName.contains("window")){
                    return LicenseVerify.OS_UUID_ERROR;
                }

                String uuid = LicenseUtils.getWinUUID();
                if(uuid == null){
                    return LicenseVerify.OS_UUID_ERROR;
                }
                if(!uuid.equals(osId)){
                    return LicenseVerify.OS_UUID_ERROR;
                }
            }

            int validDays = jsonObject.get("valid_days").getAsInt();

            if(validDays > -1){
                int nowYmd = Integer.parseInt(YmdUtils.now());
                String createYmd = YmdUtils.getYmd(time);
                int lastYmd = Integer.parseInt(YmdUtils.getYmd(createYmd, validDays));

                if(nowYmd > lastYmd){
                    return LicenseVerify.EXPIRED_ERROR;
                }
            }

            if(jsonObject.has("mac_address")){

                JsonArray array = jsonObject.get("mac_address").getAsJsonArray();
                Set<String> checkSet = new HashSet<>();

                for (int i = 0; i <array.size() ; i++) {
                    checkSet.add(array.get(i).getAsString());
                }

                if(checkSet.size() > 0){
                    String [] macArray = LicenseUtils.getMacAddressArray();

                    boolean isData = false;

                    for(String mac : macArray){
                        if(checkSet.contains(mac)){
                            isData= true;
                            break;
                        }
                    }

                    if(!isData){
                        return LicenseVerify.MAC_ADDRESS_ERROR;
                    }
                }

                if(addDataMap != null && addDataMap.size() > 0){
                    if(!jsonObject.has("add_data")){
                        return LicenseVerify.ADD_DATA_VALIDATION_ERROR;
                    }

                    JsonObject addDataObject = jsonObject.get("add_data").getAsJsonObject();
                    Set<String> keys = addDataMap.keySet();

                    for(String key : keys){
                        String value = addDataMap.get(key);

                        if(!addDataObject.has(key)){
                            return LicenseVerify.ADD_DATA_VALIDATION_ERROR;
                        }

                        String checkValue = addDataObject.get(key).getAsString();

                        if(!checkValue.equals(value)){
                            return LicenseVerify.ADD_DATA_VALIDATION_ERROR;
                        }

                    }
                }

            }

        }catch (Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
            return LicenseVerify.EXCEPTION;

        }

        return LicenseVerify.SUCCESS;
    }


    public static void main(String[] args) {

        Map<String, String> addDataMap = new HashMap<>();
        addDataMap.put("login_id","test");
        System.out.println(verifyToPath("license_info","test", addDataMap));

    }

}
