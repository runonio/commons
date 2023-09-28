package io.runon.commons.data.service.health;

import com.seomse.commons.config.Config;

/**
 * @author macle
 */
public class ServerHealthCommons {

    public static String CONNECT_TIME_REDIS_KEY = Config.getConfig("server.health.connect.time.redis.key","server_health_last_connect_time");

    public static void serviceStart(){

        if(Config.getBoolean("server.health.connect.time.service.flag",true) || Config.getBoolean("server.health.off.notify.service.flag", true)) {
            new ServerConnectTimeRecordService().start();
        }

        if(Config.getBoolean("server.health.off.notify.service.flag", true)){
            new ServersOffNotifyConnectTimeService().start();
        }

    }

}
