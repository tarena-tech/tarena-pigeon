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

package com.tarena.mnmp.admin.controller.white;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class PhoneWhiteView {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @JsonIgnore
    private String appCode;

    @ApiModelProperty(value = "app名称")
    private String appName;

    @ApiModelProperty(value = "生效开始时间")
    private Date startTime;

    @ApiModelProperty(value = "生效结束时间")
    private Date endTime;

    @ApiModelProperty(value = "1:启用, 0:禁用")
    private Integer isEnabled;


    @Ignore
    public static List<PhoneWhiteView> convert(List<PhoneWhiteListDO> sources) {
        List<PhoneWhiteView> list = new ArrayList<>();
        for (PhoneWhiteListDO phones : sources) {
            PhoneWhiteView view = new PhoneWhiteView();
            BeanUtils.copyProperties(phones, view);
            list.add(view);
        }
        return list;
    }
}
