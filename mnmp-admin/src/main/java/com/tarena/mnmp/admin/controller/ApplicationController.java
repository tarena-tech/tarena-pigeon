package com.tarena.mnmp.admin.controller;

import com.tarena.mnmp.admin.codegen.api.app.ApplicationApi;
import com.tarena.mnmp.app.dto.AppAddDTO;
import com.tarena.mnmp.app.dto.AppEditDTO;
import com.tarena.mnmp.commons.protocol.Result;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController implements ApplicationApi {
    @Override public Result addApp(AppAddDTO appAddDTO) {
        return Result.success();
    }

    @Override public Result editApp(AppEditDTO appEditDTO) {
        return Result.success();
    }
}
