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
package com.tarena.mnmp.domain.common;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel("审核模型")
public class AuditParam {

    @NotNull(message = "id不能为空")
    @Min(1)
    private Long id;

    @Min(value = -1, message = "非法参数")
    @Max(value = 1, message = "非法参数")
    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;

    @NotBlank(message = "审核意见不能为空")
    private String auditResult;

}
