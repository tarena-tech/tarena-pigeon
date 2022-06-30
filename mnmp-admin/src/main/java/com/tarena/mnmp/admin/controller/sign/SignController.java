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
import com.tarena.mnmp.sign.Sign;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController implements SignApi {
    @Override public String addSign(Sign sign) {
        return null;
    }

    @Override public void closeSign(Long id) {

    }

    @Override public String editSign(Sign sign) {
        return null;
    }

    @Override public void openSign(Long id) {

    }

    @Override public Sign querySignDetail(Long id) {
        return null;
    }

    @Override public List<Sign> querySignList(String appCode, Integer status, String name) {
        return null;
    }
}
