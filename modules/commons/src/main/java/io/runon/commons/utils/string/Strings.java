package io.runon.commons.utils.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 문자열 관련 유틸성 클래스
 * @author macle
 */
public class Strings {

    /**
     * 배열 비교를 위해 구성요소를 정렬한후 비교함
     * 정렬을 원하지 않을경우 복사된 배열을 보내야 함
     */
    public static boolean equalsSort(String [] a, String [] b){

        if(a == null && b== null){
            return true;
        }

        if(a == null){
            return false;
        }

        if(b == null){
            return false;
        }

        if(a.length != b.length){
            return false;
        }

        //a와 b가 둘다 0이면
        if(a.length == 0){
            return true;
        }

        Arrays.sort(a);
        Arrays.sort(b);

        for (int i = 0; i <a.length ; i++) {
            if(!a[i].equals(b[i])){
                return false;
            }
        }

        return true;
    }


    public static boolean equals(String a, String b){
        return Check.equals(a,b);
    }

    public static String [] addString(String [] array , String add){
        String [] newStrings = new String[array.length+1];
        System.arraycopy(array, 0, newStrings, 0, array.length);
        newStrings[array.length] = add;
        return newStrings;
    }

    public static String [] addString(String [] array , String [] adds){
        String [] newStrings = new String[array.length + adds.length];
        System.arraycopy(array, 0, newStrings, 0, array.length);

        int index = 0;
        for (int i = array.length; i <newStrings.length ; i++) {
            newStrings[i] = adds[index++];
        }

        return newStrings;
    }

    public static String [] makeTextArray(String text, String tag, String [] array){

        String [] textArray = new String[array.length];

        for (int i = 0; i <textArray.length ; i++) {
            textArray[i] = text.replace(tag, array[i]);
        }

        return textArray;
    }

    public static String toString(String [] array, String split){
        if(array == null || array.length == 0){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for (int i = 1; i <array.length ; i++) {
            sb.append(split).append(array[i]);
        }
        return sb.toString();
    }


    public static String toString(List<String> list , String split){
        if(list == null || list.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0));
        for (int i = 1; i < list.size() ; i++) {
            sb.append(split).append(list.get(i));
        }
        return sb.toString();
    }



    private final static String DEFAULT_SORT_KEY_SPLIT = ",";

    /**
     * 배열을 정렬하여 키생성
     * @param array
     * @return
     */
    public static String makeSortKey(String [] array){
        return makeSortKey(array, DEFAULT_SORT_KEY_SPLIT);

    }

    /**
     * 배열을 정렬하여 키생성
     * @param array
     * @param split
     * @return
     */
    public static String makeSortKey(String [] array, String split){
        if(array == null || array.length ==0){
            throw new RuntimeException("array null");
        }
        if(array.length==1){
            return array[0];
        }
        Arrays.sort(array);
        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for(int i=1 ; i < array.length ; i++){
            sb.append(split+array[i]);
        }
        return sb.toString();

    }

    public static int inCount(String source, String target){
        return inCount(source.toCharArray(), target.toCharArray());
    }

    public static int inCount(char [] source, char [] target){
        Set<Integer> dupleSet = new HashSet<>();
        int count = 0;

        for (char c : source) {
            for (int j = 0; j < target.length; j++) {
                if (dupleSet.contains(j)) {
                    continue;
                }
                if (c == target[j]) {
                    count++;
                    dupleSet.add(j);
                    if (dupleSet.size() == target.length) {
                        return count;
                    }
                }

            }
        }

        return count;
    }


    public static String [] toSortArray(String arrayValue){
        return arrayValue.split(DEFAULT_SORT_KEY_SPLIT);
    }



    public static boolean contains(String [] array, String [] inArray){

        if(inArray.length > array.length){
            return false;
        }

        for(String in: inArray){
            if(!contains(array, in)){
                return false;
            }
        }

        return true;
    }

    public static boolean contains(String [] array, String in){
        for(String str : array){
            if(str.equals(in)){
                return true;
            }
        }

        return false;
    }

}
