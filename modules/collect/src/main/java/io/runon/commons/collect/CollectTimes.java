package io.runon.commons.collect;

import com.seomse.crawling.AccessTimeManager;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

/**
 * 국가별 업무시간
 * 크롤링에서 각가별 업무시간은 피해서 크롤링할 필요가 있을경우
 * @author macle
 */
public class CollectTimes {


    public static boolean isKorWorkTime(long time){

        Instant i = Instant.ofEpochMilli(time);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(i,  ZoneId.of("Asia/Seoul"));

//        MONDAY, //월
//        TUESDAY, //화
//        WEDNESDAY, //수
//        THURSDAY, //목
//        FRIDAY, //금
//        SATURDAY, //토
//        SUNDAY; //일

        //토일이면 업무하지 않음
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
            return false;
        }

        int hour = zonedDateTime.getHour();

        //9시 ~ 18시 는 업무시간 // 오후 6시부터 시작
        //noinspection RedundantIfStatement
        if(hour >= 9  && hour < 18){

            return true;
        }

        return false;
    }

    public static boolean isUsaWorkTime(long time){
        Instant i = Instant.ofEpochMilli(time);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(i,  ZoneId.of("America/New_York"));

        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
            return false;
        }

        int hour = zonedDateTime.getHour();

        //8시 ~ 20시 는 업무시간 으로 본다. 미국 크롤링 사이트는 차단이 심하기 때문에 업무시간을 더 잘피하기 위해 더 범위 있는 값을 설정한다.
        //noinspection RedundantIfStatement
        if(hour >= 8  && hour < 20){
            return true;
        }
        return false;
    }


    public static void sleepRandomTime(long time){
        Random random = new Random();
        //랜덤 클로링 봇이라는걸 피하기위한 시간설정
        long randomSleepTime = random.nextLong(time);
        if(randomSleepTime > 0){
            try{Thread.sleep(randomSleepTime);}catch(Exception ignore){}
        }
    }

    public static void sleepRandomTime(String site, long minTime ,long randomTime){
        try{Thread.sleep(getSleepRandomTime(site,minTime, randomTime));}catch(Exception ignore){}
    }

    public static long getSleepRandomTime(String site, long time){
        Random random = new Random();
        long randomSleepTime = random.nextLong(time);
        return  AccessTimeManager.getInstance().getSleepTime(site, randomSleepTime);
    }


    public static long getSleepRandomTime(String site, long minTime, long randomTime){
        Random random = new Random();
        long sleepTime = random.nextLong(randomTime) + minTime;
        return  AccessTimeManager.getInstance().getSleepTime(site, sleepTime);
    }


    public static void main(String[] args) {
//        System.out.println(isKorWorkTime(System.currentTimeMillis()));

        for (int i = 0; i <20 ; i++) {
            long t = getSleepRandomTime("test", 10000, 10000);
            System.out.println(t/1000);
        }
    }

}
