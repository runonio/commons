package io.runon.install;

import io.runon.install.utils.FileUtils;

import java.io.File;

/**
 * 다른 라이브러리를 최대한 참조하지 않기위해 직접 구현한 구현체 위주로 사용해야한다.
 * @author macle
 */
public class LogbackHomeDir {

    private final String homeDir;

    private String logBackFilePath = "config/logback.xml";

    public  LogbackHomeDir(String homeDir){
        this.homeDir = homeDir;
    }

    public void setLogBackFilePath(String logBackFilePath) {
        this.logBackFilePath = logBackFilePath;
    }

    public void change(){

        String dirSeparator ="/";
        String os = System.getProperty("os.name").toLowerCase();

        String filePath = logBackFilePath;

        if(os.contains("win")){
            dirSeparator = "\\";
            filePath = filePath.replace("/","\\");
        }

        if(!logBackFilePath.startsWith(homeDir)){
            filePath = homeDir + dirSeparator + filePath;
        }

        File logBackFile = new File(filePath);
        if(!logBackFile.isFile()){
            return;
        }

        String logbackText = FileUtils.getFileContents(logBackFile, "UTF-8");

        StringBuilder sb = new StringBuilder();

        boolean isChange = false;

        String searchText ="\"LOG_HOME\"";

        String [] lines = logbackText.split("\n");
        for(String line: lines){

            sb.append("\n");
            if(line.contains(searchText)){

                String value ="value=\"";

                int index  = line.indexOf(value);
                if(index == -1){
                    sb.append(line);
                    continue;
                }

                int end = line.indexOf("\"", index+value.length());

                if(end == -1){
                    sb.append(line);
                    continue;
                }

                String logHome = line.substring(index+value.length(), end);

                if(!logHome.startsWith(homeDir)){
                    //이미 설정된 경로 체크 //절대 경로 이면
                    if(os.contains("win") && logHome.charAt(1) == ':'){
                        sb.append(line);
                        continue;
                    }else if(!os.contains("win") && logHome.startsWith("/")){
                        
                        sb.append(line);
                        continue;
                    }
                    
                    String changeText = value+logHome+"\"";

                    logHome = homeDir +dirSeparator + logHome;

                    line = line.replace(changeText, value+logHome+"\"" );
                    sb.append(line);
                    isChange = true;
                }

            }else{
                sb.append(line);
            }
        }

        if(isChange && sb.length() > 0){
            FileUtils.fileOutput(sb.substring(1), logBackFile.getAbsolutePath(),"UTF-8",false);
            sb.setLength(0);
        }

    }

}
