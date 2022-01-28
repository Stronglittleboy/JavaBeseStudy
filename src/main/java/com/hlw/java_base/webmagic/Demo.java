package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.beans.Encoder;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: Demo
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/3 0003 16:44
 * @modified: LongWang·Hou
 */
@Slf4j
public class Demo {

    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
//        SELECT * FROM t_videos_av WHERE file_name LIKE '%浪%'
//        List<Entity> all = Db.use().findAll(Entity.create("t_videos_av").set("file_name", "LIKE '%浪%'"));
        List<Entity> all = Db.use().query("SELECT * FROM t_videos_av WHERE mp4_240_url LIKE CONCAT('',?,'%');", "https://lizst.classserkin.com/");
//        ThreadPoolExecutor executor = ThreadUtil.newExecutor(8, 16);

        List<String> list = new ArrayList<>();
        List<String> fileNames = Arrays.stream(new File("E:\\movie test").list()).collect(Collectors.toList());
        Map<String, String> listMap = new HashMap<>();
        System.setProperty("selenuim_config", "D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\config.ini");
        ChromeDriver webDriver = new ChromeDriver();
        all
                .stream()
                .filter(item -> {
                    String file_name = item.get("file_name").toString();
                    return !fileNames.contains(file_name);
                })
                .forEach(item -> {
                    String file_name = item.get("file_name").toString();
                    if (list.contains(file_name)) {
                        return;
                    }
                    list.add(file_name);
                    String mp4_360_url = (String) item.get("mp4_360_url");
                    WebDriver.Navigation navigate = webDriver.navigate();
                    navigate.to(item.get("source").toString());
//                    executor.submit(() -> {
//                                try {
////                                    downFile(getSize(mp4_360_url),mp4_360_url, file_name);
//                                } catch (Exception e) {
//                                    log.error("异常：",e);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    WebElement webElement = webDriver.findElement(By.xpath("/html"));
                    String content = webElement.getAttribute("outerHTML");
                    Html html = new Html(content);
                    String mp4360Url = html.xpath("//video//source[@res='360']/@src").get();

                    listMap.put(file_name, mp4360Url);
                });
        webDriver.close();
        String s = listMap.values().toString();
        ClipboardUtil.setStr(s);
//                );
//        executor.shutdown();
    }

    private static void downFile(long size, String url, String file_name) {
        HttpUtil.downloadFile(url
                , new File("E:\\movie test\\" + file_name + ".mp4"), new StreamProgress() {
                    @Override
                    public void start() {
                        log.info("文件名：【{}】 开始下载", file_name);
                    }

                    @SneakyThrows
                    @Override
                    public void progress(long progressSize) {
                        System.out.println(FileUtil.readableFileSize(size) + FileUtil.readableFileSize(progressSize));
                        log.info("文件名：【{}】 完成大小：{} 进度：{}", file_name,
                                FileUtil.readableFileSize(progressSize), getStr(size, progressSize));
                    }

                    @Override
                    public void finish() {
                        log.info("文件名：【{}】 下载完成", file_name);
                    }
                });
    }

    public static String getStr(long size, long wanSize) throws IOException {
        return NumberUtil.decimalFormat("#.##%",
                new BigDecimal(wanSize).divide(new BigDecimal(size), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

    }

    private static long getSize(String urlPath) throws IOException {
        int size;
        URL url = new URL(urlPath);
        URLConnection conn = url.openConnection();
        size = conn.getContentLength();
        if (size < 0) {
            throw new RuntimeException("无法获取文件大小");
        }
        return size;
    }
}
