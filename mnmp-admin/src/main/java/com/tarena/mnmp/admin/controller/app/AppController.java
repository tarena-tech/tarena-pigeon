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
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.app.AppQueryParam;
import com.tarena.mnmp.domain.app.AppSaveParam;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.sign.SignService;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController implements AppApi {
    @Autowired
    private AppService appService;

    @Resource
    private TemplateService templateService;

    @Resource
    private SignService signService;

    @Override public void save(AppSaveParam appAddParam) {
        appService.save(appAddParam);
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

        // 应用被禁用 依赖于该应用的关系全部禁用
        if (Objects.equals(Enabled.NO.getVal(), up.getEnabled())) {
            templateService.changeEnableByAppId(id, up.getEnabled());
            signService.changeEnableByAppId(id, up.getEnabled());
        }

    }

    @Override public AppView queryAppDetail(Long id) {
        AppDO appDO = appService.queryAppDetail(id);
        AppView appVO = new AppView();
        BeanUtils.copyProperties(appDO, appVO);
        return appVO;
    }

    @Override public PagerResult<AppView> queryPage(AppQueryParam param) {
        List<AppDO> appDTOs = appService.queryList(param);
        Long count = appService.queryCount(param);
        PagerResult<AppView> pagerResult = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());
        pagerResult.setList(AppView.convert(appDTOs));
        pagerResult.setRecordCount(count);
        return pagerResult;
    }

    @GetMapping("query/list") @Override public List<AppView> queryList(AppQueryParam param) {
        param.setOrderBy(false);
        List<AppDO> dos = appService.queryList(param);
        return AppView.convert(dos);
    }

    @Override public void auditApp(AuditParam param) {
        appService.auditApp(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }
}
