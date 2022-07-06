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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tarena.mnmp.admin.codegen.api.app.AppApi;
import com.tarena.mnmp.admin.view.app.AppView;
import com.tarena.mnmp.app.AppAddParam;
import com.tarena.mnmp.app.AppDTO;
import com.tarena.mnmp.app.AppEditParam;
import com.tarena.mnmp.app.AppService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController implements AppApi {
    @Autowired
    private AppService appService;

    @Override public void addApp(AppAddParam appAddParam) {
        appService.addApp(appAddParam);
    }

    @Override public void editApp(AppEditParam appEditParam) {
        appService.editApp(appEditParam);
    }

    @Override public void closeApp(Long id) {
        appService.closeApp(id);
    }

    @Override public void openApp(Long id) {
        appService.openApp(id);
    }

    @Override public AppView queryAppDetail(Long id) {
        AppDTO appDTO = appService.queryAppDetail(id);
        AppView appVO = new AppView();
        BeanUtils.copyProperties(appDTO, appVO);

        return appVO;
    }

    @Override public List<AppView> queryList() {
        List<AppDTO> appDTOs = appService.queryList();
        List<AppView> appVOs = new ArrayList<>();
        for (AppDTO appDTO : appDTOs) {
            AppView appVO = new AppView();
            BeanUtils.copyProperties(appDTO, appVO);
            appVOs.add(appVO);
        }
        return appVOs;
    }
}
