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

import com.tarena.mnmp.commons.utils.Asserts;
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

    public void save(AppSaveParam appSaveParam) throws BusinessException {
        AppDO save = new AppDO();
        BeanUtils.copyProperties(appSaveParam, save);
        checkOnlyOne(appSaveParam, save);

        if (null == appSaveParam.getId()) {
            appDao.save(save);
        } else {
            save.cleanSameData();
            appDao.modify(save);
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
        List<AppDO> sources = appDao.queryByParam(param);
        // 查询集合是为了给前端修改时回显，但是数据量一旦上来无法回显用户已经选择的应用
        if (null != param.getAppendId()) {
            boolean append = sources.stream().noneMatch(source -> source.getId().equals(param.getAppendId()));
            AppDO app;
            if (append && null != (app = queryAppDetail(param.getAppendId()))) {
                sources.add(app);
            }
        }
        return sources;
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

        Asserts.isTrue(null == app, new BusinessException("100", "应用不存在"));
        String name = app.getName();

        Asserts.isTrue(!Objects.equals(AuditStatus.PASS.getStatus(), app.getAuditStatus()),
            new BusinessException("100","[" + name + "]应用审核未通过"));

        Asserts.isTrue(!Objects.equals(Enabled.YES.getVal(), app.getEnabled()),
            new BusinessException("100", "[" + name + "]应用未启用"));
        return app;
    }

    private void checkOnlyOne(AppSaveParam appSaveParam, AppDO save) throws BusinessException {
        AppQueryParam param = new AppQueryParam();
        param.setName(save.getName());
        param.setExcludeId(appSaveParam.getId());
        Long count  = appDao.queryCount(param);
        if (count > 0) {
            throw new BusinessException("100", "[" + save.getName() + "]应用名称重复");
        }

        param.setName(null);
        param.setCode(save.getCode());
        count = appDao.queryCount(param);
        if (count > 0) {
            throw new BusinessException("100", "[" + save.getName() + "]应用编码重复");
        }
    }
}
