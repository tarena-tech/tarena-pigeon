package com.tarena.mnmp.admin.codegen.api.app;

import com.tarena.mnmp.app.dto.AppAddDTO;
import com.tarena.mnmp.app.dto.AppEditDTO;
import com.tarena.mnmp.commons.protocol.Result;
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
    Result addApp(@ApiParam(value = "新增应用",required = true)@Valid @RequestBody AppAddDTO appAddDTO);

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
    Result editApp(@ApiParam(value = "编辑应用",required = true) @Valid @RequestBody AppEditDTO appEditDTO);
}
