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

package com.tarena.mnmp.admin.controller.sign;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.domain.SignDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@ApiModel(value = "签名模型")
@Data
public class SignView {

    @ApiModelProperty("签名id")
    private Long id;

    @ApiModelProperty("签名编码")
    private String code;

    @ApiModelProperty("签名名称")
    private String name;

    @ApiModelProperty("绑定的应用id")
    private Long appId;

    @ApiModelProperty("绑定的应用编码")
    private String appCode;

    @ApiModelProperty("备注/简介")
    private String remarks;

    @ApiModelProperty("暂时无用")
    private String creator;

    @ApiModelProperty("是否启用 0否 1是")
    private Integer enabled;

    @ApiModelProperty("审核状态 -1未通过 0审核中 1通过")
    private Integer auditStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("审核意见")
    private String auditResult;

    @Ignore
    public static List<SignView> convert(List<SignDO> sources) {
        List<SignView> list = new ArrayList<>();
        for (SignDO sign : sources) {
            SignView signView = new SignView();
            BeanUtils.copyProperties(sign, signView);
            list.add(signView);
        }
        return list;
    }
}
