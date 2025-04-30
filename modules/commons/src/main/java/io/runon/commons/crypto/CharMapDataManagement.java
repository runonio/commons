package io.runon.commons.crypto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.runon.commons.config.Config;
import io.runon.commons.utils.FileUtil;
import io.runon.commons.utils.string.Check;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @author macle
 */
public class CharMapDataManagement {

    private static class Singleton {
        private static final CharMapDataManagement instance = new CharMapDataManagement();
    }

    public static CharMapDataManagement getInstance(){
        return Singleton.instance;
    }

    private long lastSeq = 1;

    private String lastData;

    private CharMapDataManagement(){
        loadData();
    }

    private final Object lock = new Object();

    public void loadData(){
        String filePath = Config.getConfig("application.crypto.charmap.path","config/charmaps");

        if(!FileUtil.isFile(filePath)){
            return ;
        }

        String fileStr = FileUtil.getFileContents(new File(filePath), "utf-8");
        loadData(fileStr);

        lastData = fileStr;

        Map<String, CharMap> map  = CharMapManager.getInstance().getDataMap();

        if(map.isEmpty()){
            return;
        }

        Set<String> keys = map.keySet();

        for(String key : keys){
            if(Check.isNumber(key)){
                long seq = Long.parseLong(key);
                if(lastSeq < (seq+1)){
                    lastSeq = seq + 1;
                }
            }
        }
    }

    public void addRandomCharMap(){

        String id = Long.toString(lastSeq);
        lastSeq++;
        addCharMap(id,  new CharMap(CharMap.makeRandomMap()));
    }

    public void addRandomCharMap(long startNum, long endNum){
        String filePath = Config.getConfig("application.crypto.charmap.path", "config/charmaps");
        CharMapManager charMapManager = CharMapManager.getInstance();

        synchronized (lock) {
            for (long i = startNum; i <= endNum; i++) {
                charMapManager.put(Long.toString(i), new CharMap(CharMap.makeRandomMap()));
            }

            if (lastSeq < endNum + 1) {
                lastSeq = endNum + 1;
            }

            String str = outStr();
            FileUtil.fileOutput(str, filePath, false);

            lastData = str;
        }

    }

    public void addCharMap(String id, CharMap charMap){
        String filePath = Config.getConfig("application.crypto.charmap.path");
        CharMapManager charMapManager = CharMapManager.getInstance();
        synchronized (lock) {
            charMapManager.put(id, charMap);
            String str = outStr();
            FileUtil.fileOutput(str, filePath, false);

            lastData = str;
        }
    }

    public String outStr(){
        CharMapManager charMapManager = CharMapManager.getInstance();

        Map<String, CharMap> map = charMapManager.getDataMap();

        if(map.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        Gson gson = new Gson();

        Set<String> ids = map.keySet();
        for(String id: ids){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("data", map.get(id).toString());
            sb.append("\n").append(StringCrypto.DEFAULT_256.enc(gson.toJson(jsonObject)));
        }
        return sb.substring(1);
    }

    public long getLastSeq() {
        return lastSeq;
    }

    public static void loadData(String encData){
        String [] lines = encData.split("\n");
        CharMapManager.getInstance().setCharMap(lines);
    }

    public String getLastData() {
        return lastData;
    }

}
