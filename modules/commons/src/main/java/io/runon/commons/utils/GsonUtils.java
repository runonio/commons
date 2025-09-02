package io.runon.commons.utils;

import com.google.gson.*;
import io.runon.commons.exception.ReflectiveOperationRuntimeException;
import io.runon.commons.utils.packages.classes.field.FieldUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author macle
 */
public class GsonUtils {

    public static final Gson GSON = new Gson();
    public static final Gson LOWER_CASE_WITH_UNDERSCORES_PRETTY =  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    public static final Gson LOWER_CASE_WITH_UNDERSCORES =  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    public static final Gson PRETTY =  new GsonBuilder().setPrettyPrinting().create();


    public static String toJson(Object obj){
        return GSON.toJson(obj);
    }

    public static JsonObject fromJsonObject(String jsonText){
        if(jsonText == null ){
            return new JsonObject();
        }
        jsonText = jsonText.trim();
        if(jsonText.trim().isEmpty()){
            return new JsonObject();
        }

        return GSON.fromJson(jsonText, JsonObject.class);
    }


    public static JsonArray fromJsonArray(String jsonText){
        return GSON.fromJson(jsonText, JsonArray.class);
    }

    public static JsonArray getArray(String [] array){

        if(array == null){
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for(String value : array){
            jsonArray.add(value);
        }
        return jsonArray;
    }


    public static JsonArray getArray(int [] array){

        if(array == null){
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for(int value : array){
            jsonArray.add(value);
        }
        return jsonArray;
    }


    public static JsonArray getArray(double [] array){

        if(array == null){
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for(double value : array){
            jsonArray.add(value);
        }
        return jsonArray;
    }

    public static JsonArray getArray(long [] array){

        if(array == null){
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for(long value : array){
            jsonArray.add(value);
        }
        return jsonArray;
    }


    public static JsonArray getArray(Number[] array){

        if(array == null){
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for(Number value : array){
            jsonArray.add(value);
        }
        return jsonArray;
    }

    public static String [] getString(JsonArray jsonArray){

        String [] array = new String[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsString();
        }

        return array;
    }

    public static int [] getInts(JsonArray jsonArray){

        int [] array = new int[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsInt();
        }

        return array;
    }

    public static double [] getDoubles(JsonArray jsonArray){

        double [] array = new double[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsDouble();
        }

        return array;
    }

    public static long [] getLongs(JsonArray jsonArray){

        long [] array = new long[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsLong();
        }

        return array;
    }

    public static Number [] getNumbers(JsonArray jsonArray){

        Number [] array = new Number[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsNumber();
        }

        return array;
    }

    public static BigDecimal [] getBigDecimals(JsonArray jsonArray){

        BigDecimal [] array = new BigDecimal[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsBigDecimal();
        }

        return array;
    }

    public static BigInteger[] getBigIntegers(JsonArray jsonArray){

        BigInteger [] array = new BigInteger[jsonArray.size()];
        for (int i = 0; i <array.length ; i++) {
            array[i] = jsonArray.get(i).getAsBigInteger();
        }

        return array;
    }

    public static void mergeJsonField(String fieldName, Object source, Object target) {
        try {
            Field[] fields = FieldUtil.getFieldArrayToAllParents(source.getClass());
            for (Field field : fields) {
                if (!field.getName().equals(fieldName)) {
                    continue;
                }

                field.setAccessible(true);

                Object sourceObj = field.get(source);
                if (sourceObj == null) {
                    return;
                }

                String sourceJson = (String) sourceObj;
                if (!sourceJson.startsWith("{")) {
                    //json 형태가 아니면 업데이트 하지 않음
                    return;
                }

                Object targetObj = field.get(target);
                if (targetObj == null) {
                    return;
                }

                String targetJson = (String) targetObj;
                if (!targetJson.startsWith("{")) {
                    //json 형태가 아니면 업데이트 하지 않음
                    return;
                }

                if (sourceJson.equals(targetJson)) {
                    return;
                }


                Set<String> mergeKeySet = new LinkedHashSet<>();

                JsonObject sourceJsonObj = GsonUtils.fromJsonObject(sourceJson);
                JsonObject targetJsonObj = GsonUtils.fromJsonObject(targetJson);


                mergeKeySet.addAll(sourceJsonObj.keySet());
                mergeKeySet.addAll(targetJsonObj.keySet());

                String [] keys = mergeKeySet.toArray(new String[0]);
                Arrays.sort(keys);

                JsonObject mergeObject = new JsonObject();
                for(String key : keys){
                    if(targetJsonObj.has(key)){
                        mergeObject.add(key, targetJsonObj.get(key));
                    }else{
                        mergeObject.add(key, sourceJsonObj.get(key));
                    }
                }

                field.set(target,  GsonUtils.toJson(mergeObject));
            }
        }catch (IllegalAccessException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    public static String updateJsonObjStr(String json, String key, String value){
        return updateJsonObjStr(json,key,value,PRETTY);
    }




    public static String updateJsonObjStr(String json, String key, String value, Gson gson){
        JsonObject object;
        if(json == null){
            object = new JsonObject();
        }else{
            object = gson.fromJson(json, JsonObject.class);
        }

        object.addProperty(key, value);

        return gson.toJson(object);
    }


    public static String updateJsonObjStr(String json, JsonObject updateObj){
         return updateJsonObjStr(json, updateObj, PRETTY);
    }

    public static String updateJsonObjStr(String json, JsonObject updateObj, Gson gson){
        JsonObject object;
        if(json == null){
            object = updateObj;
        }else{
            object = gson.fromJson(json, JsonObject.class);
            updateObj.entrySet().forEach(entry -> {
                object.add(entry.getKey(), entry.getValue());
            });
        }
        return gson.toJson(object);
    }


}
