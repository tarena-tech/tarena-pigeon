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
import com.tarena.mnmp.commons.utils.Asserts;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.app.AppQueryParam;
import com.tarena.mnmp.domain.app.AppSaveParam;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.sign.SignService;
import com.tarena.mnmp.domain.task.TaskQuery;
import com.tarena.mnmp.domain.task.TaskService;
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
    private TaskService taskService;

    @Resource
    private SignService signService;

    @Override public void save(AppSaveParam appAddParam) throws BusinessException {
        appService.save(appAddParam);
    }


    @Override public void changeEnableStatus(Long id) throws BusinessException {
        AppDO app = appService.queryAppDetail(id);
        if (app == null) {
            throw new BusinessException("100", "应用不存在");
        }

        AppDO up = new AppDO();
        up.setId(id);
        up.setEnabled(Enabled.reverse(app.getEnabled()).getVal());
        appService.updateById(up);

        // 应用被禁用 依赖于该应用的关系全部禁用
        if (Objects.equals(Enabled.NO.getVal(), up.getEnabled())) {
            templateService.changeEnableByAppId(id, up.getEnabled());
            signService.changeEnableByAppId(id, up.getEnabled());

            TaskQuery query = new TaskQuery();
            query.setAppId(id);
            taskService.endTaskStatusByTargetId(query);
        }

    }

    @Override public AppView queryAppDetail(Long id) {
        AppDO app = appService.queryAppDetail(id);
        if (null == app) {
            return null;
        }
        AppView appVO = new AppView();
        BeanUtils.copyProperties(app, appVO);
        return appVO;
    }

    @Override public PagerResult<AppView> queryPage(AppQueryParam param) {
        List<AppDO> sources = appService.queryList(param);
        Long count = appService.queryCount(param);
        PagerResult<AppView> pagerResult = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());
        pagerResult.setList(AppView.convert(sources));
        pagerResult.setRecordCount(count);
        return pagerResult;
    }

    @GetMapping("query/list") @Override public List<AppView> queryList(AppQueryParam param) {
        param.setOrderBy(false);
        List<AppDO> sources = appService.queryList(param);
        return AppView.convert(sources);
    }

    @Override public void auditApp(AuditParam param) {
        appService.auditApp(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }
}
