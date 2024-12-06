import io.runon.commons.utils.time.Times;
import io.runon.commons.data.service.notify.NotifyGmail;
import io.runon.commons.data.service.redis.Redis;

import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

/**
 * redis 데이터가 잘 안보여서 가져다 보기위한 유틸성
 * @author macle
 */
public class RedisView {
    public static void main(String[] args) {
        String value = Redis.get("notify_service_priority");
        System.out.println(value);

        Map<String, String> map = Redis.hgetall("server_health_last_connect_time");
        Set<String> serverIds = map.keySet();
        for(String serverId :  serverIds){
            System.out.println(serverId + ", last connect: " + Times.ymdhm(Long.parseLong(map.get(serverId)), ZoneId.of("Asia/Seoul")));
        }

        String mails = Redis.get(NotifyGmail.NOTIFY_MAILS_REDIS_KEY);
        System.out.println(mails);

    }
}
