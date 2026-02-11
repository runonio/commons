package io.runon.commons.data;

/**
 * @author macle
 */
public class StringArray extends DataArray<String>{

    public static final String [] EMPTY_ARRAY = new String[0];


    public StringArray(String ...array){

        super(array);
    }

    public boolean containsArrays(String text){

        if(array == null || array.length==0){
            return false;
        }

        for(String a : array){
            if(!text.contains(a)){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);

        for (int i = 1; i < array.length; i++) {
            sb.append(' ').append(array[i]);
        }

        return sb.toString();
    }
}
