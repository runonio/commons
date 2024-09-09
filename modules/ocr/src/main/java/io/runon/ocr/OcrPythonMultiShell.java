package io.runon.ocr;

import com.google.gson.JsonArray;
import com.seomse.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;

/**
 * @author macle
 */
@Slf4j
public class OcrPythonMultiShell {
    private final String [] filePaths;
    private final String ocrHome;
    public OcrPythonMultiShell(String ocrHome, String [] filePaths){
        this.ocrHome = ocrHome;
        this.filePaths = filePaths;
    }


    private final Object lock = new Object();

    final JsonArray array = new JsonArray();
    String errorMessage = null;

    public void runToWait(){
        for(final String fiePath : filePaths){
            new Thread(() -> {
                OcrText ocrText;
                try{
                    ocrText = OcrPythonShell.analysis(ocrHome, fiePath, true);
                    complete(ocrText);
                }catch (Exception e){
                    synchronized (lock){

                        errorMessage = ExceptionUtil.getStackTrace(e);
                        completeCount++;
                    }
                }
            }).start();
        }

        for(;;){
            if(completeCount >= filePaths.length){
                break;
            }
            try{Thread.sleep(500);}catch (Exception ignore){}
        }

    }


    private int completeCount = 0;
    public void complete(OcrText ocrText){
        synchronized (lock){
            try{
                if(ocrText.getType() == OcrText.Type.SUCCESS){

                    JSONArray ocrArray = new JSONArray(ocrText.getText());
                    for (int i = 0; i <ocrArray.length() ; i++) {
                        array.add(ocrArray.getString(i));
                    }

                }else{
                    errorMessage = ocrText.getText();
                }

            }catch (Exception e){
                errorMessage = ExceptionUtil.getStackTrace(e);
            }
            completeCount++;

        }


    }

    public JsonArray getArray() {
        return array;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
