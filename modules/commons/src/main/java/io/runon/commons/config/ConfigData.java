
package io.runon.commons.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 설정 데이터 추상체
 * @author macle
 */
public abstract class ConfigData {

    /**
     * 값 얻기
     * @param key String 설정 키
     * @return String 저장된 설정된 값
     */
    public abstract String getConfig(String key);

    /**
     * 설정키 존재 여부
     * @param key String
     * @return boolean
     */
    public abstract boolean containsKey(String key);



    /**
     * 호출 우선순위
     * 필수구현
     * @return int 우선순위
     */
    public abstract int getPriority();

    public abstract void put(String key, String value);

    /**
     * 설정삭제
     * @param key String key
     * @return String remove value
     */
    public abstract String remove(String key);

    /**
     * 초기 설정이 끝나고 업데이트 될경우
     * 설정하기
     * @param key String 설정키
     * @param value String 설정된 값
     * @return boolean 변화여부
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean setConfig(String key, String value){
        if(value == null){
            return false;
        }
        String lastValue = getConfig(key);

        if(lastValue != null && lastValue.equals(value)){
            return false;
        }

        put(key, value);

        ConfigInfo [] configInfos = new ConfigInfo[1];
        configInfos[0] = new ConfigInfo(key, value);
        Config.notify(this, configInfos);
        return true;
    }

    /**
     * 설정 정보 변경
     * @param configInfo ConfigInfo
     * @return boolean
     */
    public boolean setConfig(ConfigInfo configInfo){
        if(configInfo.isDelete){
            Object obj = remove(configInfo.key);
            if(obj == null){
                return false;
            }
            ConfigInfo [] configInfos = new ConfigInfo[1];
            configInfos[0] = configInfo;
            Config.notify(this, configInfos);
            return true;
        }

        String lastValue = getConfig(configInfo.key);
        if(lastValue != null && lastValue.equals(configInfo.value)){
            return false;
        }

        ConfigInfo [] configInfos = new ConfigInfo[1];
        configInfos[0] = configInfo;
        Config.notify(this, configInfos);
        return true;

    }

    /**
     * 설정하기
     * 여러정보 동시
     * 초기 세팅이 완료된후
     * 이후 변경 과정 업데이트
     * 반드시 Config 클래스에 notify 시킬것
     * @param configInfos ConfigInfo [] configInfo array
     */
    public void setConfig(ConfigInfo [] configInfos){
        List<ConfigInfo> changeList = null;
        for(ConfigInfo configInfo : configInfos){

            if(configInfo.isDelete){
                Object obj = remove(configInfo.key);
                if(obj == null){
                    continue;
                }
                if(changeList == null){
                    changeList = new ArrayList<>();
                }
                changeList.add(configInfo);
            }

            String lastValue = getConfig(configInfo.key);
            if(lastValue != null && lastValue.equals(configInfo.value)){
                continue;
            }

            if(changeList == null){
                changeList = new ArrayList<>();
            }

            put(configInfo.key, configInfo.value);
            changeList.add(configInfo);
        }

        if (changeList == null) {
            return;
        }

        ConfigInfo [] changeArray = changeList.toArray(new ConfigInfo[0]);
        Config.notify(this, changeArray);
        changeList.clear();
    }

}
