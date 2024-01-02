package io.runon.install.utils;

import io.runon.install.exception.IORuntimeException;

import java.io.*;
import java.nio.file.Files;

/**
 * 설치관련 모듈
 * @author macle
 */
public class FileUtils {

    public static String getFileContents(File file, String charSet){

        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), charSet))){
            String line;

            while ((line = br.readLine()) != null) {
                sb.append("\n");
                sb.append(line);
            }

        }catch(IOException e){
            throw new IORuntimeException(e);
        }
        if(sb.length() == 0){
            return "";
        }

        return sb.substring(1);
    }

    public static void fileOutput(String outValue, String filePath, boolean isAppend){
        fileOutput(outValue, filePath, "UTF-8", isAppend);
    }

    public static void fileOutput(String outValue, String filePath, String charSet,boolean isAppend){
        try {
            File parent = new File(filePath).getParentFile();
            if(!parent.isDirectory()){
                //noinspection ResultOfMethodCallIgnored
                parent.mkdirs();
            }
        }catch(Exception ignore){}

        try(FileOutputStream out =  new FileOutputStream(filePath, isAppend)){
            out.write(outValue.getBytes(charSet));
            out.flush();
            out.getFD().sync();
        }catch(IOException e){
            throw new IORuntimeException(e);
            //io, nio 패키지를 같이쓰면 잘 써지고도 에러나는 경우가 있으므로 예외처리
//            log.(ExceptionUtil.getStackTrace(e));
        }
    }
}
