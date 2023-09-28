package io.runon.commons.data.service.notify;

import io.runon.commons.data.service.health.ServerHealthCommons;
import io.runon.commons.data.service.redis.Redis;
import org.json.JSONArray;

import java.util.Map;

/**
 * @author macle
 */
public class NotifyCommons {

   public static final String SERVER_PRIORITY_KEY = "notify_service_priority";

   public static void removeNotifyServer(String serverId){
      Redis.removeArrayValue(SERVER_PRIORITY_KEY, serverId);
      Redis.hdelAsync(ServerHealthCommons.CONNECT_TIME_REDIS_KEY, serverId);
   }


   public static boolean isNotifyPriority(String serverId, Map<String, String> serverConnectTimeMap , long errorTime){



      String priorityJsonArrayValue = Redis.getAsync(NotifyCommons.SERVER_PRIORITY_KEY);

      if(priorityJsonArrayValue == null){
         // redis 서버 오류인경우
         return false;
      }

      if(serverConnectTimeMap == null){
         serverConnectTimeMap = Redis.hgetall(ServerHealthCommons.CONNECT_TIME_REDIS_KEY);
      }

      boolean isNotify = false;

      //내가 알림 서버인경우 체크
      JSONArray jsonArray = new JSONArray(priorityJsonArrayValue);
      for (int i = 0; i < jsonArray.length(); i++) {
         String id = jsonArray.getString(i);
         if (serverId.equals(id)) {
            isNotify = true;
            break;
         }

         long connectTime = 0;

         String timeValue = serverConnectTimeMap.get(id);
         if (timeValue != null) {
            connectTime = Long.parseLong(timeValue);
         }

         if (System.currentTimeMillis() - connectTime <= errorTime) {
            //우선순위가 높은 정상적으로 동작하는 서버가 있음.
            break;
         }
      }

      return isNotify;
   }

}
