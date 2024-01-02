package io.runon.install;

import io.runon.install.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 윈도우 설치환경 자동세팅
 * @author macle
 */
public class WindowsEnv {
    public static void main(String[] args) {
        JavaClassPathOut.out();

        Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString();

        File [] files = new File(path).listFiles();
        if(files == null){
            return;
        }

        String javaPath = args[0];

        for(File file : files){
            if(file.isDirectory()){
                continue;
            }
            if(file.getName().equals("env.bat")){
                continue;
            }
            if(file.getName().endsWith(".bat")){
                String batText = FileUtils.getFileContents(file, "EUC-KR");
                StringBuilder sb = new StringBuilder();

                String [] lines = batText.split("\n");
                for(String line: lines){

                    sb.append("\n");
                    if(line.startsWith("SET APP_HOME")){
                        sb.append("SET APP_HOME=\"").append(path).append("\"");
                    }else if(line.startsWith("SET JAVA_PATH")){
                        sb.append("SET JAVA_PATH=\"").append(javaPath).append("\"");
                    }else{
                        sb.append(line);
                    }
                }

                if(sb.length() > 0){
                    FileUtils.fileOutput(sb.substring(1),file.getAbsolutePath(),"EUC-KR",false);
                    sb.setLength(0);
                }
            }

        }

    }
}
