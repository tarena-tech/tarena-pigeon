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

package com.tarena.mnmp.domain.provider;

import com.tarena.mnmp.domain.ProviderDO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    @Autowired
    private ProviderDao providerDao;

    public void addProvider(ProviderSaveParam providerSaveParam) {
        ProviderDO providerDO = new ProviderDO();
        BeanUtils.copyProperties(providerSaveParam, providerDO);
        providerDao.save(providerDO);
    }

    public void closeProvider(Long id) {
        providerDao.disable(id);
    }

    public void editProvider(ProviderSaveParam providerSaveParam) {
        ProviderDO providerDO = new ProviderDO();
        BeanUtils.copyProperties(providerSaveParam, providerDO);
        providerDao.modify(providerDO);
    }

    public void openProvider(Long id) {
        providerDao.enable(id);
    }

    public List<ProviderDO> queryList(ProviderQueryParam param) {
        return providerDao.queryByParam(param);
    }

    public ProviderDO queryProviderDetail(Long id) {
        return providerDao.findById(id);
    }

    public void auditProvider(Long id, Integer auditStatus) {
        ProviderDO providerDO = new ProviderDO();
        providerDO.setId(id);
        providerDO.setAuditStatus(auditStatus);
        providerDao.modify(providerDO);
    }

    public Long queryCount(ProviderQueryParam param) {
        Long count = providerDao.queryCount(param);
        return Optional.ofNullable(count).orElse(0L);
    }

    public void update(ProviderDO up) {
        providerDao.modify(up);
    }
}
