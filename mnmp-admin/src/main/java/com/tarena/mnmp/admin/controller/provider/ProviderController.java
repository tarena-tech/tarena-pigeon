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

package com.tarena.mnmp.admin.controller.provider;

import com.tarena.mnmp.admin.codegen.api.provider.ProviderApi;
import com.tarena.mnmp.domain.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.ProviderDO;
import com.tarena.mnmp.domain.param.ProviderQueryParam;
import com.tarena.mnmp.domain.param.ProviderSaveParam;
import com.tarena.mnmp.domain.provider.ProviderService;
import com.tarena.mnmp.domain.template.TemplateService;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController implements ProviderApi {
    @Autowired
    private ProviderService providerService;

    @Resource
    private TemplateService templateService;


    @Override public void save(ProviderSaveParam param, LoginToken token) throws BusinessException {
        providerService.save(param);
    }

    @Override public void changeEnableStatus(Long id) throws BusinessException {
        ProviderDO provider = providerService.queryProviderDetail(id);
        if (null == provider) {
            throw new BusinessException("100", "服务商不存在");
        }

        ProviderDO up = new ProviderDO();
        up.setId(id);
        up.setEnabled(Enabled.reverse(provider.getEnabled()).getVal());
        providerService.update(up);
        // 服务供应商被禁用 跟他有关系的全部禁用
        if (Objects.equals(Enabled.NO.getVal(), up.getEnabled())) {
            templateService.changeEnableByProviderId(id, up.getEnabled());
        }
    }

    @Override public PagerResult<ProviderView> queryPage(ProviderQueryParam param, LoginToken token) {
        List<ProviderDO> sources = providerService.queryList(param);
        Long count = providerService.queryCount(param);
        PagerResult<ProviderView> pagerResult = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());
        boolean show = null != token && "root".equals(token.getUsername());
        pagerResult.setList(ProviderView.convert(sources, show));
        pagerResult.setRecordCount(count);
        return pagerResult;
    }

    @Override public ProviderView queryProviderDetail(Long id) {
        ProviderDO providerDO = providerService.queryProviderDetail(id);
        ProviderView providerView = new ProviderView();
        BeanUtils.copyProperties(providerDO, providerView);
        providerView.setClientConfig(null);
        return providerView;
    }

    @Override public void auditProvider(AuditParam param) {
        providerService.auditProvider(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }

    @Override public List<ProviderView> queryList(ProviderQueryParam param) {
        param.setOrderBy(false);
        List<ProviderDO> sources = providerService.queryList(param);
        return ProviderView.convert(sources, false);
    }
}
