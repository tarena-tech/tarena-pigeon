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

package com.tarena.mnmp.admin.controller.app;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.domain.AppDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@ApiModel(value = "应用控制层出参")
@Data
public class AppView {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "应用编码")
    private String code;

    @ApiModelProperty(value = "应用名称")
    private String name;

    @ApiModelProperty(value = "应用负责人")
    private String leader;

    @ApiModelProperty(value = "应用组员")
    private String teamMembers;

    @ApiModelProperty(value = "应用简介")
    private String remarks;

    @ApiModelProperty(value = "应用审核状态")
    private Integer auditStatus;

    @ApiModelProperty(value = "应用开启停用")
    private Integer enabled;

    @ApiModelProperty(value = "应用创建时间")
    private Date createTime;

    @ApiModelProperty(value = "应用更新时间")
    private Date updateTime;

    @Ignore
    public static List<AppView> convert(List<AppDO> source) {
        List<AppView> list = new ArrayList<>();
        for (AppDO appDO : source) {
            AppView appVO = new AppView();
            BeanUtils.copyProperties(appDO, appVO);
            list.add(appVO);
        }
        return list;
    }

}
