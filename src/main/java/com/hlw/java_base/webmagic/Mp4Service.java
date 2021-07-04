package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @classname: Mp4Service
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/30 0030 21:06
 * @modified: LongWang·Hou
 */
@Slf4j
public class Mp4Service implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static String BASE_SITE = "http://91porn.com/v.php?next=watch";
    @Override
    public void process(Page page) {
        Html html =
                page.getHtml();
        System.out.println(html);
        /*1.下一页的数据*/
        if (page.getUrl().get().matches("\\S*next=watch\\S*")){
            List<String> all = html.xpath("//div[@class=\"row\"]").links().all();
            List<String> nextUrls = html.xpath("//div[@class='pagingnav']").links().all();
            page.addTargetRequests(all);
            page.addTargetRequests(nextUrls);
            FileWriter fileWriter = new FileWriter("D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\mp3_videos.txt");
            fileWriter.appendLines(all);
            return;
        }
        String s = html.xpath("//source/@src").get();
        System.out.println(s+"========================");
        FileWriter writer = new FileWriter("D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\test.txt");
        writer.append(s+"\n");
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.setProperty("selenuim_config", "D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\config.ini");
        Request request = new Request("http://91porn.com/v.php?next=watch");
        request.addCookie("language","cn_CN");
        request.addCookie("cf_clearance","30dbbfcbd3171d40520355c6e302061ecbad41ad-1625296592-0-150");
        request.addCookie("CLIPSHARE","9lgk61blai64dqh1givvd0q3gv");

        Spider
                .create(new Mp4Service())
                .setDownloader(new SeleniumDownloader("D:\\workHolder\\BrowserDriver\\chromedriver.exe").setSleepTime(20000))
                .setScheduler(new FileCacheQueueScheduler("e:/").setDuplicateRemover(new HashSetDuplicateRemover()))
                .addRequest(request)
                .thread(5).run();
    }
}
