package com.hlw.java_base.webmagic;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @classname: VideosVO
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/8 0008 10:18
 * @modified: LongWang·Hou
 */
@Data
public class VideosVO implements Serializable {
    private String fileName;
    private String mp4360Url;

    public VideosVO(String fileName, String mp4360Url) {
        this.fileName = fileName;
        this.mp4360Url = mp4360Url;
    }

    public VideosVO() {
    }
}
