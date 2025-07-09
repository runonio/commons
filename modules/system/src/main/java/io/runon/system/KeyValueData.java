package io.runon.system;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="key_value")
public class KeyValueData {

    @PrimaryKey(seq = 1)
    @Column(name = "data_key")
    String key;

    @Column(name = "data_value")
    String value;

    @Column(name = "meta_data")
    String dataType;


    @Column(name = "meta_data")
    String metaData;

    @Column(name = "is_del")
    boolean isDel = false;

    @DateTime
    @Column(name = "updated_at")
    long updatedAt = System.currentTimeMillis();


    public boolean isConfig(){
        if(dataType == null){
            return false;
        }

        return dataType.toUpperCase().equals("CONFIG");
    }

}
