package io.runon.commons.outputs;
/**
 * @author macle
 */
public class FileUtils {

    public static String getExtension(String fileName){

        int index = fileName.lastIndexOf(".");
        if(index < 0){
            return "";
        }
        return fileName.substring(index +1);

    }
}
