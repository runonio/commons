package io.runon.commons.data.service.redis;

import io.runon.commons.data.service.DataServiceYml;
import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * RedisClient, RedisClusterClient 를 활용한  RedisConnect 생성
 * @author macle
 */
@Slf4j
public class RedisConnectFactory {

    /**
     * 설정파일을 읽어서 RedisConnect 정보생성
     * @return RedisConnect
     */
    public static RedisConnect newRedisConnect(){
        //여기에 클러스터 or 클라이언트 어떤걸로 탈지 소스 넣기


        Map<String, Object> redisMap = DataServiceYml.getYmlMap("redis");
        try{
            if((Boolean)redisMap.get("cluster")){
                return new RedisConnectCluster(redisMap);
            }else{
                return new RedisConnectClient(redisMap);
            }
        }catch(Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
            return new RedisConnectClient(redisMap);
        }

    }

    public static void main(String[] args) {
        System.out.println( newRedisConnect().getClass().getName());

    }

}
