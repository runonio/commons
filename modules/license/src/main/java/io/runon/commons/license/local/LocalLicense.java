package io.runon.commons.license.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.commons.utils.FileUtil;
import com.seomse.crypto.HashConfusionCrypto;
import com.seomse.crypto.HashConfusionString;

import io.runon.commons.license.LicenseUtils;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 라이센스 로컬 인증 관련(서버연결 X)
 * 라이선스 체크에 필요한항목
 *  OS 이름
 * 맥주소
 * 기간
 * host name 은 사용하지 않는다. 테스트할때 4초가량의 시간이 발생 하였다.
 *
 * @author macle
 */
@Slf4j
public class LocalLicense {

    private String licenseFilePath = "license_info";

    public void setLicenseFilePath(String licenseFilePath) {
        this.licenseFilePath = licenseFilePath;
    }

    public LocalLicense(){

    }

    boolean isMacAddress = true;

    public void setMacAddress(boolean macAddress) {
        isMacAddress = macAddress;
    }

    private int  validDays = 365;

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    private Map<String, String> addMap = null;

    /**
     * 검증에 상요할 추가 데이터를 구성한다
     */
    public void addData(String key, String value){
        if(addMap == null){
            addMap = new HashMap<>();
        }

        addMap.put(key, value);
    }

    public String newLicenceStr(){

        JsonObject jsonObject = new JsonObject();

        String osName = System.getProperty("os.name");
        if(osName != null) {
            osName = osName.toLowerCase();

            try {

                if (osName.contains("window")) {
                    jsonObject.addProperty("os_uuid", LicenseUtils.getWinUUID());
                }
            } catch (Exception ignore) {
            }
        }

        if(isMacAddress){
            try{
                String [] macAddressArray = LicenseUtils.getMacAddressArray();
                if(macAddressArray.length > 0){
                    JsonArray jsonArray = new JsonArray();
                    for(String macAddress : macAddressArray){
                        jsonArray.add(macAddress);
                    }
                    jsonObject.add("mac_address", jsonArray);
                }


            }catch (Exception e){
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }

        long time = System.currentTimeMillis();

        jsonObject.addProperty("created_at", time);

        //년월일 (기간)

        jsonObject.addProperty("valid_days", validDays);

        if(addMap != null && addMap.size() > 0){

            //반드시 정렬 시키고 활용 ( 같은 데이터가 생성되어야 한다)
            String [] keys = addMap.keySet().toArray(new String[0]);
            Arrays.sort(keys);

            JsonObject addData = new JsonObject();

            for(String key : keys){
                addData.addProperty(key, addMap.get(key));
            }

            jsonObject.add("add_data", addData);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonText = gson.toJson(jsonObject);

        try {
            String checkSum = getCheckSum(jsonText, time);
            jsonObject.addProperty("check_sum", checkSum);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return gson.toJson(jsonObject);
    }


    public static String getCheckSum(String text, long time) throws NoSuchAlgorithmException {
        return HashConfusionString.get("SHA-512", text + "\n" + LicenseUtils.getDateText(time));
    }


    public void out(String passwd){

        String licenceStr = newLicenceStr();
        String enc = HashConfusionCrypto.encStr(passwd, licenceStr);
        FileUtil.fileOutput(enc, licenseFilePath, false);
    }



    public static void main(String[] args) {
        LocalLicense localLicense = new LocalLicense();
        localLicense.addData("login_id","test");

        long time = System.currentTimeMillis();
        localLicense.out("test");
        System.out.println(System.currentTimeMillis() - time);

//        String [] array = getMacAddressArray();
//        for(String a : array){
//            System.out.println(a);
//        }

    }



}
