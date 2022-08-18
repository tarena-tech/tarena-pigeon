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
package com.tarena.mnmp.domain.param;

import com.tarena.mnmp.commons.pager.PagerResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("服务商查询模型")
@Data
public class ProviderQueryParam extends PagerResult {

    public ProviderQueryParam() {
        super();
        this.orderBy = true;
    }

    @ApiModelProperty("供应商名称")
    private String name;

    @ApiModelProperty("供应商编码")
    private String code;


    @ApiModelProperty("是否启用 0：未启用， 1：启用")
    private Integer enable;

    @ApiModelProperty("审核状态 -1未通过 0审核中 1通过")
    private Integer auditStatus;

    @ApiModelProperty("true：倒排， false：不排序")
    private Boolean orderBy;

    @ApiModelProperty("列表中必须包含某条数据")
    private Long appendId;

    @ApiModelProperty("列表中指定排除某个id")
    private Long excludeId;

}
