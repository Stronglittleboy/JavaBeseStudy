package com.hlw.java_base.webmagic;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.beans.Encoder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: Demo
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/3 0003 16:44
 * @modified: LongWang·Hou
 */
@Slf4j
public class Demo {

    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
//        SELECT * FROM t_videos_av WHERE file_name LIKE '%浪%'
//        List<Entity> all = Db.use().findAll(Entity.create("t_videos_av").set("file_name", "LIKE '%浪%'"));
        List<Entity> all = Db.use().query("SELECT * FROM t_videos_av WHERE file_name LIKE CONCAT('%',?,'%');", "叫");
        ThreadPoolExecutor executor = ThreadUtil.newExecutor(8, 16);

        List<String> list = new ArrayList<>();
        List<String> fileNames = Arrays.stream(new File("E:\\movie test").list()).collect(Collectors.toList());
        all
                .stream()
                .filter(item->{
                    String file_name = item.get("file_name").toString();
                    return !fileNames.contains(file_name);
                })
                .forEach(item -> {
                            String file_name = item.get("file_name").toString();
                            if (list.contains(file_name)) {
                                return;
                            }
                            list.add(file_name);
                            executor.submit(() -> {
                                HttpUtil.downloadFile((String) item.get("mp4_360_url")
                                        , new File("E:\\movie test\\" + file_name + ".mp4"), new StreamProgress() {
                                            @Override
                                            public void start() {
                                                log.info("文件名：【{}】 开始下载", file_name);
                                            }

                                            @Override
                                            public void progress(long progressSize) {
                                                log.info("文件名：【{}】 进度：{}", file_name,
                                                        FileUtil.readableFileSize(progressSize));
                                            }

                                            @Override
                                            public void finish() {
                                                log.info("文件名：【{}】 下载完成", file_name);
                                            }
                                        });
                            });
                        }
                );
        executor.shutdown();
    }
}
