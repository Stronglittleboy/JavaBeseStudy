package com.hlw.java_base;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\workSoft\\steam\\steamapps\\workshop\\content\\431960");
        List<String> list = new ArrayList<>();
        List<String> path = getMp4File(file,list);
        path.forEach(item->FileUtil.move(new File(item),new File("D:\\data\\"),true));
    }

    private static List<String> getMp4File(File file, List<String> list) {
        if (file.isDirectory()){
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(file1 -> getMp4File(file1, list));
        }
        if (file.getName().endsWith(".mp4")) {
            list.add(file.getPath());
        }
        return list;
    }
}
