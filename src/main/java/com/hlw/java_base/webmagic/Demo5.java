package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: Demo
 * @author: LongWang·Hou
 * @description: 使用并发编程 和消费者模式处理爬虫问题
 * @date: 621/7/3 0003 16:44
 * @modified: LongWang·Hou
 */
@Slf4j
public class Demo5 {

    private static volatile LinkedBlockingQueue<VideosVO> resource = new LinkedBlockingQueue<>(6);

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        List<Entity> all = Db.use().query("SELECT * FROM t_videos_av WHERE tags LIKE CONCAT('',?,'%') AND id >1313 ;", "性爱");

        List<String> fileNames = Arrays.stream(new File("E:\\movie test").list()).collect(Collectors.toList());
        System.setProperty("selenuim_config", "D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\config.ini");
        List<Entity> collect = all
                .stream()
                .filter(item -> {
                    String file_name = item.get("file_name").toString();
                    return !fileNames.contains(file_name);
                }).collect(Collectors.toList());

//        ThreadPoolExecutor executor = ThreadUtil.newExecutor(2, 2);
        new Thread(() -> {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            options.addArguments("-headless");
//            options.addArguments("-headless");
            ChromeDriver webDriver = new ChromeDriver(options);
            WebDriver.Timeouts timeouts = webDriver.manage().timeouts();
            timeouts.pageLoadTimeout(3000, TimeUnit.SECONDS);
            for (int i = 0; i < collect.size(); i++) {
                    System.out.println("生产者"+resource.size());
                    try {
                        Entity item = collect.get(i);
//                        if (doGetUrl(list, webDriver, item)) return;
                        String file_name = item.get("file_name").toString();
                        webDriver.get(item.get("source").toString());
                        WebElement webElement = webDriver.findElement(By.xpath("/html"));
                        String content = webElement.getAttribute("outerHTML");
                        Html html = new Html(content);
                        String mp4360Url = html.xpath("//video//source[@res='360']/@src").get();
                        System.out.println("id" + item.get("id") + "获取url ：\t" + file_name + "获取到url：\t" + mp4360Url);
                        resource.put(new VideosVO(file_name,mp4360Url));
                        if (resource.size() == 6){
                            System.out.println("线程满了" + Thread.currentThread());
                        }
                    } catch (Exception e) {
                        log.error("异常：{}", e);
                        webDriver = new ChromeDriver(options);
                    }
            }
        }).start();

        Runnable custom = () -> {
            while (true){
                System.out.println("消费者" + resource.size());
                try {
                    if (resource.size() == 0) {
                        System.out.println("不可以消费" + Thread.currentThread());
                    }
                    VideosVO poll = resource.take();
                    try {
                        downFile(getSize(poll.getMp4360Url()), poll.getMp4360Url(), poll.getFileName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 5; i++) {
            new Thread(custom).start();
        }
    }

    private static boolean doGetUrl(List<String> list, ChromeDriver webDriver, Entity item) throws InterruptedException {
        String file_name = item.get("file_name").toString();
        if (list.contains(file_name)) {
            return true;
        }
        list.add(file_name);
        webDriver.get(item.get("source").toString());
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Html html = new Html(content);
        String mp4360Url = html.xpath("//video//source[@res='360']/@src").get();
        System.out.println("id" + item.get("id") + "获取url ：\t" + file_name + "获取到url：\t" + mp4360Url);
        if (StringUtils.isBlank(mp4360Url)) {
            doGetUrl(list,webDriver,item);
        }
        resource.put(new VideosVO(file_name,mp4360Url));
        if (resource.size() == 6){
            System.out.println("线程满了" + Thread.currentThread());
        }
        return false;
    }

    private static void downFile(long size, String url, String file_name) {
        File file = new File("F:\\迅雷下载\\testVideos\\" + file_name + ".mp4");
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

    public static String getStr(long size, long wanSize) {
        return NumberUtil.decimalFormat("#.##%",
                new BigDecimal(wanSize).divide(new BigDecimal(size), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

    }
}
