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
package com.tarena.mnmp.domain.provider;

import com.tarena.mnmp.commons.pager.PagerResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("服务商查询模型")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Boolean orderBy) {
        this.orderBy = orderBy;
    }

    public Long getAppendId() {
        return appendId;
    }

    public void setAppendId(Long appendId) {
        this.appendId = appendId;
    }
}
