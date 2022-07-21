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
import com.tarena.mnmp.admin.codegen.api.provider.ProviderView;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.provider.ProviderDO;
import com.tarena.mnmp.domain.provider.ProviderSaveParam;
import com.tarena.mnmp.domain.provider.ProviderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController implements ProviderApi {
    @Autowired
    private ProviderService providerService;

    @Override public void addProvider(ProviderSaveParam providerSaveParam) {
        providerService.addProvider(providerSaveParam);
    }

    @Override public void closeProvider(Long id) {
        providerService.closeProvider(id);
    }

    @Override public void editProvider(ProviderSaveParam providerSaveParam) {
        providerService.editProvider(providerSaveParam);
    }

    @Override public void openProvider(Long id) {
        providerService.openProvider(id);
    }

    @Override public PagerResult<ProviderView> queryList() {
        List<ProviderDO> providerDOs = providerService.queryList();
        List<ProviderView> providerViews = new ArrayList<>(providerDOs.size());
        for (ProviderDO providerDO : providerDOs) {
            ProviderView providerView = new ProviderView();
            BeanUtils.copyProperties(providerDO, providerView);
            providerViews.add(providerView);
        }
        PagerResult<ProviderView> pagerResult = new PagerResult<ProviderView>(1000, 1);
        pagerResult.setList(providerViews);
        pagerResult.setRecordCount((long)providerViews.size());
        return pagerResult;
    }

    @Override public ProviderView queryProviderDetail(Long id) {
        ProviderDO providerDO = providerService.queryProviderDetail(id);
        ProviderView providerView = new ProviderView();
        BeanUtils.copyProperties(providerDO, providerView);
        return providerView;
    }

    @Override public void auditProvider(Long id, Integer auditStatus) {
        providerService.auditProvider(id,auditStatus);
    }

}
