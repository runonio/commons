package io.runon.install.utils;

import io.runon.install.exception.IORuntimeException;

import java.io.*;
import java.nio.channels.FileChannel;
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

        }
    }

    public static boolean copy(String inFilePath, String outFilePath) {
        try {

            File outFile = new File(outFilePath);
            File parentFile = outFile.getParentFile();

            if(!parentFile.isDirectory()) {
                //noinspection ResultOfMethodCallIgnored
                parentFile.mkdirs();
            }

            FileInputStream fis = new FileInputStream(inFilePath);
            FileOutputStream fos = new FileOutputStream(outFile);

            FileChannel fcin =  fis.getChannel();
            FileChannel fcout = fos.getChannel();

            long size = fcin.size();
            fcin.transferTo(0, size, fcout);

            fcin.close();
            fcout.close();

            fis.close();
            fos.close();
            return true;
        } catch (IOException e) {

            throw new IORuntimeException(e);
        }
    }
}
