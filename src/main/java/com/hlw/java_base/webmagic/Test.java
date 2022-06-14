package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 * @classname: Test
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/4 0004 20:59
 * @modified: LongWang·Hou
 */
public class Test {
//    public static void main(String[] args) throws IOException {
//        int size;
//        URL url = new URL("https://lizst.classserkin.com/media/videos/mp4/236614.mp4?st=ofka6CaWJC4HqZDMlTLcAQ&e=1625410855");
//        URLConnection conn = url.openConnection();
//        size = conn.getContentLength();
//        if (size < 0) {
//            System.out.println("无法获取文件大小。");
//        }
//        else {
//            System.out.println(FileUtil.readableFileSize(size));
//        }
//
//        String strNumber = NumberUtil.decimalFormat("#.##%",
//                new BigDecimal(79567673).divide(new BigDecimal(size), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        System.out.println(strNumber);
//        System.out.println("文件大小为：" + size + " bytes");
//        conn.getInputStream().close();
//    }
public static void main(String[] args) {
//    Map<String, String> listMap = new HashMap<>();
//    listMap.put("123","https://saintsaens.classserkin.com/media/videos/mp4/48771.mp4?st=gWxTmJcipRf84qtdzFer0Q&e=1625402482");
//    listMap.put("153","https://saintsaens.classserkin.com/media/videos/mp4/46903.mp4?st=7R_pXPnUcrIRzWC-K2Zq2g&e=1625402478");
//    String s = listMap.values().toString();
//    ClipboardUtil.setStr(s);
//    System.out.println(15 % 15);
    System.out.println(StringUtils.isBlank(null));
}
}
