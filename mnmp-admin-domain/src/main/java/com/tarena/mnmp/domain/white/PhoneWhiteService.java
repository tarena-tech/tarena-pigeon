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

package com.tarena.mnmp.domain.white;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.StringUtils;
import com.tarena.mnmp.commons.utils.RegexUtils;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.param.AppQueryParam;
import com.tarena.mnmp.domain.param.PhoneWhiteParam;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.domain.task.TargetExcelData;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.protocol.LoginToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhoneWhiteService {

    @Resource
    private PhoneWhiteListDao phoneWhiteListDao;

    @Resource
    private AppService appService;

    public List<PhoneWhiteListDO> queryList(PhoneWhiteQueryParam param, LoginToken token) {
        return phoneWhiteListDao.queryList(param);
    }


    public PhoneWhiteListDO detail (Long id, LoginToken token) {
        return phoneWhiteListDao.selectByPrimaryKey(id);
    }

    public List<PhoneWhiteExcelData> saveByFile(MultipartFile file, LoginToken token) throws IOException {

        List<PhoneWhiteExcelData> fails = new ArrayList<>();
        Map<String, AppDO> appCache = new HashMap<>();
        AppDO app = new AppDO();
        app.setId(-1L);

        AppQueryParam queryParam = new AppQueryParam();
        queryParam.setEnable(Enabled.YES.getVal());
        queryParam.setDesc(false);
        queryParam.setCurrentPageIndex(1);
        queryParam.setPageSize(1);
        Date now = new Date();

        EasyExcel.read(file.getInputStream(), PhoneWhiteExcelData.class, new ReadListener<PhoneWhiteExcelData>() {

            final Set<String> phone = new HashSet<>();

            public static final int BATCH_COUNT = 100;

            private List<PhoneWhiteListDO> targets =  new ArrayList<>(BATCH_COUNT);

            @Override public void invoke(PhoneWhiteExcelData data, AnalysisContext context) {
                if (phone.contains(data.getPhone())) {
                    data.setRemark("同一批次多个手机号,只录入一条");
                    return;
                }

                if (!RegexUtils.checkPhone(data.getPhone())) {
                    data.setRemark("手机号格式不正确");
                    fails.add(data);
                    return;
                }

                if (StringUtils.isEmpty(data.getAppCode())) {
                    data.setRemark("app编码不能为空");
                    fails.add(data);
                    return;
                }

                if (null == data.getSendLimit() || data.getSendLimit() < 0) {
                    data.setRemark("发送流控不能小于0");
                    fails.add(data);
                    return;
                }

                if (null == data.getStartTime() || null == data.getEndTime()) {
                    data.setRemark("生效起止时间不能为空");
                    fails.add(data);
                    return;
                }

                if (data.getStartTime().after(data.getEndTime())) {
                    data.setRemark("生效时间不能早于结束时间");
                    fails.add(data);
                    return;
                }

                PhoneWhiteListDO pw = new PhoneWhiteListDO();
                BeanUtils.copyProperties(data, pw);

                AppDO curApp = appCache.computeIfAbsent(data.getAppCode(), appCode -> {
                    queryParam.setCode(appCode);
                    List<AppDO> dos = appService.queryList(queryParam);
                    if (CollectionUtils.isEmpty(dos)) {
                        return app;
                    }
                    return dos.get(0);
                });

                if (curApp.getId() < 0) {
                    data.setRemark("根据app编码查询不到有效的app");
                    fails.add(data);
                    return;
                }

                pw.setAppName(curApp.getName());
                pw.setIsEnabled(Enabled.YES.getVal());
                pw.setCreateTime(now);
                pw.setUpdateTime(now);
                targets.add(pw);
                if (targets.size() >= BATCH_COUNT) {
                    save();
                }
            }

            @Override public void doAfterAllAnalysed(AnalysisContext context) {
                save();
            }

            private void save() {
                if (!CollectionUtils.isEmpty(targets)) {
                    phoneWhiteListDao.batchInsert(targets);
                    targets = new ArrayList<>(BATCH_COUNT);
                }
            }
        }).sheet().doRead();
        return fails;
    }

    public void update(PhoneWhiteParam param, LoginToken token) {



    }

    public Long queryCount(PhoneWhiteQueryParam param, LoginToken token) {
        return phoneWhiteListDao.count(param);
    }
}
