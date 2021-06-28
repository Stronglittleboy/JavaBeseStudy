package com.hlw.java_base;

import com.lmax.disruptor.RingBuffer;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @version 1.0.0
 * @classname: Rename
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/22 0022 23:05
 * @modified: LongWang·Hou
 */
public class Rename {
    public static void main(String[] args) {
         String path = "G:\\迅雷下载\\";
        File file = new File("G:\\迅雷下载\\");
        for (String s : file.list()) {
            if (s.contains("迅雷下载")){
                System.out.println(s);
                String name = "G:\\迅雷下载\\" + s.replace("迅雷下载", "");
                System.out.println(name);
//                new File(s).renameTo(new File("G:\\迅雷下载\\"+s.replace("迅雷下载","")));
                new File(path+s).renameTo(new File(name));
                System.out.println("");
            }
        }
    }
}
