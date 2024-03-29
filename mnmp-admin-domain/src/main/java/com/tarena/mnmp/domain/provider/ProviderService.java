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
import com.tarena.mnmp.domain.param.ProviderQueryParam;
import com.tarena.mnmp.domain.param.ProviderSaveParam;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    @Autowired
    private ProviderDao providerDao;

    public void addProvider(ProviderSaveParam providerSaveParam) {
        ProviderDO provider = new ProviderDO();
        BeanUtils.copyProperties(providerSaveParam, provider);
        if (null == provider.getId()) {
            providerDao.save(provider);
        } else {
            provider.setAuditResult(null);
            provider.setEnabled(null);
            provider.setAuditStatus(null);
            provider.setCreateTime(null);
            providerDao.modify(provider);
        }
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
        List<ProviderDO> sources = providerDao.queryByParam(param);
        if (null != param.getAppendId()) {
            boolean append = sources.stream().noneMatch(source -> source.getId().equals(param.getAppendId()));
            ProviderDO provider;
            if (append && null != (provider = queryProviderDetail(param.getAppendId()))) {
                sources.add(provider);
            }
        }
        return sources;
    }

    public ProviderDO queryProviderDetail(Long id) {
        return providerDao.findById(id);
    }

    public void auditProvider(Long id, Integer auditStatus, String auditResult) {
        ProviderDO providerDO = new ProviderDO();
        providerDO.setId(id);
        providerDO.setAuditStatus(auditStatus);
        providerDO.setAuditResult(auditResult);
        providerDao.modify(providerDO);
    }

    public Long queryCount(ProviderQueryParam param) {
        Long count = providerDao.queryCount(param);
        return Optional.ofNullable(count).orElse(0L);
    }

    public void update(ProviderDO up) {
        providerDao.modify(up);
    }

    public void save(ProviderSaveParam saveParam) throws BusinessException {
        ProviderDO provider = new ProviderDO();
        BeanUtils.copyProperties(saveParam, provider);


        ProviderQueryParam param = new ProviderQueryParam();
        param.setExcludeId(saveParam.getId());
        param.setCode(saveParam.getCode());
        Long count = queryCount(param);
        if (count > 0) {
            throw new BusinessException("100", "[" + saveParam.getName() + "]编码已存在，请勿重复添加");
        }

        if (null == provider.getId()) {
            providerDao.save(provider);
        } else {
            provider.setEnabled(null);
            provider.setAuditStatus(null);
            provider.setCreateTime(null);
            providerDao.modify(provider);
        }
    }

    public ProviderDO checkStatus(Long providerId) throws BusinessException {
        ProviderDO provider = queryProviderDetail(providerId);
        if (null == provider) {
            throw new BusinessException("100", "请选择供应商");
        }
        String name = provider.getName();
        if (!Objects.equals(AuditStatus.PASS.getStatus(), provider.getAuditStatus())) {
            throw new BusinessException("100", "[" + name + "]供应商审核未通过");
        }

        if (!Objects.equals(Enabled.YES.getVal(), provider.getEnabled())) {
            throw new BusinessException("100", "[" + name + "]供应商未启用");
        }
        return provider;
    }
}
