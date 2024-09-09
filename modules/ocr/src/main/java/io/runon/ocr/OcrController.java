package io.runon.ocr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.seomse.commons.config.Config;
import com.seomse.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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


            String originalName = file.getOriginalFilename();

            if(originalName != null && originalName.endsWith(".pdf")){
                return getPdfOcr(ocrHome, new File(tempFullPath));
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            OcrText ocrText = OcrPythonShell.analysis(ocrHome, tempFullPath, true);
            JsonObject response = new JsonObject();

            if(ocrText.getType() == OcrText.Type.SUCCESS){
                response.addProperty("code", "1");
                response.add("ocr_text", gson.fromJson(ocrText.getText(), JsonArray.class));
            }else{
                response.addProperty("code", "-2");
                response.addProperty("error_message", ocrText.getText());
            }

            return gson.toJson(response);

        }catch (Exception e){
            JSONObject response = new JSONObject();
            response.put("code", "-1");
            response.put("message", ExceptionUtil.getStackTrace(e));
            return response.toString();

        }
    }


    public String getPdfOcr(String ocrHome,File pdfFile) throws IOException {

        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();

        String tempDirPath = ocrHome +"/temp/";
        List<String> pathList = new ArrayList<>();
        String text;
        try {
            text = stripper.getText(document);
            int pageCount = document.getNumberOfPages();//pdf의 페이지 수
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for(int i=0;i<pageCount;i++)
            {
                String tempFileName = System.currentTimeMillis() + "_" + getTempNum();
                String tempFullPath = tempDirPath + tempFileName + ".jpg";
                try {
                    BufferedImage imageObj = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);//pdf파일의 페이지를돌면서 이미지 파일 변환
                    File outputfile = new File(tempFullPath);//파일이름 변경(.pdf->.jpg)
                    ImageIO.write(imageObj, "jpg", outputfile);//변환한 파일 업로드
                    pathList.add(tempFullPath);
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            document.close();
        }

        OcrPythonMultiShell ocrPythonMultiShell = new OcrPythonMultiShell(ocrHome, pathList.toArray(new String[0]));
        ocrPythonMultiShell.runToWait();



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject response = new JsonObject();
        if(ocrPythonMultiShell.getErrorMessage() == null){
            response.addProperty("code", "1");
        }else{
            response.addProperty("code", "-2");
            response.addProperty("error_mesage", ocrPythonMultiShell.getErrorMessage());
        }

        response.addProperty("pdf_text", text);
        response.add("orc_text", ocrPythonMultiShell.getArray());
        return gson.toJson(response);
    }

}
