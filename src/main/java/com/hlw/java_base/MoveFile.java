package com.hlw.java_base;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * @version 1.0.0
 * @classname: MoveFile
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/5 0005 19:41
 * @modified: LongWang·Hou
 */
public class MoveFile {
    private final static  String filePath = "G:\\迅雷下载";
    public static void main(String[] args) {
        File file = new File(filePath);
        moveFile(file);
    }

    private static void moveFile(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String s : list) {
                File file1 = new File(file.getAbsolutePath()+"\\"+s);
               moveFile(file1);
            }
        }else {
            if (file.getName().contains(".torrent")){
                return;
            }
            if (file.getName().endsWith(".mp4")){
                FileUtil.move(file,new File(filePath+file.getName()),false);
            }
        }
    }
}
