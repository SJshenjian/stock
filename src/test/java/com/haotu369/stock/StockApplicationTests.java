package com.haotu369.stock;

import com.googlecode.jhocr.converter.HocrToPdf;
import com.googlecode.jhocr.util.enums.FExt;
import com.googlecode.jhocr.util.enums.PDFF;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoCounter;
import com.sun.org.apache.xpath.internal.SourceTree;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.PdfUtilities;
import net.sourceforge.tess4j.util.Utils;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationTests {
    private Tesseract tesseract;
    private static final String LANGUAGE_PATH = "src/test/resources/tessdata";
    private static String testFileResultsPath = "src/test/resources/test-results";
    private static String testJPG = "src/test/resources/test-results/test.jpg";
    private String testFileName = "hello";
    private Resource resource;
    private File file;

    @Value("${tesseract.windows.path}")
    private String tesseract_window_path;
    @Value("${tesseract.unix.path}")
    private String tesseract_unix_path;

    @Before
    public void setUp() throws IOException {
        tesseract = new Tesseract();
        resource = new ClassPathResource("/static/upload/ocr_test.pdf");
        file = resource.getFile();
    }

    @Test
    public void testOcr() throws IOException, TesseractException {
        // 设置训练语言的路径
        tesseract.setDatapath(LANGUAGE_PATH);
        tesseract.setLanguage("chi_sim");
        tesseract.setHocr(true);
        String context = tesseract.doOCR(file);

        // 转化为pdf(此版本不支持中文)
        File pdfAbsFileName = new File(testFileResultsPath, String.format("%s.%s", testFileName, FExt.PDF));

        try {
            FileOutputStream os = new FileOutputStream(pdfAbsFileName);
            HocrToPdf hocrToPdf = new HocrToPdf(os);

            InputStream hocrInputStream = new ByteArrayInputStream(context.getBytes());
            InputStream imgInputStream = new FileInputStream(new File(testJPG));
            hocrToPdf.addHocrDocument(hocrInputStream, imgInputStream);
            hocrToPdf.setPdfFormat(PDFF.PDF_A_1B);

            hocrToPdf.convert();

            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(context);
    }

    @Test
    public void testOcr2() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
       if (true) { // windows环境条件
           if (null == tesseract_window_path) {
               throw new NullPointerException("tesseract windows 环境属性未配置");
           }
           // tiff临时存在位置
           File tempFileDir = new File(tesseract_window_path + "/temp");
           tempFileDir.mkdirs();

           // pdf文件转tiff
           File tiff = PdfUtilities.convertPdf2Tiff(file);
           File tempTiff = new File(tempFileDir, tiff.getName());
           FileUtils.copyFile(tiff, tempTiff);
           tiff.delete();
           System.out.println("pdf转换成功，花费" + (System.currentTimeMillis() - start) / 1000 / 60 + "分钟");

           String[] evp = new String[3];
           evp[0] = "disk=" + "d";
           evp[1] = "dir=" + tesseract_window_path;
           evp[2] = "filename=" + tiff.getName();

           // 执行脚本
           Process process = runtime.exec("cmd /c start /b E:\\MyProject\\stock\\src\\main\\resources\\script\\ocr_pdf_window.bat", evp);

           InputStream inputStream = process.getInputStream();
           byte[] bytes = new byte[1024];
           while (inputStream.read(bytes) != -1) {
               if(new String(bytes, "UTF-8").contains("echo success")){
                   break;
               }
           }
           System.out.println("解析成功，花费" + (System.currentTimeMillis() - start) / 1000 / 60 + "分钟");
       } else { // unix环境

       }
    }
}