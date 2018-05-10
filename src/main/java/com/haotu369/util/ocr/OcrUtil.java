package com.haotu369.util.ocr;

import net.sourceforge.tess4j.util.PdfUtilities;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/10
 */
public class OcrUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(OcrUtil.class);

    //配置tesseract安装路径 TODO 配置使用@Value 在配置文件种配置
    private static final String tesseractWindowPath = "D://Program Files (x86)/Tesseract-OCR";
    private  static  final String tesseractUnixPath = "/";

    /**
     * PDF 文件 OCR 处理，以便pdf.js全局搜索识别
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void doOcr() throws ExecutionException, InterruptedException, IOException {
        if (null == tesseractWindowPath &&  null == tesseractUnixPath) {
            LOGGER.error("tesseractWindowPath 或 tesseractUnixPath 未配置");
        }

        int poolSize = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Future> futureList = new ArrayList<>();

        for (int i = 1; i <= poolSize; i++) {
            parsePdfToTiff(i);
            Callable callable = new OcrCallable(String.valueOf(i), tesseractWindowPath, tesseractUnixPath);
            Future future = executorService.submit(callable);
            futureList.add(future);
        }
        executorService.shutdown();

        for (Future future : futureList) {
            LOGGER.info(future.get().toString());
        }
    }

    private static void parsePdfToTiff(int taskNum) throws IOException {
        // tiff临时存在位置
        File tempFileDir = new File(tesseractWindowPath + "/temp");
        tempFileDir.mkdirs();

        String fileName = "stock_school_" + taskNum;
        // pdf文件转tiff
        Resource resource = new ClassPathResource("/ocr/" + fileName + ".pdf");
        File tiff = PdfUtilities.convertPdf2Tiff(resource.getFile());
        File tempTiff = new File(tempFileDir, fileName+".tif");

        FileUtils.copyFile(tiff, tempTiff);
    }
}
