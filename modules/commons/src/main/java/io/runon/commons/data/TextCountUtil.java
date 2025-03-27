
package io.runon.commons.data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author macle
 */
public class TextCountUtil {


    /**
     * 겹과 합침
     * @param sourceMap Map
     * @param sumMap Map
     */
    public static  void sumParentsMap(Map<String, Map<String, TextCountInt>> sourceMap, Map<String, Map<String, TextCountInt>> sumMap){

        if(sourceMap == null || sumMap == null){
            return;
        }
        Set<String> sumKeys = sumMap.keySet();

        for(String sumKey : sumKeys){
            Map<String, TextCountInt> sourceCountMap = sourceMap.get(sumKey);

            if(sourceCountMap == null){
                sourceMap.put(sumKey, sumMap.get(sumKey));

            }else{
                sumMap(sourceCountMap, sumMap.get(sumKey));
            }
        }
    }

    /**
     * 결과 합침
     * @param sourceMap Map
     * @param sumMap Map
     */
    public static  void sumMap(Map<String, TextCountInt> sourceMap, Map<String, TextCountInt> sumMap){

        if(sourceMap == null || sumMap == null){
            return;
        }

        Collection<TextCountInt> sumCollection = sumMap.values();

        for(TextCountInt sumCount : sumCollection){
            TextCountInt valueCountInt = sourceMap.get(sumCount.getText());
            if(valueCountInt == null){
                sourceMap.put(sumCount.getText(), sumCount);
            }else{
                valueCountInt.plus(sumCount.getCount());
            }
        }
    }
}
