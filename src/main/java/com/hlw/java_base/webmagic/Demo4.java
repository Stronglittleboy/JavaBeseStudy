package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.NumberUtil;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @classname: Demo4
 * @author: LongWang·Hou
 * @description: 测试无头浏览器
 * @date: 2021/7/8 0008 10:39
 * @modified: LongWang·Hou
 */
@Slf4j
public class Demo4 {
    public static void main(String[] args) throws IOException {
//        /*配置基础选项*/
//        ChromeOptions options = new ChromeOptions();
//        /*配置页面加载策略 */
//        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//        /*设置无头模式*/
//        options.addArguments("-headless");
//        /*创建驱动*/
//        ChromeDriver webDriver = new ChromeDriver(options);
//        /*设置页面超时时间配置*/
//        WebDriver.Timeouts timeouts = webDriver.manage().timeouts();
//        timeouts.pageLoadTimeout(20, TimeUnit.SECONDS);
//        /*获取页面*/
//        webDriver.get("https://www.cnblogs.com/dk1024/p/11590510.html");
//        /*获取页面元素*/
//        WebElement webElement = webDriver.findElement(By.xpath("/html"));
//        /*获取页面内容*/
//        String content = webElement.getAttribute("outerHTML");
//        /*完整页面html*/
//        Html html = new Html(content);
//        System.out.println(html);
        String url = "https://lizst.classserkin.com/media/videos/mp4/236635.mp4?st=u6EYwJ7KDGA6mGnIpyCpew&e=1625769274";
        String name = "国产高质量A片性感少妇发浪 床上被狠狠操 叫声淫荡销魂";
        downFile(getSize(url),url,name);
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

    public static String getStr(long size, long wanSize) {
        return NumberUtil.decimalFormat("#.##%",
                new BigDecimal(wanSize).divide(new BigDecimal(size), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

    }
}
