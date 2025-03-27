package io.runon.commons.data;

import java.util.Map;
import java.util.Set;


public class CountIntUtil {

    public static void sumCountMap(Map<String, CountInt> countMap, Map<String, CountInt> sumMap){

        if(sumMap == null || sumMap.size() == 0){
            return;
        }

        Set<String> sumKeySet =  sumMap.keySet();

        for(String sumKey : sumKeySet){
            CountInt sumCount = sumMap.get(sumKey);

            CountInt countInt = countMap.get(sumKey);
            if(countInt == null){
                countMap.put(sumKey, sumCount);
            }else{
                countInt.plus(sumCount.getCount());
            }

        }

    }
}
