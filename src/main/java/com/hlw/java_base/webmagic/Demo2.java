package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
public class Demo2 {
    private static volatile Map<String,String> listMap = new HashMap<>();
    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        List<Entity> all = Db.use().query("SELECT * FROM t_videos_av WHERE tags LIKE CONCAT('',?,'%') LIMIT 30,5000;", "性爱");

        List<String> list = new ArrayList<>();
        List<String> fileNames = Arrays.stream(new File("E:\\movie test").list()).collect(Collectors.toList());
        System.setProperty("selenuim_config", "D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\config.ini");
        List<Entity> collect = all
                .stream()
                .filter(item -> {
                    String file_name = item.get("file_name").toString();
                    return !fileNames.contains(file_name);
                }).collect(Collectors.toList());
        ChromeDriver webDriver = new ChromeDriver();
        WebDriver.Timeouts timeouts = webDriver.manage().timeouts();
        timeouts.pageLoadTimeout(20, TimeUnit.SECONDS);
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < collect.size(); i++) {
//            int finalI = i;
//            ThreadUtil.newExecutor().submit(()->{
                Entity item = collect.get(i);
                String file_name = item.get("file_name").toString();
                if (list.contains(file_name)) {
                    return;
                }
                list.add(file_name);
                webDriver.get(item.get("source").toString());
                WebElement webElement = webDriver.findElement(By.xpath("/html"));
                String content = webElement.getAttribute("outerHTML");
                Html html = new Html(content);
                String mp4360Url = html.xpath("//video//source[@res='360']/@src").get();
                listMap.put(file_name, mp4360Url);
                System.out.println("获取url ：\t" + file_name + "获取到url：\t"+mp4360Url);
                map.put(file_name,mp4360Url);
//                ThreadUtil.newExecutor(6,12).submit(()-> {
//                    try {
//                        downFile(getSize(mp4360Url),mp4360Url,file_name);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            });
        }
        System.out.println(JSON.toJSONString(map));
//        webDrivers.forEach(WebDriver::close);
//        System.out.println(JSON.toJSONString(listMap));

    }

    private static void downFile(long size, String url, String file_name) {
        File file = new File("E:\\movie test\\testVideos\\" + file_name + ".mp4");
        if (file.exists()) {
            return;
        }
        HttpUtil.downloadFile(url
                , file, new StreamProgress() {
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
    public static String getStr(long size, long wanSize) throws IOException {
        return NumberUtil.decimalFormat("#.##%",
                new BigDecimal(wanSize).divide(new BigDecimal(size), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

    }
}
