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

package com.tarena.mnmp.admin.codegen.api.app;

import com.tarena.mnmp.app.App;
import com.tarena.mnmp.protocol.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Api(
    tags = "C端应用APP"
)
@RequestMapping("/app")
public interface ApplicationApi {
    @ApiOperation(
        value = "新增应用",
        nickname = "addApp",
        notes = ""
    )
    @PostMapping(
        value = {"/add"},
        consumes = {"application/json"}
    )
    Result addApp(@ApiParam(value = "新增应用", required = true) @Valid @RequestBody App app);

    @ApiOperation(
        value = "编辑应用",
        nickname = "editApp",
        notes = "",
        tags = {"App"}
    )
    @PostMapping(
        value = {"/app/edit"},
        consumes = {"application/json"}
    )
    Result editApp(@ApiParam(value = "编辑应用", required = true) @Valid @RequestBody App App);
}
