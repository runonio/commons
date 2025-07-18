package io.runon.system;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigSet;
import io.runon.commons.config.MapConfigData;
import io.runon.commons.service.SyncService;
import io.runon.commons.service.Synchronizer;
import io.runon.commons.service.SynchronizerManager;
import io.runon.jdbc.PrepareStatementData;
import io.runon.jdbc.PrepareStatements;
import io.runon.jdbc.objects.JdbcObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author macle
 */
public class SystemManager implements Synchronizer {

    private static class Singleton {
        private static final SystemManager instance = new SystemManager();
    }

    public static SystemManager getInstance(){
        return Singleton.instance;
    }

    private final MapConfigData systemConfigData = new MapConfigData(new HashMap<>());

    private SystemManager(){
        //설정파일 불러오기
        Config.getConfig("");
        //동기화 등록
        SynchronizerManager.getInstance().add(this);

        systemConfigData.setPriority(Config.getInteger("config.db.priority", ConfigSet.XML_FILE_PRIORITY + 1));
        configSync();
        Config.addConfigData(systemConfigData);

        //동기화 서비스 실행
        new SyncService().start();
    }

    private long configUpdateTime = -1;

    @Override
    public void sync() {
        configSync();

    }

    public void configSync(){
        List<KeyValueData> configList;
        if(configUpdateTime > 0){
            configList = JdbcObjects.getObjList(KeyValueData.class, null, "updated_at asc");
        }else{

            Map<Integer, PrepareStatementData> prepareStatementDataMap =  PrepareStatements.newTimeMap(configUpdateTime);
            configList = JdbcObjects.getObjList(KeyValueData.class,  null ,"updated_at > ?", "updated_at asc", prepareStatementDataMap);
        }

        if(configList.size() > 0){

            for(KeyValueData commonConfig : configList){
                if(commonConfig.isDel() || !commonConfig.isConfig()){
                    systemConfigData.remove(commonConfig.getKey());
                }else{
                    systemConfigData.put(commonConfig.getKey(), commonConfig.getValue());
                }
            }
            configUpdateTime =  configList.get(configList.size()-1).getUpdatedAt();
            configList.clear();
        }
    }

}
