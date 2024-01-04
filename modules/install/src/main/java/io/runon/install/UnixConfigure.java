package io.runon.install;

import io.runon.install.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * unix 계열 자동설정
 * @author macle
 */
public class UnixConfigure {
    public static void main(String[] args) {

        Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString();

        File envFile = new File(path+"/env.sh");
        if(envFile.isFile()){
            String shText = FileUtils.getFileContents(envFile, "UTF-8");
            StringBuilder sb = new StringBuilder();

            String [] lines = shText.split("\n");
            for(String line: lines){

                sb.append("\n");
                if(line.startsWith("export APP_HOME")){
                    sb.append("export APP_HOME=\"").append(path).append("\"");
                }else{
                    sb.append(line);
                }
            }

            if(sb.length() > 0){
                FileUtils.fileOutput(sb.substring(1), envFile.getAbsolutePath(),"UTF-8",false);
                sb.setLength(0);
            }

        }


        ConfigRelativePath configRelativePath = new ConfigRelativePath(path);
        configRelativePath.change();

    }
}
