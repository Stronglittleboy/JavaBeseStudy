package com.hlw.java_base.webmagic;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * @version 1.0.0
 * @classname: MyMp4Pinine
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/3 0003 16:28
 * @modified: LongWang·Hou
 */
@Slf4j
public class MyMp4Pinine implements Pipeline {

    @SneakyThrows
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> all = resultItems.getAll();
        if (all.isEmpty()) {
            return;
        }
        log.info("爬到的所有的数据内容:{}", all);
        Object fileName = resultItems.get("fileName");
        List<Entity> list = Db.use().query("SELECT id FROM t_videos_av WHERE file_name = ?", fileName);
        if (!list.isEmpty()){
            return;
        }
        Db.use().insert(
                Entity.create("t_videos_av")
                        .set("file_name", fileName)
                        .set("mp4_360_url", StringUtils.defaultString(resultItems.get("mp4360Url")))
                        .set("mp4_240_url", StringUtils.defaultString(resultItems.get("mp4240Url")))
                        .set("tags", resultItems.get("tags"))
                        .set("source", resultItems.get("source"))
        );
    }
}


//    CREATE TABLE `t_videos_av` (
//        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
//        `file_name` VARCHAR (64) NOT NULL DEFAULT '' COMMENT 'av名称',
//        `mp4_360_url` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '360p地址',
//        `mp4_240_url` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '260p地址',
//        `tags` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '标签',
//        `source` VARCHAR (255) NOT NULL DEFAULT '' COMMENT '来源 爬虫url',
//        `data_status` TINYINT (1) NOT NULL DEFAULT '1' COMMENT '数据状态 0无效 1有效',
//        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
//        `created_by` VARCHAR (64) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
//        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
//        `updated_by` VARCHAR (64) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
//        PRIMARY KEY (`id`)
//        ) COMMENT = '小说信息表';
