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

import com.tarena.mnmp.admin.codegen.api.app.AppApi;
import com.tarena.mnmp.admin.codegen.api.app.AppAuditParam;
import com.tarena.mnmp.admin.codegen.api.app.AppView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.app.AppQueryParam;
import com.tarena.mnmp.domain.app.AppSaveParam;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController implements AppApi {
    @Autowired
    private AppService appService;

    @Override public void addApp(AppSaveParam appAddParam) {
        appService.addApp(appAddParam);
    }

    @Override public void editApp(AppSaveParam appEditParam) {
        appService.editApp(appEditParam);
    }

    @Override public void closeApp(Long id) {
        appService.closeApp(id);
    }

    @Override public void openApp(Long id) {
        appService.openApp(id);
    }

    @Override public void changeEnableStatus(Long id) throws BusinessException {
        AppDO aDo = appService.queryAppDetail(id);
        if (aDo == null) {
            throw new BusinessException("100", "应用不存在");
        }

        AppDO up = new AppDO();
        up.setId(id);
        up.setEnabled(aDo.getEnabled() == 1 ? 0 : 1);
        appService.updateById(up);
    }

    @Override public AppView queryAppDetail(Long id) {
        AppDO appDO = appService.queryAppDetail(id);
        AppView appVO = new AppView();
        BeanUtils.copyProperties(appDO, appVO);
        return appVO;
    }

    @Override public PagerResult<AppView> queryList(AppQueryParam param) {
        List<AppDO> appDTOs = appService.queryList(param);
        Long count = appService.queryCount(param);
        List<AppView> appVOs = new ArrayList<>();
        for (AppDO appDO : appDTOs) {
            AppView appVO = new AppView();
            BeanUtils.copyProperties(appDO, appVO);
            appVOs.add(appVO);
        }
        PagerResult<AppView> pagerResult = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());
        pagerResult.setList(appVOs);
        pagerResult.setRecordCount(count);
        return pagerResult;
    }

    @Override public void auditApp(AppAuditParam param) {
        appService.auditApp(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }
}
