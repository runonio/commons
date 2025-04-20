package io.runon.commons.data;

import io.runon.commons.utils.GsonUtils;

/**
 * @author macle
 */
public class IntArray {
    int [] array;

    public IntArray(int ...array){
        this.array = array;
    }


    public int [] getArray() {
        return array;
    }

    public int get(int index){
        return array[index];
    }

    public int length(){
        return array.length;
    }

    @Override
    public String toString(){
        return GsonUtils.GSON.toJson(array);
    }

}
