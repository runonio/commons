package io.runon.commons.data.service.health;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.runon.commons.config.Config;
import io.runon.commons.service.Service;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.time.Times;
import io.runon.commons.data.service.exception.ServerNotRegException;
import io.runon.commons.data.service.notify.NotifyCase;
import io.runon.commons.data.service.notify.NotifyCommons;
import io.runon.commons.data.service.notify.NotifyGmail;
import io.runon.commons.data.service.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 서버들이 켜져있는지 체크하는 서비스
 * 서버가 켜져있는지 체크할때 서버 마지막 접속시간을 활용하는 서비스다.
 * @author macle
 */
@Slf4j
public class ServersOffNotifyConnectTimeService extends Service {


    private final String serverId = Config.getConfig("server.id");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private ZoneId zoneId = ZoneId.of("Asia/Seoul");

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    private long errorTime = Times.MINUTE_30;

    public void setErrorTime(long errorTime) {
        this.errorTime = errorTime;
    }

    public ServersOffNotifyConnectTimeService(){
        if(serverId == null){
            throw new ServerNotRegException("server id null config check: server.id");
        }

        setDelayStartTime(Config.getLong("server.health.off.notify.service.start.delay.time", Times.MINUTE_1));
        setSleepTime(Config.getLong("server.health.off.notify.service.sleep.time", Times.MINUTE_5));
        setState(Service.State.START);

        try{
            // 내 서버가 등록되어 있지 않으면 등록하기
            // 꼭 동기화를 사용할 것
            String priorityJsonArrayValue = Redis.get(NotifyCommons.SERVER_PRIORITY_KEY);
            if(priorityJsonArrayValue == null){
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(serverId);
                Redis.set(NotifyCommons.SERVER_PRIORITY_KEY, jsonArray.toString());
            }else{
                JSONArray jsonArray = new JSONArray(priorityJsonArrayValue);
                boolean isReg = false;
                for (int i = 0; i <jsonArray.length() ; i++) {
                    String id = jsonArray.getString(i);
                    if(serverId.equals(id)){
                        isReg = true;
                        break;
                    }
                }

                if(!isReg){
                    jsonArray.put(serverId);
                    Redis.set(NotifyCommons.SERVER_PRIORITY_KEY, jsonArray.toString());
                }
            }
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    
    private boolean isPriorityCheck = true;

    public void setPriorityCheck(boolean priorityCheck) {
        isPriorityCheck = priorityCheck;
    }

    private long notifyTimeGap = Times.DAY_2;

    private final Map<String, Long> lastNotifyMap = new HashMap<>();

    public void setNotifyTimeGap(long notifyTimeGap) {
        this.notifyTimeGap = notifyTimeGap;
    }


    private NotifyCase notifyCase = NotifyCase.EMAIL;

    public void setNotifyCase(NotifyCase notifyCase) {
        this.notifyCase = notifyCase;
    }

    private NotifyGmail notifyGmail = null;

    @Override
    public void work() {
        try{

            Map<String, String> serverConnectTimeMap = Redis.hgetall(ServerHealthCommons.CONNECT_TIME_REDIS_KEY);

            if(isPriorityCheck) {

                //서버동작 우선순위 체크
                if(!NotifyCommons.isNotifyPriority(serverId, serverConnectTimeMap, errorTime)){
                    return;
                }
            }

            StringBuilder sb = new StringBuilder();

            //서버 오류 목록 찾기
            Set<String> serverIds = serverConnectTimeMap.keySet();

            int newNotifyCount = 0;

            int errorCount = 0;

            for(String serverId : serverIds){
                String timeValue = serverConnectTimeMap.get(serverId);
                long lastConnectTime = Long.parseLong(timeValue);

                if(System.currentTimeMillis() - lastConnectTime > errorTime){
                    errorCount++;
                    Long lsatNotifyTime = lastNotifyMap.get(serverId);
                    if(lsatNotifyTime == null || System.currentTimeMillis() - lsatNotifyTime > notifyTimeGap) {
                        lastNotifyMap.put(serverId, System.currentTimeMillis());
                        newNotifyCount++;
                    }

                    //메시지는 신규알람과 이전알람 둘다 생성

                    //오류이면
                    //noinspection StringConcatenationInsideStringBufferAppend
                    sb.append("\nsever connect error: " + serverId +", last connected at: " + Times.ymdhm(lastConnectTime, zoneId)  + "(" + lastConnectTime +")");
                }

            }

            if(newNotifyCount == 0){
                //신규 알람이 없으면
                return;
            }

            if(notifyCase == NotifyCase.CONSOLE){
                log.info(sb.toString());
            }else if(notifyCase == NotifyCase.EMAIL){
                if(notifyGmail == null){
                    notifyGmail = new NotifyGmail();
                }

                notifyGmail.sendMail("server connect error " + errorCount , sb.toString());

            }

        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    public static void main(String[] args) {
        new ServersOffNotifyConnectTimeService().work();
    }
}
