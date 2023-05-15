package io.runon.commons.data.service.property;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.commons.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author macle
 */
@Slf4j
public class JsonFileProperties {

    private final String filePath;

    private final JsonObject jsonObject;

    private final Gson gson =  new GsonBuilder().setPrettyPrinting().create();

    private final Object lock = new Object();

    public JsonFileProperties(String filePath) {

        this.filePath = filePath;

        File file = new File(filePath);
        JsonObject jsonObj;
        if(file.isFile()){
            String fileValue = FileUtil.getFileContents(file, "UTF-8");
            try{
                jsonObj = gson.fromJson(fileValue, JsonObject.class);
            }catch(Exception e){
                jsonObj = new JsonObject();
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }else{
            jsonObj = new JsonObject();
        }
        this.jsonObject = jsonObj;
    }

    public void set(String key, String value){
        synchronized (lock) {

            jsonObject.addProperty(key, value);

            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }

    public void set(String key, Number value){
        synchronized (lock) {
            jsonObject.addProperty(key, value);

            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }

    public void set(String key, long value){
        synchronized (lock) {
            jsonObject.addProperty(key, value);

            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }

    public void set(String key, int value){
        synchronized (lock) {
            jsonObject.addProperty(key, value);

            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }

    public void set(String key, double value){
        synchronized (lock) {
            jsonObject.addProperty(key, value);

            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }

    public void remove(String key){
        synchronized (lock) {
            jsonObject.remove(key);
            String jsonValue = gson.toJson(jsonObject);
            FileUtil.fileOutput(jsonValue, filePath, false);
        }
    }


    public String getString(String key){
        synchronized (lock) {
           return jsonObject.get(key).getAsString();
        }
    }

    public BigDecimal getBigDecimal(String key){
        synchronized (lock) {
            return jsonObject.get(key).getAsBigDecimal();
        }
    }

    public int getInt(String key, int defaultValue){
        synchronized (lock) {
            if(!jsonObject.has(key)){
                return defaultValue;
            }
            return jsonObject.get(key).getAsInt();
        }
    }

    public long getLong(String key, long defaultValue){
        synchronized (lock) {
            if(!jsonObject.has(key)){
                return defaultValue;
            }

            return jsonObject.get(key).getAsLong();
        }
    }

    public double getDouble(String key, double  defaultValue){
        synchronized (lock) {
            if(!jsonObject.has(key)){
                return defaultValue;
            }
            return jsonObject.get(key).getAsDouble();
        }
    }

    public JsonObject getJsonObject(String key){
        synchronized (lock) {
            return jsonObject.get(key).getAsJsonObject();
        }
    }

    public String getJsonValue(){
        synchronized (lock) {
            return gson.toJson(jsonObject);
        }
    }

}
