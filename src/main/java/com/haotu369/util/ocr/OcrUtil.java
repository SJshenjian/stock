package com.haotu369.util.ocr;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/10
 */
public class OcrUtil {

    @Value("${tesseract.windows.path}")
    private static String tesseractWindowPath;

    @Value("${tesseract.unix.path}")
    private static String tesseractUnixPath;

    /**
     * PDF 文件 OCR 处理，以便pdf.js全局搜索识别
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void doOcr() throws ExecutionException, InterruptedException {
        if (null == tesseractWindowPath &&  null == tesseractUnixPath) {
            throw new RuntimeException("tesseractWindowPath 或 tesseractUnixPath 未配置");
        }
        int poolSize = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Future> futureList = new ArrayList<>();

        for (int i = 1; i <= poolSize; i++) {
            Callable callable = new OcrCallable(String.valueOf(i), tesseractWindowPath, tesseractUnixPath);
            Future future = executorService.submit(callable);
            futureList.add(future);
        }
        executorService.shutdown();

        for (Future future : futureList) {
            System.out.println(future.get());
        }
    }
}
