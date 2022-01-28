package com.hlw.java_base.webmagic;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import us.codecraft.webmagic.selector.Html;

import java.io.*;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: Demo6
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/9 0009 22:11
 * @modified: LongWang·Hou
 */
public class Demo6 {
    public static void main(String[] args) throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//            options.addArguments("-headless");
        // start the browser up
        ChromeDriver webDriver = new ChromeDriver(options);

        WebDriver.Timeouts timeouts = webDriver.manage().timeouts();
        timeouts.pageLoadTimeout(3000, TimeUnit.SECONDS);


        // open yahoo.com
        webDriver.get("https://manhua.dmzj.com/zqlr/720.shtml#@page=15");

        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Html html = new Html(content);
        String s = html.xpath("//div[@id='center_box']//img/@src").get();
        System.out.println(s);

        List<HttpCookie> cookieList = webDriver.manage().getCookies()
                .stream().map(item -> new HttpCookie(item.getName(), item.getValue()))
                .collect(Collectors.toList());
        Map<String, String> map = webDriver.manage().getCookies().stream()
                .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));

        System.out.println("https:"+s);
        map.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("Referer", "https://manhua.dmzj.com/");
            put("sec-ch-ua", " Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"91\", \"Chromium\";v=\"91");
            put("sec-ch-ua-mobile", "?0");
            put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        }};
        byte[] bytes = HttpRequest.get("https:"+s)
                .addHeaders(hashMap)
                .cookie(cookieList)
                .execute()
                .bodyBytes();
        IOUtils.write(bytes, new FileOutputStream(("F:\\22343.jpg")));
    }
}
