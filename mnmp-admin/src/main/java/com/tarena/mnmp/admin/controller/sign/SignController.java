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

package com.tarena.mnmp.admin.controller.sign;

import com.tarena.mnmp.admin.codegen.api.sign.SignApi;
import com.tarena.mnmp.admin.param.AuditParam;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SignDO;
import com.tarena.mnmp.domain.sign.SignQuery;
import com.tarena.mnmp.domain.sign.SignSaveParam;
import com.tarena.mnmp.domain.sign.SignService;
import com.tarena.mnmp.protocol.BusinessException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController implements SignApi {
    @Autowired
    private SignService signService;

    @Override public void save(SignSaveParam signSaveParam) {
        signService.save(signSaveParam);
    }

    @Override public void editSign(SignSaveParam signSaveParam) {
        signService.editSign(signSaveParam);
    }


    @Override public void changeEnableStatus(Long id) throws BusinessException {
        SignDO aDo = signService.querySignDetail(id);
        if (null == aDo) {
            throw new BusinessException("100", "签名不存在");
        }

        SignDO up = new SignDO();
        up.setId(id);
        up.setEnabled(aDo.getEnabled() == 1 ? 0 : 1);
        signService.modify(up);
    }

    @Override public SignView querySignDetail(Long id) {
        SignDO signDO = signService.querySignDetail(id);
        SignView signView = new SignView();
        BeanUtils.copyProperties(signDO, signView);
        return signView;
    }

    @Override public PagerResult<SignView> queryPage(SignQuery signQuery) {
        List<SignDO> signDOs = signService.querySignList(signQuery);
        Long count = signService.queryCount(signQuery);
        List<SignView> signViews = new ArrayList<>();
        for (SignDO signDO : signDOs) {
            SignView signView = new SignView();
            BeanUtils.copyProperties(signDO, signView);
            signViews.add(signView);
        }

        PagerResult<SignView> rest = new PagerResult<>(signQuery.getPageSize(), signQuery.getCurrentPageIndex());
        rest.setList(signViews);
        rest.setRecordCount(count);
        return rest;
    }

    @Override public List<SignView> queryList(SignQuery signQuery) {
        List<SignDO> signDOs = signService.querySignList(signQuery);
        List<SignView> signViews = new ArrayList<>();
        for (SignDO signDO : signDOs) {
            SignView signView = new SignView();
            BeanUtils.copyProperties(signDO, signView);
            signViews.add(signView);
        }
        return signViews;
    }

    @Override public void auditSign(AuditParam param) {
        signService.auditSign(param.getId(), param.getAuditStatus(), param.getAuditResult());
    }
}
