package io.runon.commons.data.service.health;

import io.runon.commons.config.Config;
import io.runon.commons.service.Service;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.time.Times;
import io.runon.commons.data.service.redis.Redis;
import lombok.extern.slf4j.Slf4j;

/**
 * 서버 헬스 체크 서비스
 * @author macle
 */
@Slf4j
public class ServerConnectTimeRecordService extends Service {


    private final String serverId = Config.getConfig("server.id");

    public ServerConnectTimeRecordService(){
        setSleepTime(Config.getLong("server.health.connect.time.service.sleep.time", Times.MINUTE_3));
        setState(Service.State.START);
    }


    @Override
    public void work() {
        try{
            Redis.hsetAsync(ServerHealthCommons.CONNECT_TIME_REDIS_KEY, serverId, Long.toString(System.currentTimeMillis()));
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    public static void main(String[] args) {
        new ServerConnectTimeRecordService().work();
    }
}
