package io.runon.commons.collect;

import com.seomse.crawling.AccessTimeManager;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 국가별 업무시간
 * 크롤링에서 각가별 업무시간은 피해서 크롤링할 필요가 있을경우
 * @author macle
 */
@SuppressWarnings("ManualMinMaxCalculation")
public class CollectTimes {

    public static boolean isKorWorkTime(long time){
        return isWorkTime(time, ZoneId.of("Asia/Seoul"), 9, 18);
    }


    public static boolean isKorHardWorkTime(long time){
        return isWorkTime(time, ZoneId.of("Asia/Seoul"), 8, 20);
    }

    public static boolean isWorkTime(long time, ZoneId zoneId, int beginHour, int endHour){

//        MONDAY, //월
//        TUESDAY, //화
//        WEDNESDAY, //수
//        THURSDAY, //목
//        FRIDAY, //금
//        SATURDAY, //토
//        SUNDAY; //일

        Instant i = Instant.ofEpochMilli(time);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(i,  zoneId);
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
            return false;
        }

        int hour = zonedDateTime.getHour();

        //8시 ~ 18시 는 업무시간 // 오후 6시부터 시작
        //noinspection RedundantIfStatement
        if(hour >= beginHour  && hour < endHour){

            return true;
        }

        return false;
    }

    public static boolean isUsaWorkTime(long time){

        return isWorkTime(time, ZoneId.of("America/New_York"), 8, 20);
    }


    public static void sleepRandomTime(long time){
        //랜덤 클로링 봇이라는걸 피하기위한 시간설정
        long randomSleepTime = ThreadLocalRandom.current().nextLong(time);
        if(randomSleepTime > 0){
            try{Thread.sleep(randomSleepTime);}catch(Exception ignore){}
        }
    }

    public static void sleepRandomTime(String site, long minTime ,long randomTime){
        try{Thread.sleep(getSleepRandomTime(site,minTime, randomTime));}catch(Exception ignore){}
    }

    public static long getSleepRandomTime(String site, long time){
        long randomSleepTime = ThreadLocalRandom.current().nextLong(time);
        long lastSleepTime = AccessTimeManager.getInstance().getSleepTime(site, randomSleepTime);

        if(lastSleepTime < randomSleepTime){
            return randomSleepTime;
        }
        return lastSleepTime;
    }


    public static long getSleepRandomTime(String site, long minTime, long randomTime) {
        long sleepTime = ThreadLocalRandom.current().nextLong(randomTime) + minTime;
        long lastSleepTime = AccessTimeManager.getInstance().getSleepTime(site, sleepTime);

        if (lastSleepTime < sleepTime) {
            return sleepTime;
        }
        return lastSleepTime;
    }


    public static void main(String[] args) {
//        System.out.println(isKorWorkTime(System.currentTimeMillis()));

        for (int i = 0; i <20 ; i++) {
            long t = getSleepRandomTime("test", 10000, 10000);
            System.out.println(t/1000);
        }
    }

}
