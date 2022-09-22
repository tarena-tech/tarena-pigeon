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
import com.alibaba.fastjson.JSON;
import com.tarena.mnmp.commons.utils.RegexUtils;
import com.tarena.mnmp.constant.Constant;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.param.AppQueryParam;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.enums.Enabled;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class PhoneWhiteService {

    @Resource
    private PhoneWhiteListDao phoneWhiteListDao;

    @Resource
    private AppService appService;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public List<PhoneWhiteListDO> queryList(PhoneWhiteQueryParam param, LoginToken token) {
        return phoneWhiteListDao.queryList(param);
    }

    public PhoneWhiteListDO detail(Long id, LoginToken token) {
        return phoneWhiteListDao.selectByPrimaryKey(id);
    }

    public List<PhoneWhiteExcelData> saveByFile(MultipartFile file,
        LoginToken token) throws IOException, BusinessException {
        //准备返回的非法数据,当前为空
        List<PhoneWhiteExcelData> fails = new ArrayList<>();
        //查询是否存在应用参数
        Date now = new Date();

        /**
         * 读取excel中数据 存放到PhoneWhiteExceData对象里
         */
        EasyExcel.read(file.getInputStream(), PhoneWhiteExcelData.class, new ReadListener<PhoneWhiteExcelData>() {
            final List<AppDO> apps = new ArrayList<>();
            final Set<String> phone = new HashSet<>();
            public static final int BATCH_COUNT = 100;
            private List<PhoneWhiteListDO> targets = new ArrayList<>(BATCH_COUNT);

            @Override public void invoke(PhoneWhiteExcelData data, AnalysisContext context) {
                log.info(JSON.toJSONString(data));
                //查看是否有appCode,以第一个非空appCode作为参照
                if (CollectionUtils.isEmpty(apps) && ObjectUtils.isNotEmpty(data.getAppCode())) {
                    AppQueryParam queryParam = new AppQueryParam();
                    queryParam.setEnable(Enabled.YES.getVal());
                    queryParam.setDesc(false);
                    queryParam.setCurrentPageIndex(1);
                    queryParam.setPageSize(1);
                    queryParam.setCode(data.getAppCode());
                    /*queryParam.setCreateUserId(token.getId());*/
                    List<AppDO> dos = appService.queryList(queryParam);
                    apps.add(dos.get(0));
                    if (CollectionUtils.isEmpty(dos)) {
                        data.setRemark("没有可用的app编码");
                        return;
                    }
                }
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
                phone.setAppCode(apps.get(0).getCode());
                phone.setAppName(apps.get(0).getName());
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
                    redisTemplate.opsForHash().putAll(Constant.WHITE_PHONE + apps.get(0).getCode(), m);
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
