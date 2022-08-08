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

package com.tarena.mnmp.domain.app;

import com.tarena.mnmp.domain.AppDO;
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
public class AppService {
    @Autowired
    private AppDao appDao;

    public void save(AppSaveParam appSaveParam) {
        AppDO appDO = new AppDO();
        BeanUtils.copyProperties(appSaveParam, appDO);
        if (null == appSaveParam.getId()) {
            appDao.save(appDO);
        } else {
            appDO.cleanSameData();
            appDao.modify(appDO);
        }
    }

    public void editApp(AppSaveParam appSaveParam) {
        AppDO appDO = new AppDO();
        BeanUtils.copyProperties(appSaveParam, appDO);
        appDao.modify(appDO);
    }

    public void closeApp(Long id) {
        appDao.disable(id);
    }

    public void openApp(Long id) {
        appDao.enable(id);
    }

    public AppDO queryAppDetail(Long id) {
        return appDao.findById(id);
    }

    public List<AppDO> queryList(AppQueryParam param) {
        return appDao.queryByParam(param);
    }

    public void auditApp(Long id, Integer auditStatus, String auditResult) {
        AppDO appDO = new AppDO();
        appDO.setId(id);
        appDO.setAuditStatus(auditStatus);
        appDO.setAuditResult(auditResult);
        appDao.modify(appDO);
    }

    public Long queryCount(AppQueryParam param) {
        Long count = appDao.queryCount(param);
        return Optional.ofNullable(count).orElse(0L);

    }

    public void updateById(AppDO up) {
        appDao.modify(up);
    }

    public AppDO checkStatus(Long appId) throws BusinessException {
        AppDO app = queryAppDetail(appId);
        if (null == app) {
            throw new BusinessException("100", "应用不存在");
        }

        if (!Objects.equals(AuditStatus.PASS.getStatus(), app.getAuditStatus())) {
            throw new BusinessException("100", "应用审核未通过");
        }

        if (!Objects.equals(Enabled.YES.getVal(), app.getEnabled())) {
            throw new BusinessException("100", "应用未启用");
        }

        return app;
    }
}
