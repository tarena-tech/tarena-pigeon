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
package com.tarena.mnmp.admin.controller.record;

import com.tarena.mnmp.admin.codegen.api.record.SmsTargetRecordApi;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.SmsRecordTargetDO;
import com.tarena.mnmp.domain.record.SmsTargetRecordParam;
import com.tarena.mnmp.domain.record.SmsTargetRecordService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsTargetRecordController implements SmsTargetRecordApi {

    @Resource
    private  SmsTargetRecordService smsTargetRecordService;

    @Override public PagerResult<SmsTargetRecordView> queryPage(SmsTargetRecordParam param) {
        List<SmsRecordTargetDO> sources = smsTargetRecordService.queryList(param);
        Long count = smsTargetRecordService.count(param);

        PagerResult<SmsTargetRecordView> result = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());
        result.setList(SmsTargetRecordView.convert(sources));
        result.setRecordCount(count);
        return result;
    }
}
