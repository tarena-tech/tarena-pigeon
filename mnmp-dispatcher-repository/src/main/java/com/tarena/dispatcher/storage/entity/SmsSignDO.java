package com.tarena.dispatcher.storage.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 短息类型消息签名表(NoticeSmsSign)实体类
 *
 * @author liuhuan
 */
@Data
public class SmsSignDO implements Serializable {
    private static final long serialVersionUID = 988125165135024152L;
    /**
     * 主键
     */

    private Long id;
    /**
     * 签名全称
     */
    private String name;
    /**
     * 描述
     */
    private String remark;

}
