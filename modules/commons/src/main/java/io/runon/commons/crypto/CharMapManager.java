package io.runon.commons.crypto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.runon.commons.config.Config;
import io.runon.commons.exception.AlreadyException;
import io.runon.commons.http.HttpApis;
import io.runon.commons.utils.string.Strings;

import java.util.*;

/**
 * 싱글턴으로 사용되지만 개별로 사용할 수 있어서 public 로 설정
 * @author macle
 */
public class CharMapManager {

    private static class Singleton {
        private static final CharMapManager instance = new CharMapManager();
    }

    public static CharMapManager getInstance(){
        return Singleton.instance;
    }

    private final Map<String, CharMap> map = new HashMap<>();
    private final List<CharMap> list = new ArrayList<>();

    public CharMapManager(){

    }

    public void put(CharMap charMap){
        put(charMap.getId(), charMap);
    }

    public void put(String id, CharMap charMap){

        if(map.containsKey(id)){
            throw new AlreadyException("CharMap " + id + " already exists");
        }else{
            if(id == null){
                id = Integer.toString(list.size());
                charMap.setId(id);
            }
            map.put(id, charMap);
            list.add(charMap);
        }
    }

    public CharMap getCharMap(String id){
        return map.get(id);
    }

    public CharMap getRowIdMap(String rowId){
        return getRowIdMap(Strings.sumCharNumbers(rowId));
    }

    public CharMap getRowIdMap(int rowId){
        rowId = Math.abs(rowId);
        return list.get(rowId%list.size());
    }

    public CharMap getRowIdMap(long rowId){

        rowId = Math.abs(rowId);

        int index = (int)(rowId%list.size());
        return list.get(index);
    }

    public Map<String, CharMap> getDataMap() {
        return map;
    }

    @Override
    public String toString(){

        if(map.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();

        Set<String> keys = map.keySet();
        for(String key : keys){
            sb.append("\n").append(key).append(":").append(map.get(key).toString());
        }


        return sb.substring(1);
    }


    public void setCharMap(String apiUrls){
        if(!map.isEmpty()){
            return;
        }
        String result = HttpApis.POST.getMessage(apiUrls);
        String [] lines = result.split("\n");
        setCharMap(lines);
    }

    public void setCharMap(String [] lines){
        if(!map.isEmpty()){
            return;
        }

        Gson gson = new Gson();
        for(String line : lines){
            if("".equals(line)){
                continue;
            }
            line = StringCrypto.DEFAULT_256.dec(line);
            JsonObject jsonObject = gson.fromJson(line, JsonObject.class);
            String id = jsonObject.get("id").getAsString();
            String data = jsonObject.get("data").getAsString();
            CharMap charMap = new CharMap(data);
            charMap.setId(id);
            put(charMap);
        }
    }

}
