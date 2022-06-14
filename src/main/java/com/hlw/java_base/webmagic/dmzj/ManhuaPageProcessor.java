package com.hlw.java_base.webmagic.dmzj;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.RejectPolicy;
import com.hlw.java_base.webmagic.Mp4Service2;
import com.hlw.java_base.webmagic.MyMp4Pinine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: ManhuaPageProcessor
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/10 0010 18:47
 * @modified: LongWang·Hou
 */
@Slf4j
public class ManhuaPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public void process(Page page) {
        Html html =
                page.getHtml();
        log.info("页面：{}",html);
        /*获取视频访问的url*/
        List<String> all1 = html.xpath("//*[@id=\"search_list_div\"]//a").links().all();
        log.info("打印所有视频访问{}",all1);
        page.addTargetRequests(all1);
        /*下一页数据*/
        List<String> all2 = html.xpath("//div[@class='pages']").links().all();
        log.info("打印所有的翻页url{}",all2);
//        page.addTargetRequests(all2);
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
        ThreadPoolExecutor executor = ExecutorBuilder.create()
                .setCorePoolSize(6)
                .setMaxPoolSize(12)
                .setWorkQueue(new LinkedBlockingQueue<>())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(2000)
                .setHandler(RejectPolicy.DISCARD_OLDEST.getValue())
                .build();

        Spider
                .create(new ManhuaPageProcessor())
                .setDownloader(
                        new SeleniumDownloader("D:\\workHolder\\BrowserDriver\\chromedriver.exe")
                                .setSleepTime(5000)
                )
                .setScheduler(
                        new FileCacheQueueScheduler("d:/")
                                .setDuplicateRemover(new HashSetDuplicateRemover())
                )
                .setExecutorService(executor)
                .addPipeline(pinine)
                .addUrl("http://manhua.dmzj.com/tags/category_search/0-0-0-all-0-0-0-1.shtml#category_nav_anchor")
                .thread(1)
                .run();
    }
}
