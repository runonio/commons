package io.runon.commons.data;

import lombok.Data;

/**
 * @author macle
 */
@Data
public class IdArray <T extends DataArray>{

    String id;
    T array;

    public IdArray(){

    }

    public IdArray(String id, T array){
        this.id = id;
        this.array = array;
    }


}
