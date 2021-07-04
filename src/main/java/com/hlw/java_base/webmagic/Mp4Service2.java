package com.hlw.java_base.webmagic;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: Mp4Service
 * @author: LongWang·Hou
 * @description: http://www.avtb01.com/popular/ 针对这个网站做爬虫处理 爬取目标 视频地址 360p 和 240p
 * @date: 2021/6/30 0030 21:06
 * @modified: LongWang·Hou
 */
@Slf4j
public class Mp4Service2 implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public void process(Page page) {
        Html html =
                page.getHtml();
        System.out.println(html);
        /*获取视频访问的url*/
        List<String> all1 = html.xpath("//div[@class='panel-body panel-padding']//ul").links().all();
        log.info("打印所有视频访问{}",all1);
        page.addTargetRequests(all1);
        /*下一页数据*/
        List<String> all2 = html.xpath("//nav[@class='text-center']").links().all();
        log.info("打印所有的翻页url{}",all2);
        page.addTargetRequests(all2);
        /*1.下一页的数据*/
        String fileName = html.xpath("//*[@id=\"video\"]/h1/text()").get();
        if (null != fileName){
            log.info("文件名称：{}",fileName);
            String mp4360Url = html.xpath("//video//source[@res='360']/@src").get();
            String mp4240Url = html.xpath("//video//source[@res='240']/@src").get();
            log.info("获取的mp4：{}，mp3:{}",mp4360Url,mp4240Url);
            List<String> tags = html.xpath("//div[@class='content-container']//a/text()").all()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(StringUtils::isNotEmpty)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            log.info("所有的标签内容：{}",tags);
            page.putField("fileName",fileName);
            page.putField("mp4360Url",mp4360Url);
            page.putField("mp4240Url",mp4240Url);
            page.putField("tags",String.join(",",tags));
            page.putField("source",page.getUrl().get());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.setProperty("selenuim_config", "D:\\workProject\\JavaBeseStudy\\src\\main\\resources\\config.ini");

        MyMp4Pinine pinine = new MyMp4Pinine();

        Spider
                .create(new Mp4Service2())
                .setDownloader(new SeleniumDownloader("D:\\workHolder\\BrowserDriver\\chromedriver.exe")
                        .setSleepTime(5000))
                .setScheduler(new FileCacheQueueScheduler("d:/").setDuplicateRemover(new HashSetDuplicateRemover()))
                .addPipeline(pinine)
                .addUrl("http://www.avtb01.com/popular/")
                .thread(10)
                .run();
    }
}
