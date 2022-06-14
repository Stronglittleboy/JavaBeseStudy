package com.hlw.java_base.webmagic.dmzj;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @classname: CommicVO
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/10 0010 19:04
 * @modified: LongWang·Hou
 */
@Data
public class CommicVO implements Serializable {
    private String name;
    private String author;
    private String status;
    private String hot;
    private String types;
    private String lastChapter;
}
