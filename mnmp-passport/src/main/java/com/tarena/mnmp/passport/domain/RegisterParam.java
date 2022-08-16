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

package com.tarena.mnmp.passport.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data @ApiModel(value = "注册参数") public class RegisterParam {
    @ApiModelProperty(value = "用户名", name = "username", example = "zhangsan") @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", name = "password", example = "123456") @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "角色", name = "role", example = "admin") @NotBlank private String role;
}
