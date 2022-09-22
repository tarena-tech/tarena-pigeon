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

package com.tarena.mnmp.admin.controller.white;

import com.alibaba.excel.EasyExcel;
import com.tarena.mnmp.admin.codegen.api.white.PhoneWhiteApi;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.admin.utils.ExcelUtils;
import com.tarena.mnmp.domain.AppDO;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import com.tarena.mnmp.domain.app.AppService;
import com.tarena.mnmp.domain.param.AppQueryParam;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.domain.white.PhoneWhiteExcelData;
import com.tarena.mnmp.domain.white.PhoneWhiteService;
import com.tarena.mnmp.enums.Role;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.protocol.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class PhoneWhiteController implements PhoneWhiteApi {

    @Resource
    private PhoneWhiteService phoneWhiteService;

    @Resource
    private AppService appService;

    @Value("${excel.path.white-list}")
    private String whiteExcelPath;

    @Override public void getExcel(String path, HttpServletResponse response) throws BusinessException, IOException {
        ExcelUtils.getExcel(StringUtils.isEmpty(path) ? null : whiteExcelPath + path, "phone_white.xlsx", response);
    }

    @Override public Result<PagerResult<PhoneWhiteView>> queryPage(PhoneWhiteQueryParam param, LoginToken token) {
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
        List<PhoneWhiteListDO> sources = phoneWhiteService.queryList(param, token);
        Long count = phoneWhiteService.queryCount(param, token);
        PagerResult<PhoneWhiteView> result = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());

        result.setList(PhoneWhiteView.convert(sources));
        result.setRecordCount(count);
        return new Result<>(result);
    }

    /**
     * 上传白名单
     * @param file excel文件
     * @param token 登录用户
     * @param response
     * @return
     * @throws IOException
     * @throws BusinessException
     */
    @Override public Result<String> saveByFile(MultipartFile file, LoginToken token, HttpServletResponse response) throws IOException, BusinessException {
        List<PhoneWhiteExcelData> data = phoneWhiteService.saveByFile(file, token);
        //返回不可以使用的电话号码
        if (!CollectionUtils.isEmpty(data)) {
            String mid = ExcelUtils.midPath();
            String dir = ExcelUtils.mkdir(whiteExcelPath + mid);
            String name = ExcelUtils.fileName("xlsx");
            EasyExcel.write(dir + name, PhoneWhiteExcelData.class).sheet("非法数据").doWrite(data);
            return new Result<>(mid + name);
        }
        return new Result<>(null);
    }

    @Override public Result<Void> dels(Long id, String appCode, LoginToken token) throws BusinessException {
        phoneWhiteService.del(id, appCode, token);
        return Result.success();
    }
}
