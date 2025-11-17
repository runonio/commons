package io.runon.commons.utils.string;


import io.runon.commons.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author macle
 */
@SuppressWarnings("rawtypes")
public class StringLines {


    public static void out(List list, String outPath){
        String sumText = sumText(list);
        out(sumText, new File(outPath),  "UTF-8");
    }

    public static void out(Object [] array, String outPath){
        String sumText = sumText(array);
        out(sumText, new File(outPath),  "UTF-8");
    }


    public static void out(String sumText, File file, String charset){

        if(sumText == null || sumText.isEmpty()){
            return;
        }

        if(charset==null){
            charset = "UTF-8";
        }

        if(file.isFile() && file.length() > 0){
            //이미 기록된 파일이 있고 그내용이 비어있지 않으면
            FileUtils.fileOutput("\n" + sumText, charset, file.getAbsolutePath() , true);
        }else{
            //기록된 파일이 없으면
            FileUtils.fileOutput(sumText, charset, file.getAbsolutePath(),  false);
        }
    }

    public static String sumText(List list){
        return Strings.sumText(list, "\n");
    }

    public static String sumText(Object [] array){
       return Strings.sumText(array,"\n");
    }


    public static String [] getFileLines(String path){

        String text = FileUtils.getFileContents(path);
        return text.split("\n");
    }
    public static String [] getFileLines(File file){

        String text = FileUtils.getFileContents(file);
        return text.split("\n");
    }


    public static String [] getLines(List list){

        String [] lines = new String[list.size()];

        for (int i = 0; i <lines.length ; i++) {
            lines[i] = list.get(i).toString();
        }

        return lines;
    }

    public static String [] getLines(Object [] array){

        String [] lines = new String[array.length];

        for (int i = 0; i <array.length ; i++) {
            lines[i] = array[i].toString();
        }

        return lines;
    }
}
