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
import com.tarena.mnmp.admin.codegen.api.sign.SignView;
import com.tarena.mnmp.domain.SignDO;
import com.tarena.mnmp.domain.sign.SignQuery;
import com.tarena.mnmp.domain.sign.SignSaveParam;
import com.tarena.mnmp.domain.sign.SignService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController implements SignApi {
    @Autowired
    private SignService signService;

    @Override public void addSign(SignSaveParam signSaveParam) {
        signService.addSign(signSaveParam);
    }

    @Override public void closeSign(Long id) {
        signService.closeSign(id);
    }

    @Override public void editSign(SignSaveParam signSaveParam) {
        signService.editSign(signSaveParam);
    }

    @Override public void openSign(Long id) {
        signService.openSign(id);
    }

    @Override public SignView querySignDetail(Long id) {
        SignDO signDO = signService.querySignDetail(id);
        SignView signView = new SignView();
        BeanUtils.copyProperties(signDO, signView);
        return signView;
    }

    @Override public List<SignView> querySignList(SignQuery signQuery) {
        List<SignDO> signDOs = signService.querySignList(signQuery);
        List<SignView> signViews = new ArrayList<>();
        for (SignDO signDO : signDOs) {
            SignView signView = new SignView();
            BeanUtils.copyProperties(signDO, signView);
            signViews.add(signView);
        }
        return signViews;
    }

    @Override public void auditSign(Long id, Integer auditStatus) {
        signService.auditSign(id, auditStatus);
    }
}
