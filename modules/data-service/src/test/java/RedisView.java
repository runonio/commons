import io.runon.commons.data.service.notify.NotifyGmail;
import io.runon.commons.data.service.redis.Redis;

import java.util.Map;

/**
 * redis 데이터가 잘 안보여서 가져다 보기위한 유틸성
 * @author macle
 */
public class RedisView {
    public static void main(String[] args) {
        String value = Redis.get("notify_service_priority");
        System.out.println(value);

        Map<String, String> map = Redis.hgetall("server_health_last_connect_time");
        System.out.println(map.size());

        String mails = Redis.get(NotifyGmail.NOTIFY_MAILS_REDIS_KEY);
        System.out.println(mails);


    }
}
