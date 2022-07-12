package com.tarena.dispatcher.storage.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 消息模板主表(NoticeTemplate)
 *
 * @author liuhuan
 */
@Data
public class TemplateDO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 模板全称
     */
    private String name;
    /**
     * 模板CODE码
     */
    private String code;
    /**
     * 模板内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer noticeType;
    /**
     * 所属应用
     */
    private Long appId;
    private String appCode;

}
