package io.runon.install.utils;

import io.runon.install.exception.IORuntimeException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 설치관련 모듈
 * @author macle
 */
public class FileUtils {

    public static void fileOutput(String outValue, String filePath, boolean isAppend){
        try {
            File parent = new File(filePath).getParentFile();
            if(!parent.isDirectory()){
                //noinspection ResultOfMethodCallIgnored
                parent.mkdirs();
            }
        }catch(Exception ignore){}

        try(FileOutputStream out =  new FileOutputStream(filePath, isAppend)){
            out.write(outValue.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.getFD().sync();
        }catch(IOException e){
            throw new IORuntimeException(e);
            //io, nio 패키지를 같이쓰면 잘 써지고도 에러나는 경우가 있으므로 예외처리
//            log.(ExceptionUtil.getStackTrace(e));
        }
    }
}
