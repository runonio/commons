package io.runon.file.text;

import com.seomse.commons.utils.FileUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author macle
 */
public class DocText {

    public static String getSimpleText(String filePath){
        File file = new File(filePath);
        String extension = FileUtil.getExtension(file).toLowerCase();

        return getSimpleText(filePath, extension);
    }

    public static String getSimpleText(String filePath, String extension){

        if(extension.equals("doc")){
            POIFSFileSystem fs = null;
            HWPFDocument doc = null;
            WordExtractor we = null;
            try{
                fs = new POIFSFileSystem(new FileInputStream(filePath));
                doc = new HWPFDocument(fs);
                we = new WordExtractor(doc);

                return we.getText();
            }catch (Exception e){
                throw new RuntimeException(e);
            }finally {
                try{we.close();}catch (Exception ignore){}
                try{doc.close();}catch (Exception ignore){}
                try{fs.close();}catch (Exception ignore){}
            }

        }else{
            FileInputStream fs =null;
            OPCPackage d = null;
            XWPFWordExtractor xw= null;
            try{
                fs = new FileInputStream(filePath);
                d = OPCPackage.open(fs);
                xw = new XWPFWordExtractor(d);

                return xw.getText();
            }catch (Exception e){
                throw new RuntimeException(e);
            }finally {
                try{xw.close();}catch (Exception ignore){}
                try{d.close();}catch (Exception ignore){}
                try{fs.close();}catch (Exception ignore){}
            }
        }
    }

    public static void main(String[] args) {
        String text =getSimpleText("D:\\업무\\이용약관.doc");
        System.out.println(text);
    }
}
