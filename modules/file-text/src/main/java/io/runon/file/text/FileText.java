package io.runon.file.text;

import com.argo.hwp.HwpTextExtractor;
import com.seomse.commons.utils.FileUtil;
import com.seomse.commons.utils.GsonUtils;
import com.seomse.commons.utils.string.Strings;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author macle
 */
public class FileText {

    @SuppressWarnings({"ResultOfMethodCallIgnored", "IfCanBeSwitch"})
    public static String getTextSimple(String homeDir , String filePath, boolean isDelete){

        if (FileUtil.isFile(filePath)) {
            throw new RuntimeException("file no search: " + filePath);
        }

        File file = new File(filePath);
        String extension = FileUtil.getExtension(file).toLowerCase();

        if(extension.equals("txt")){
            String content =  FileUtil.getFileContents(filePath);
            if(isDelete){
                file.delete();
            }
            return content;
        }else if(extension.equals("csv")){
            String content =  FileUtil.getFileContents(filePath);
            if(isDelete){
                file.delete();
            }
            return content.replace(","," ").trim();

        }else if(extension.equals("xlsx") || extension.equals("xls") ){
           //엑셀처리
            try{
                String text =  new ExcelText().getSimpleText(filePath);
                if(isDelete){
                    file.delete();
                }
                return text;
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }else if(extension.equals("docx") || extension.equals("doc") ){
            //doc
            String text = DocText.getSimpleText(filePath, extension);

            if(isDelete){
                file.delete();
            }
            return text;
        }else if(extension.equals("pptx") || extension.equals("ppt") ){
            //power point 처리
            String text = PptText.getSimpleText(filePath, extension);
            if(isDelete){
                file.delete();
            }
            return text;
        }else if(extension.equals("hwp")){
            //hwp 처리
            try{
                File hwp = new File(filePath);
                Writer writer = new StringWriter();
                HwpTextExtractor.extract(hwp, writer);
                String text = writer.toString();
                if(isDelete){
                    file.delete();
                }

                return text;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        } else if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")|| extension.equals("bmp") || extension.equals("gif")){
            //이미지처리
            try {
                OcrText ocrText = OcrPythonShell.analysis(homeDir, filePath, isDelete);
                if (ocrText.getType() == OcrText.Type.SUCCESS) {

                    String [] array = GsonUtils.getString(GsonUtils.fromJsonArray(ocrText.getText()));

                    if(isDelete){
                        file.delete();
                    }

                    return Strings.toString(array, " ");

                } else {
                    throw new RuntimeException(ocrText.getText());
                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }else if(extension.equals("pdf")){
//            String text =PdfText.getPdfOcrSimple(homeDir, new File(filePath), isDelete);
            String text = PdfText.getPdfSimple(filePath);
            if(isDelete){
                file.delete();
            }
            return text;
        }

        else{
            throw new RuntimeException("file no search: " + filePath);
        }
    }
}
