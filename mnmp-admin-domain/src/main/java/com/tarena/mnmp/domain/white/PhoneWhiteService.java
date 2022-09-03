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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.tarena.mnmp.commons.utils.RegexUtils;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.param.AppQueryParam;
import com.tarena.mnmp.domain.param.PhoneWhiteParam;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.enums.Enabled;
import com.tarena.mnmp.enums.Role;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhoneWhiteService {

    @Resource
    private PhoneWhiteListDao phoneWhiteListDao;

    @Resource
    private AppService appService;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;
    public List<PhoneWhiteListDO> queryList(PhoneWhiteQueryParam param, LoginToken token) {
        if (!Role.manager(token.getRole())) {
            AppQueryParam query = new AppQueryParam();
            query.setCreateUserId(token.getId());
            List<AppDO> dos = appService.queryList(query);
            List<String> appcdoes = new ArrayList<>();
            for (AppDO app : dos) {
                appcdoes.add(app.getCode());
                param.setAppCodes(appcdoes);
            }
        }
        return phoneWhiteListDao.queryList(param);
    }

    public PhoneWhiteListDO detail(Long id, LoginToken token) {
        return phoneWhiteListDao.selectByPrimaryKey(id);
    }

    public List<PhoneWhiteExcelData> saveByFile(MultipartFile file, LoginToken token) throws IOException, BusinessException {
        List<PhoneWhiteExcelData> fails = new ArrayList<>();

        AppQueryParam queryParam = new AppQueryParam();
        queryParam.setEnable(Enabled.YES.getVal());
        queryParam.setDesc(false);
        queryParam.setCurrentPageIndex(1);
        queryParam.setPageSize(1);
        queryParam.setCreateUserId(token.getId());
        List<AppDO> dos = appService.queryList(queryParam);
        if (CollectionUtils.isEmpty(dos)) {
            throw new BusinessException("100", "请先创建应用");
        }

        AppDO app = dos.get(0);
        Date now = new Date();

        EasyExcel.read(file.getInputStream(), PhoneWhiteExcelData.class, new ReadListener<PhoneWhiteExcelData>() {

            final Set<String> phone = new HashSet<>();

            public static final int BATCH_COUNT = 100;

            private List<PhoneWhiteListDO> targets = new ArrayList<>(BATCH_COUNT);

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
                PhoneWhiteListDO phone = new PhoneWhiteListDO();
                phone.setPhone(data.getPhone());
                phone.setAppCode(app.getCode());
                phone.setAppName(app.getName());
                phone.setCreateTime(now);
                phone.setCreateUserId(token.getId());

                targets.add(phone);
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
                    Map<String, String> m = new HashMap<>();
                    for (PhoneWhiteListDO target : targets) {
                        m.put(target.getPhone(), target.getId().toString());
                    }

                    redisTemplate.opsForHash().putAll(Constant.WHITE_PHONE + app.getCode() , m);
                    targets = new ArrayList<>(BATCH_COUNT);
                }
            }
        }).sheet().doRead();
        return fails;
    }

    public void del(Long id, String appCode, LoginToken token) throws BusinessException {
        if (null != id) {
            PhoneWhiteListDO phoneWhite = phoneWhiteListDao.selectByPrimaryKey(id);
            if (null == phoneWhite) {
                throw new BusinessException("100", "数据不存在");
            }
            redisTemplate.opsForHash().delete(Constant.WHITE_PHONE + phoneWhite.getAppCode(), phoneWhite.getPhone());
            phoneWhiteListDao.deleteByPrimaryKey(id);
            return;
        }

        if (StringUtils.isNotBlank(appCode)) {
            phoneWhiteListDao.deleteByAppCode(appCode);
            redisTemplate.delete(Constant.WHITE_PHONE + appCode);
        }


    }

    public Long queryCount(PhoneWhiteQueryParam param, LoginToken token) {
        return phoneWhiteListDao.count(param);
    }
}
