/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tarena.mnmp.admin.controller.record;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.domain.SmsRecordTargetDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@ApiModel("任务发送记录数据模型")
public class SmsTargetRecordView {


    /**
     * 主键
     */
    private Long id;


    @ApiModelProperty("应用编码")
    private String appCode;


    @ApiModelProperty("任务ID")
    private Long taskId;


    @ApiModelProperty("目标")
    private String target;

    @ApiModelProperty("短信具体内容")
    private String content;


    @ApiModelProperty("消息发送状态 0发送给供应商失败 1发送给供应商成功 2发送给目标失败 3发送给目标成功")
    private Integer status;

    @ApiModelProperty("任务执行时间")
    private String triggerTime;

    @ApiModelProperty("发送时间")
    private Date pushTime;

    @ApiModelProperty("推送到达时间")
    private Date pushReceiveTime;

    @ApiModelProperty("发送回执码")
    private String bizId;

    @ApiModelProperty("发送结果/失败原因")
    private String sendResult;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @Ignore
    public static List<SmsTargetRecordView> convert(List<SmsRecordTargetDO> sources) {
        if (null == sources) {
            return new ArrayList<>();
        }

        List<SmsTargetRecordView> list = new ArrayList<>(sources.size());

        for (SmsRecordTargetDO source : sources) {
            SmsTargetRecordView strv = new SmsTargetRecordView();
            BeanUtils.copyProperties(source, strv);
            list.add(strv);
        }
        return list;
    }
}
