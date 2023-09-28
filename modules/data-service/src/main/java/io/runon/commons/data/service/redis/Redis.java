package io.runon.commons.data.service.redis;

import io.lettuce.core.RedisFuture;
import org.json.JSONArray;

import java.util.Map;

/**
 * @author macle
 */
@SuppressWarnings("UnusedReturnValue")
public class Redis {

    public static RedisFuture<Long> publish(String channel, String message){
        return ServiceRedis.instance.publish(channel,message);
    }
    /**
     * 동기
     */
    public static Map<String, String> hgetall(String key){
        return ServiceRedis.instance.hgetall(key);
    }


    /**
     * 동기
     */
    public static Boolean hset(String key, String field, String value){
        return ServiceRedis.instance.hset(key, field, value);
    }

    /**
     * 비동기
     */
    public static Map<String, String> hgetallAsync(String key){
        return ServiceRedis.instance.hgetallAsync(key);
    }
    /**
     * 동기
     */
    public static String get(String key){
        return ServiceRedis.instance.get(key);
    }

    /**
     * 동기
     */
    public static void set(String key, String value) {
        ServiceRedis.instance.set(key, value);
    }

    public static String getAsync(String key) {
        return ServiceRedis.instance.getAsync(key);
    }




    public static RedisFuture<Boolean> hsetAsync(String key, String field, String value){
        return ServiceRedis.instance.hsetAsync(key,field,value);
    }

    public static RedisFuture<Long> hsetAsync(String key, Map<String, String> map){
        return ServiceRedis.instance.hsetAsync(key, map);
    }

    public static RedisFuture<Long> hdelAsync(String key,  String... fields){
        return ServiceRedis.instance.hdelAsync(key, fields);
    }


    public static RedisFuture<String> setAsync(String key, String value){
        return ServiceRedis.instance.setAsync(key, value);

    }


    public static boolean removeArrayValue(String key, String remove){
        String mailsValue = Redis.get(key);
        if(mailsValue == null){
            return false;
        }

        JSONArray jsonArray = new JSONArray(mailsValue);
        JSONArray newArray = new JSONArray();
        for (int i = 0; i <jsonArray.length() ; i++) {
            String value = jsonArray.getString(i);
            if(value.equals(remove)){
                continue;
            }
            newArray.put(value);
        }

        if(jsonArray.length() != newArray.length()){
            Redis.set(key, newArray.toString());
            return true;
        }

        return false;

    }

}
