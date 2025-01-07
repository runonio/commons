package io.runon.commons.data;

import com.google.gson.JsonArray;
import io.runon.commons.utils.GsonUtils;

import java.math.BigDecimal;

/**
 * @author macle
 */
public class BigDecimalArray extends DataArray<BigDecimal> {

    public BigDecimalArray(BigDecimal ... array){
        super(array);
    }



    public JsonArray toJson(){
        JsonArray jsonArray = new JsonArray();
        for(BigDecimal num : array){
            jsonArray.add(num.toPlainString());
        }
        return jsonArray;
    }

    @Override
    public String toString(){
        return GsonUtils.GSON.toJson(toJson());
    }
}
