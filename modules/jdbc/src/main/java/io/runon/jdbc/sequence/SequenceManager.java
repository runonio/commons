

package io.runon.jdbc.sequence;

import io.runon.commons.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * 시퀀스 관리자
 * @author macle
 */
public class SequenceManager {


    private final Map<String, SequenceMaker> dbTypeMap = new HashMap<>();

    private final Object lock = new Object();

    public SequenceManager(){
        String jdbcType = Config.getConfig("application.jdbc.type");
        if(jdbcType != null){
            setDefaultMaker(jdbcType);
        }
    }


    /**
     * 시퀀스 값 얻기
     * @param sequenceName String sequence name
     * @param jdbcType db 유형
     * @return String sequence value
     */
    public String nextVal(String sequenceName, String jdbcType){
        SequenceMaker sequenceMaker =  dbTypeMap.get(jdbcType);

        if(sequenceMaker == null){
            sequenceMaker = make(jdbcType);
        }
        return sequenceMaker.nextVal(sequenceName);
    }

    private SequenceMaker make(String jdbcType){
        synchronized (lock){
            SequenceMaker sequenceMaker =  dbTypeMap.get(jdbcType);
            if(sequenceMaker == null){
                sequenceMaker =  SequenceMakerFactory.make(jdbcType);
                dbTypeMap.put(jdbcType, sequenceMaker);
            }

            return sequenceMaker;
        }
    }

    private SequenceMaker defaultMaker = null;

    /**
     * 기본 생성기 설정
     * @param jdbcType db 유형
     */
    public void setDefaultMaker(String jdbcType) {
        this.defaultMaker = SequenceMakerFactory.make(jdbcType);
    }

    /**
     * 시퀀스 값 얻기
     * @param sequenceName String sequence name
     * @return String sequence value
     */
    public String nextVal(String sequenceName){
        return defaultMaker.nextVal(sequenceName);
    }

    /**
     * 시퀀스 num 얻기
     * @param sequenceName String sequence name
     * @param jdbcType db 유형
     * @return sequence num (long)
     */
    public long nextLong(String sequenceName, String jdbcType){
        SequenceMaker sequenceMaker =  dbTypeMap.get(jdbcType);

        if(sequenceMaker == null){
            sequenceMaker = make(jdbcType);
        }
        return sequenceMaker.nextLong(sequenceName);
    }

    /**
     * 시퀀스 num
     * @param sequenceName 시퀀스 명
     * @return next long
     */
    public long nextLong(String sequenceName){
        return defaultMaker.nextLong(sequenceName);
    }


}
