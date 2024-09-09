import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfTest {
    public static void main(String[] args) throws IOException {
//        String expectedText = "Hello World!\n";
        File file = new File("test.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);



        try {
            int pageCount = document.getNumberOfPages();//pdf의 페이지 수
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for(int i=0;i<pageCount;i++)
            {
                BufferedImage imageObj = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);//pdf파일의 페이지를돌면서 이미지 파일 변환
                File outputfile = new File("test_" + (i+1) + ".jpg");//파일이름 변경(.pdf->.jpg)
                System.out.println(outputfile.getAbsolutePath());
                ImageIO.write(imageObj, "jpg", outputfile);//변환한 파일 업로드
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.close();
//        System.out.println(text);
    }
}
