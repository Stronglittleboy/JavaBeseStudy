package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version 1.0.0
 * @classname: jklkljkl
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/5 0005 0:09
 * @modified: LongWang·Hou
 */
@Slf4j
public class jklkljkl {
    public static void main(String[] args) throws IOException {
//        String string = "https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555";
//        String name = string.substring(0,string.indexOf(".mp4"));
//        String substring = name.substring(name.lastIndexOf("/")+1);
//        System.out.println(substring);

       String[] strings = new String[] {"https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555"," https://lizst.classserkin.com/media/videos/mp4/236580.mp4?st=uTpQ_UT4t3WpnkewSZkcHQ&e=1625421496"," https://lizst.classserkin.com/media/videos/mp4/236579.mp4?st=nxiIkybbk753W4bToqTn6Q&e=1625421654"," https://lizst.classserkin.com/media/videos/mp4/236625.mp4?st=u8kt-49h7wyB3fiX_QnQ7w&e=1625421532"," https://lizst.classserkin.com/media/videos/mp4/236621.mp4?st=beI8Hc_lSXn0VNEI8ROpbg&e=1625421712"," https://lizst.classserkin.com/media/videos/mp4/236636.mp4?st=xThlEwzsj1fdRsjWi9bpnw&e=1625421437"," https://lizst.classserkin.com/media/videos/mp4/236639.mp4?st=7e6j8xv5RvhnisstR9Wc-Q&e=1625421493"," https://lizst.classserkin.com/media/videos/mp4/236580.mp4?st=uTpQ_UT4t3WpnkewSZkcHQ&e=1625421496"," https://lizst.classserkin.com/media/videos/mp4/236625.mp4?st=u8kt-49h7wyB3fiX_QnQ7w&e=1625421532"," https://lizst.classserkin.com/media/videos/mp4/236621.mp4?st=beI8Hc_lSXn0VNEI8ROpbg&e=1625421712"," https://lizst.classserkin.com/media/videos/mp4/236316.mp4?st=prQujAvweuq6WOZHTEo_Uw&e=1625421594"," https://lizst.classserkin.com/media/videos/mp4/236579.mp4?st=nxiIkybbk753W4bToqTn6Q&e=1625421654"," https://lizst.classserkin.com/media/videos/mp4/236316.mp4?st=prQujAvweuq6WOZHTEo_Uw&e=1625421594"," https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555"," https://lizst.classserkin.com/media/videos/mp4/236395.mp4?st=V2alb02e4tIj-Hgl2JYC9w&e=1625421559"," https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555"," https://lizst.classserkin.com/media/videos/mp4/236580.mp4?st=uTpQ_UT4t3WpnkewSZkcHQ&e=1625421496"," https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555"," https://lizst.classserkin.com/media/videos/mp4/236625.mp4?st=u8kt-49h7wyB3fiX_QnQ7w&e=1625421532"," https://lizst.classserkin.com/media/videos/mp4/228675.mp4?st=NX72aYi6Cvrmyy-FPzszOQ&e=1625421538"," https://lizst.classserkin.com/media/videos/mp4/236580.mp4?st=uTpQ_UT4t3WpnkewSZkcHQ&e=1625421496"," https://lizst.classserkin.com/media/videos/mp4/236393.mp4?st=SPoKicLNmjYMThHfVAJgbg&e=1625421629"," https://lizst.classserkin.com/media/videos/mp4/228675.mp4?st=NX72aYi6Cvrmyy-FPzszOQ&e=1625421538"," https://lizst.classserkin.com/media/videos/mp4/236579.mp4?st=nxiIkybbk753W4bToqTn6Q&e=1625421654"," https://lizst.classserkin.com/media/videos/mp4/236316.mp4?st=prQujAvweuq6WOZHTEo_Uw&e=1625421594"," https://lizst.classserkin.com/media/videos/mp4/236621.mp4?st=beI8Hc_lSXn0VNEI8ROpbg&e=1625421712"," https://lizst.classserkin.com/media/videos/mp4/236395.mp4?st=V2alb02e4tIj-Hgl2JYC9w&e=1625421559"," https://lizst.classserkin.com/media/videos/mp4/236636.mp4?st=xThlEwzsj1fdRsjWi9bpnw&e=1625421437"," https://lizst.classserkin.com/media/videos/mp4/228675.mp4?st=NX72aYi6Cvrmyy-FPzszOQ&e=1625421538"," https://lizst.classserkin.com/media/videos/mp4/236395.mp4?st=V2alb02e4tIj-Hgl2JYC9w&e=1625421559"," https://lizst.classserkin.com/media/videos/mp4/236393.mp4?st=SPoKicLNmjYMThHfVAJgbg&e=1625421629"," https://lizst.classserkin.com/media/videos/mp4/236393.mp4?st=SPoKicLNmjYMThHfVAJgbg&e=1625421629"," https://lizst.classserkin.com/media/videos/mp4/228675.mp4?st=NX72aYi6Cvrmyy-FPzszOQ&e=1625421538"," https://lizst.classserkin.com/media/videos/mp4/236395.mp4?st=V2alb02e4tIj-Hgl2JYC9w&e=1625421559"," https://lizst.classserkin.com/media/videos/mp4/236316.mp4?st=prQujAvweuq6WOZHTEo_Uw&e=1625421594"," https://lizst.classserkin.com/media/videos/mp4/236579.mp4?st=nxiIkybbk753W4bToqTn6Q&e=1625421654"," https://lizst.classserkin.com/media/videos/mp4/236625.mp4?st=u8kt-49h7wyB3fiX_QnQ7w&e=1625421532"," https://lizst.classserkin.com/media/videos/mp4/236579.mp4?st=nxiIkybbk753W4bToqTn6Q&e=1625421654"," https://lizst.classserkin.com/media/videos/mp4/236639.mp4?st=7e6j8xv5RvhnisstR9Wc-Q&e=1625421493"," https://lizst.classserkin.com/media/videos/mp4/236621.mp4?st=beI8Hc_lSXn0VNEI8ROpbg&e=1625421712"," https://lizst.classserkin.com/media/videos/mp4/236316.mp4?st=prQujAvweuq6WOZHTEo_Uw&e=1625421594"," https://lizst.classserkin.com/media/videos/mp4/236639.mp4?st=7e6j8xv5RvhnisstR9Wc-Q&e=1625421493"," https://lizst.classserkin.com/media/videos/mp4/236636.mp4?st=xThlEwzsj1fdRsjWi9bpnw&e=1625421437"," https://lizst.classserkin.com/media/videos/mp4/236639.mp4?st=7e6j8xv5RvhnisstR9Wc-Q&e=1625421493"," https://lizst.classserkin.com/media/videos/mp4/236612.mp4?st=uHBDHPrSeNnJaEJ9NswwoA&e=1625421555"};
        System.out.println(strings.length);
        for (String string : strings) {
            ThreadUtil.newExecutor(6,12).submit(()->{
                String name = string.substring(0,string.indexOf(".mp4"));
                String substring = name.substring(name.lastIndexOf("/")+1);
                try {
                    downFile(getSize(string),string,substring);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
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
