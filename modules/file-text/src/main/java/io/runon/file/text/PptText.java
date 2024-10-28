package io.runon.file.text;

import com.seomse.commons.utils.FileUtil;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author macle
 */
public class PptText {

    public static String getSimpleText(String filePath){
        File file = new File(filePath);
        String extension = FileUtil.getExtension(file).toLowerCase();

        return getSimpleText(filePath, extension);
    }

    public static String getSimpleText(String filePath, String extension){

        if(extension.equals("ppt")){
            POIFSFileSystem fs = null;
            PowerPointExtractor extractor = null;
            try{
                fs = new POIFSFileSystem(new FileInputStream(filePath));
                extractor = new PowerPointExtractor(fs);

                return extractor.getText();
            }catch (Exception e){
                throw new RuntimeException(e);
            }finally {
                try{extractor.close();}catch (Exception ignore){}
                try{fs.close();}catch (Exception ignore){}
            }

        }else{
            FileInputStream fs =null;
            OPCPackage d = null;
            XSLFPowerPointExtractor xp = null;
            try{
                fs = new FileInputStream(filePath);
                d = OPCPackage.open(fs);
                xp = new XSLFPowerPointExtractor (d);

                return xp.getText();
            }catch (Exception e){
                throw new RuntimeException(e);
            }finally {
                try{xp.close();}catch (Exception ignore){}
                try{d.close();}catch (Exception ignore){}
                try{fs.close();}catch (Exception ignore){}
            }
        }
    }

    public static void main(String[] args) {
        String text =getSimpleText("D:\\업무\\알바몬_(넥서스,위고).pptx");
        System.out.println(text);
    }
}
