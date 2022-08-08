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

package com.tarena.mnmp.domain.sign;

import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.SignDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.enums.AuditStatus;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService {
    @Autowired
    private SignDao signDao;

    @Resource
    private AppService appService;

    public void save(SignSaveParam signSaveParam) throws BusinessException {
        SignDO signDO = new SignDO();
        BeanUtils.copyProperties(signSaveParam, signDO);
        check(signDO);
        if (null == signDO.getId()) {
            signDao.save(signDO);
        } else {
            signDO.setEnabled(null);
            signDO.setAuditStatus(null);
            signDO.setCreateTime(null);
            signDao.modify(signDO);
        }
    }

    public void closeSign(Long id) {
        signDao.disable(id);
    }

    public void editSign(SignSaveParam signSaveParam) {
        SignDO signDO = new SignDO();
        BeanUtils.copyProperties(signSaveParam, signDO);
        signDao.modify(signDO);
    }

    public void openSign(Long id) {
        signDao.enable(id);
    }

    public SignDO querySignDetail(Long id) {
        return signDao.findById(id);
    }

    public List<SignDO> querySignList(SignQuery signQuery) {
        return signDao.querySigns(signQuery);
    }

    public void auditSign(Long id, Integer status, String auditResult) {
        SignDO signDO = new SignDO();
        signDO.setId(id);
        signDO.setAuditStatus(status);
        signDO.setAuditResult(auditResult);
        signDao.modify(signDO);
    }

    public Long queryCount(SignQuery query) {
        return signDao.queryCount(query);
    }

    public void modify(SignDO up) {
        signDao.modify(up);
    }

    public void changeEnableByAppId(Long id, Integer enable) {
        signDao.changeEnableByAppId(id, enable);
    }

    private void check(SignDO sign) throws BusinessException {
        AppDO app = appService.queryAppDetail(sign.getAppId());
        if (null == app) {
            throw new BusinessException("100", "应用不存在");
        }

        if (!Objects.equals(Enabled.YES.getVal(), app.getEnabled())) {
            throw new BusinessException("100", "应用已被禁用");
        }

        if (!Objects.equals(AuditStatus.PASS.getStatus(), app.getAuditStatus())) {
            throw new BusinessException("100", "应用未审核通过");
        }

    }
}
