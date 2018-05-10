package com.haotu369.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 选股学堂
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/6
 */
@Controller
@RequestMapping("/school")
public class StockSchoolAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockSchoolAction.class);

    @RequestMapping("/index")
    public String index() {
        return "school/index";
    }

    @RequestMapping(value = "/getSchoolContent", method = RequestMethod.GET)
    public void getSchoolContent(@RequestParam("type") String type, HttpServletResponse response) {
        FileInputStream inputStream = null;
        try {
            String fileName = "stock_school_" + type + ".pdf";
            Resource resource = new ClassPathResource("/static/upload/" + fileName);
            File file = resource.getFile();
            if (file.exists()) {
                inputStream = new FileInputStream(file);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                response.getOutputStream().write(bytes);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("StockSchool --> getSchoolContent : FileNotFoundException {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("StockSchool --> getSchoolContent : IOException {}", e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("StockSchool --> getSchoolContent : IOException {}", e.getMessage());
            }
        }
    }
}
