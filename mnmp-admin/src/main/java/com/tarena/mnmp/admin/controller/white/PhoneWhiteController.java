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

import com.alibaba.excel.EasyExcelFactory;
import com.tarena.mnmp.admin.codegen.api.white.PhoneWhiteApi;
import com.tarena.mnmp.commons.pager.PagerResult;
import com.tarena.mnmp.domain.PhoneWhiteListDO;
import com.tarena.mnmp.domain.param.PhoneWhiteParam;
import com.tarena.mnmp.domain.param.PhoneWhiteQueryParam;
import com.tarena.mnmp.domain.white.PhoneWhiteExcelData;
import com.tarena.mnmp.domain.white.PhoneWhiteService;
import com.tarena.mnmp.protocol.BusinessException;
import com.tarena.mnmp.protocol.LoginToken;
import com.tarena.mnmp.protocol.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class PhoneWhiteController implements PhoneWhiteApi {

    @Resource
    private PhoneWhiteService phoneWhiteService;

    @Override public void getExcel(HttpServletResponse response) throws BusinessException, IOException {
        String phoneWhite = "phone_white.xlsx";
        ClassPathResource classPathResource = new ClassPathResource(phoneWhite);
        InputStream stream = classPathResource.getInputStream();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + phoneWhite);
        response.setHeader("Content-excelname", phoneWhite);
        OutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        int i;
        while ((i = stream.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        log.info("file name: {}", classPathResource.getFilename());
    }

    @Override public Result<PagerResult<PhoneWhiteView>> queryPage(PhoneWhiteQueryParam param, LoginToken token) {
        List<PhoneWhiteListDO> sources = phoneWhiteService.queryList(param, token);
        Long count = phoneWhiteService.queryCount(param, token);
        PagerResult<PhoneWhiteView> result = new PagerResult<>(param.getPageSize(), param.getCurrentPageIndex());

        result.setList(PhoneWhiteView.convert(sources));
        result.setRecordCount(count);
        return new Result<>(result);
    }

    @Override public Result<Void> saveByFile(MultipartFile file, LoginToken token, HttpServletResponse response) throws IOException {
        List<PhoneWhiteExcelData> data = phoneWhiteService.saveByFile(file, token);
        if (!CollectionUtils.isEmpty(data)) {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + "error_data.xlsx");
            response.setHeader("Content-excelname", "error_data.xlsx");
            EasyExcelFactory.write(response.getOutputStream(), PhoneWhiteExcelData.class).sheet("失败数据").doWrite(data);
        }
        return Result.success();
    }

    @Override public Result<Void> update(PhoneWhiteParam param, LoginToken token) {
        phoneWhiteService.update(param, token);
        return Result.success();
    }
}
