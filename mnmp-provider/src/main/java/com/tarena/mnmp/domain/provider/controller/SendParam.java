package com.tarena.mnmp.domain.provider.controller;

import com.tarena.mnmp.api.TargetDTO;
import com.tarena.mnmp.enums.NoticeType;
import com.tarena.mnmp.enums.SendType;
import java.util.List;
import lombok.Data;

@Data
public class SendParam {


    public SendParam() {
        this.mock = 0;
    }

    private String triggerTime;
    private SendType sendType;
    private NoticeType noticeType;
    private String appCode;
    private String templateCode;
    private String templateContent;
    private List<TargetDTO> targets;
    private String signCode;
    private String signName;
    private String providerCode;
    private Integer mock;
    private Long taskId;
}
