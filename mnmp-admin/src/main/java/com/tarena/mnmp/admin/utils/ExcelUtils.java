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
package com.tarena.mnmp.admin.utils;

import com.tarena.mnmp.commons.utils.DateUtils;
import com.tarena.mnmp.protocol.BusinessException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ExcelUtils {

    final static Set<String> EXCEL_SUFFIXS = new HashSet<>();

    static {
        EXCEL_SUFFIXS.add("xls");
        EXCEL_SUFFIXS.add("xlsx");
    }


    public static boolean checkExcel(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }

        int index = name.indexOf(".");
        if (index == -1) {
            return false;
        }
        return EXCEL_SUFFIXS.contains(name.substring(index + 1));
    }

    public static String suffixName(String name) {
        return name.substring(name.indexOf(".") + 1);
    }

    public static String fileName(String suffix) throws BusinessException {
        return UUID.randomUUID() + "." + suffix;
    }

    public static String preFix(String path, boolean mkdir) throws BusinessException {
        String pre = path + DateUtils.dateStr(new Date(), "yyyy/MM/dd/");
        if (mkdir) {
            mkdir(pre);
        }
        return pre;
    }

    public static String midPath() {
        return DateUtils.dateStr(new Date(), "yyyy/MM/dd/");
    }







    public static String mkdir(String path) throws BusinessException {
        File f = new File(path);
        if (!f.exists() && !f.mkdirs()) {
            throw new BusinessException("203", "目录创建失败，请检查权限");
        }
        return path;
    }

    public static String saveLocal(String path, MultipartFile file) throws BusinessException, IOException {
        if (null == file) {
            throw new BusinessException("201", "上传文件不能为空");
        }
        if (!ExcelUtils.checkExcel(file.getOriginalFilename())) {
            throw new BusinessException("202", "上传文件不是excel格式");
        }
        String mid = midPath();
        String name = fileName(suffixName(file.getOriginalFilename()));
        String dir = path + mid;
        mkdir(dir);
        Streams.copy(file.getInputStream(), Files.newOutputStream(Paths.get(dir + name)), true);
        return mid + name;
    }

    public static void getExcel(String path, String name, HttpServletResponse response) throws BusinessException {
        if (StringUtils.isNotBlank(name)) {
            try {
                ClassPathResource classPathResource = new ClassPathResource(name);
                InputStream stream = classPathResource.getInputStream();
                export(response, stream, name);
                log.info("file name: {}", classPathResource.getFilename());
                return;
            } catch (IOException e) {
                log.error("读取resource文件异常", e);
                throw new BusinessException("201", "读取文件异常，请稍后再试");
            }
        }

        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            log.error("找不到文件");
            throw new BusinessException("202", "文件不存在！！！");
        }
        try {
            export(response, new BufferedInputStream(Files.newInputStream(file.toPath())), file.getName());
        } catch (IOException e) {
            log.error("读取流异常", e);
            throw new BusinessException("201", "读取文件异常，请稍后再试");
        }
    }

    public static void export(HttpServletResponse response, InputStream is, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-excelname", fileName);
        OutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        int i;
        while ((i = is.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
    }


}
