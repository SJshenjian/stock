package com.haotu369.util.ocr;

import com.haotu369.base.ContextPath;
import org.python.jline.internal.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.util.concurrent.Callable;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/9
 */
public class OcrCallable implements Callable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OcrCallable.class);

    private String taskNumber;
    private String tesseractWindowPath;
    private String tesseractUnixPath;

    public OcrCallable(String taskNumber, String tesseractWindowPath, String tesseractUnixPath) {
        this.taskNumber = taskNumber;
        this.tesseractWindowPath = tesseractWindowPath;
        this.tesseractUnixPath = tesseractUnixPath;
    }

    @Override
    public Object call() throws Exception {
        String fileName = "stock_school_" + taskNumber + ".tif";

        if (tesseractWindowPath != null) { // windows环境条件
            long start = System.currentTimeMillis();

            String path = ContextPath.getContextPath("script/OcrPdfWindow.bat");
            String command = "cmd /c " + path;// 不能用 /b 否则回显内容无法读取

            String[] evp = new String[3];
            evp[0] = "disk=" + "d";
            evp[1] = "dir=" + tesseractWindowPath;
            evp[2] = "filename=" + fileName;

            // 执行脚本
            Process process = Runtime.getRuntime().exec(command, evp);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while ((result = bufferedReader.readLine()) != null) {
                LOGGER.info("当前执行解析" + fileName + ",命令为：" + result);
            }
            bufferedReader.close();
            return "解析" + fileName + "成功，花费" + (System.currentTimeMillis() - start) / 1000 / 60 + "分钟";
        } else {
            // TODO linux 环境
        }
        return "解析" + fileName + "异常";
    }
}
