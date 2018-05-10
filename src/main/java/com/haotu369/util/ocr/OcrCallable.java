package com.haotu369.util.ocr;

import net.sourceforge.tess4j.util.PdfUtilities;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/9
 */
public class OcrCallable implements Callable {
    private String taskNumber;
    private String tesseractWindowPath;
    private String tesseractUnixPath;

    public OcrCallable(String taskNumber, String tesseractWindowPath, String tesseractUnixPath) {
        this.taskNumber = taskNumber;
        this.tesseractWindowPath = tesseractWindowPath;
        this.tesseractUnixPath =tesseractUnixPath;
    }

    @Override
    public Object call() throws Exception {
        if (tesseractWindowPath != null) { // windows环境条件
            long start = System.currentTimeMillis();
            // tiff临时存在位置
            File tempFileDir = new File(tesseractWindowPath + "/temp");
            tempFileDir.mkdirs();

            // pdf文件转tiff
            Resource resource = new ClassPathResource("/ocr/stock_school_" + taskNumber + ".pdf");
            File tiff = PdfUtilities.convertPdf2Tiff(resource.getFile());
            File tempTiff = new File(tempFileDir, tiff.getName());

            FileUtils.copyFile(tiff, tempTiff);
            tiff.delete();

            String[] evp = new String[3];
            evp[0] = "disk=" + "d";
            evp[1] = "dir=" + tesseractWindowPath;
            evp[2] = "filename=" + tiff.getName();

            // 执行脚本
            // TODO 路径优化
            Process process = Runtime.getRuntime().exec("cmd /c start /b E:\\Project\\java\\stock\\src\\main\\resources\\script\\ocr_pdf_window.bat", evp);

            InputStream inputStream = process.getInputStream();
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1) {
                if (new String(bytes, "UTF-8").contains("echo success")) {
                    break;
                }
            }

            return "解析" + taskNumber + "成功，花费" + (System.currentTimeMillis() - start) / 1000 / 60 + "分钟";
        } else {
            // TODO linux 环境
        }
        return "解析" + taskNumber + "异常";
    }
}
