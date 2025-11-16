

package io.runon.commons.service;

import io.runon.commons.config.Config;
import io.runon.commons.utils.ExceptionUtils;
import io.runon.commons.utils.PriorityUtils;
import io.runon.commons.utils.packages.classes.ClassSearch;
import io.runon.commons.utils.time.RunningTime;
import io.runon.commons.utils.time.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 동기화 관리자
 * @author macle
 */
@Slf4j
public class SynchronizerManager {

    private static class Singleton{
        private static final SynchronizerManager instance = new SynchronizerManager();
    }

    /**
     * 인스턴스 얻기
     * @return SynchronizerManager Singleton instance
     */
    public static SynchronizerManager getInstance(){
        return Singleton.instance;
    }


    private final Set<Synchronizer> syncSet = new HashSet<>();

    private Synchronizer[] syncArray ;

    private boolean isIng = false;

    private long lastSyncTime = System.currentTimeMillis();

    /**
     * 생성자
     */
    private SynchronizerManager(){

        try{

            String syncPackagesValue = Config.getConfig("synchronizer.package");
            if(syncPackagesValue == null){
                syncPackagesValue = Config.getConfig("default.package", "io.runon");
            }

            String [] syncPackages = syncPackagesValue.split(",");
            ClassSearch search = new ClassSearch();
            search.setInPackages(syncPackages);
            Class<?> [] inClasses = {Synchronizer.class};
            search.setInClasses(inClasses);

            List<Class<?>> classes = search.search();
            for(Class<?> cl : classes){
                if (cl.isAnnotationPresent(Synchronization.class)) {

                    Synchronizer sync = (Synchronizer) cl.newInstance();
                    syncSet.add(sync);
                }
            }

            if (syncSet.size() == 0) {
                this.syncArray = new Synchronizer[0];
                return;
            }

            changeArray();
        }catch(Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 메모리 정보 변경
     */
    private void changeArray(){

        Synchronizer[] SyncArray = syncSet.toArray( new Synchronizer[0]);

        Arrays.sort(SyncArray, PriorityUtils.PRIORITY_SORT);

        this.syncArray = SyncArray;
    }

    private final Object lock = new Object();

    /**
     * 동기화  객체 추가
     * @param synchronizer Synchronizer
     */
    public void add(Synchronizer synchronizer){
        synchronized (lock){
            if(syncSet.contains(synchronizer)){
                return;
            }
            syncSet.add(synchronizer);
            changeArray();
        }
    }



    /**
     * 동기화 객체 제거
     * @param synchronizer Synchronizer
     */
    public void remove(Synchronizer synchronizer){
        synchronized (lock){
            if(!syncSet.contains(synchronizer)){
                return;
            }
            syncSet.remove(synchronizer);

            if(syncSet.size() == 0){
                this.syncArray = new Synchronizer[0];
            }else{
                changeArray();
            }
        }
    }


    private final Object syncLock = new Object();

    /**
     * 초기에 처음 실행될 이벤트 정의
     */
    public void sync(){
        synchronized (syncLock) {
            isIng = true;
            lastSyncTime = System.currentTimeMillis();
            RunningTime runningTime = new RunningTime();

            Synchronizer[] syncArray = this.syncArray;

            //순서정보를 명확하게 하기위해 i 사용 ( 순서가 꼭 지켜져야 함을 명시)
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < syncArray.length; i++) {
                try {
                    Synchronizer sync = syncArray[i];
                    log.debug("sync : " + sync.getClass().getName());
                    sync.sync();

                    log.debug(TimeUtils.getTimeValue(runningTime.getRunningTime()));

                } catch (Exception e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
            isIng = false;
        }
    }

    /**
     * @return boolean 진행중 여부
     */
    public boolean isIng() {
        return isIng;
    }

    /**
     * @return long unix time 마지막 동기화 시간
     */
    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public static void main(String[] args) {
        SynchronizerManager.getInstance().sync();
    }
}
