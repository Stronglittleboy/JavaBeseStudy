package com.hlw.java_base.webmagic;

import cn.hutool.http.HttpUtil;

/**
 * @version 1.0.0
 * @classname: Mp4Util
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/3 0003 15:44
 * @modified: LongWang·Hou
 */
public class Mp4Util {
    public static void main(String[] args) {

//        HttpUtil.downloadFileFromUrl("https://lizst.classserkin.com/media/videos/mp4/236418.mp4?st=0tX2nAIFOBU8rh1KTFefRA&e=1625305365","D:\\loubok\\");
        HttpUtil.downloadFile("https://rachno2.classserkin.com/media/videos/mp4/159.mp4?st=-jpvtQcFjGzdRjkuM9X6Vw&e=1625371137","D:\\louShow");
    }
}
