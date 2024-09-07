package io.runon.ocr;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.commons.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author macle
 */
@Slf4j
@RestController
public class OcrController {

    public static final Object TEMP_NUM_LOCK = new JSONObject();

    private static int TEMP_NUM =0;

    private static int getTempNum(){
        synchronized (TEMP_NUM_LOCK){
            TEMP_NUM++;
            if(TEMP_NUM < 1){
                TEMP_NUM = 1;
            }
            return TEMP_NUM;
        }
    }

    @RequestMapping(value = "/text/ocr" , method = RequestMethod.POST, produces= MediaType.MULTIPART_FORM_DATA_VALUE)
    public String ocr(@RequestPart(value="file") MultipartFile file){
        try{

            String ocrHome = Config.getConfig("ocr.home");
            String tempDirPath = ocrHome +"/temp/";

            String tempFileName = System.currentTimeMillis()+"_"+getTempNum();

            String tempFullPath = tempDirPath + tempFileName;

            FileOutputStream outStream = new FileOutputStream(tempDirPath + tempFileName);

            InputStream inputStream = file.getInputStream();
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            try{outStream.getFD().sync();}catch (Exception ignore){}
            try{outStream.close();}catch (Exception ignore){}
            try{inputStream.close();}catch (Exception ignore){}


            List<String> commandList =  new ArrayList<>();
            commandList.add(ocrHome +"/bin/python");
            commandList.add(ocrHome +"/ocr_out.py");
            commandList.add(tempFullPath);


            AtomicBoolean isAnalysis = new AtomicBoolean(false);
            ProcessBuilder builder = new ProcessBuilder(commandList);

            final Process process = builder.start();

            final String [] message = new String[1];

            new Thread(() -> {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
                    String line = null;

                    while ((line = reader.readLine()) != null) {

                        if(line.startsWith("ocr json text:")){
                            message[0] = line.substring("ocr json text:".length()).trim();
                            break;
                        }
                    }

                }catch(Exception e){
                    log.error(ExceptionUtil.getStackTrace(e));
                }finally{
                    try{reader.close(); }catch(Exception e){}
                    isAnalysis.set(true);
                }



            }).start();


            final StringBuilder errorBuilder =new StringBuilder();
            new Thread(() -> {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader( new InputStreamReader( process.getErrorStream() ) );
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        errorBuilder.append(line).append("\n");
                    }

                }catch(Exception e){
                    log.error(ExceptionUtil.getStackTrace(e));
                }finally{
                    try{reader.close(); }catch(Exception e){}
                }

                isAnalysis.set(true);

            }).start();

            long watiSum = 0;

            while (!isAnalysis.get()) {
                watiSum += 500;
                Thread.sleep(500);

                if(watiSum > 5000 && errorBuilder.length() > 0){
                    break;
                }
            }

            synchronized (process) {
                process.wait();
                process.destroy();
            }

            try{new File(tempFullPath).delete();}catch (Exception ignore){}

            if(message[0] == null && errorBuilder.length() > 0){
                JSONObject response = new JSONObject();
                response.put("code", "-2");
                response.put("message", errorBuilder.toString());
                return response.toString();
            }


            JSONObject response = new JSONObject();

            response.put("code", "1");
            response.put("message", new JSONArray(message[0]));
            return response.toString();
        }catch (Exception e){
            JSONObject response = new JSONObject();
            response.put("code", "-1");
            response.put("message", ExceptionUtil.getStackTrace(e));
            return response.toString();

        }
    }

}
