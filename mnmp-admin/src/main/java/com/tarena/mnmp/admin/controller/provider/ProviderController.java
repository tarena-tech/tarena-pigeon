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

package com.tarena.mnmp.admin.controller.provider;

import com.tarena.mnmp.admin.codegen.api.provider.ProviderApi;
import com.tarena.mnmp.provider.Provider;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController implements ProviderApi {
    @Override
    public void addProvider(Provider provider) {

    }

    @Override
    public void closeProvider(Long id) {

    }

    @Override
    public void editProvider(Provider provider) {

    }

    @Override
    public void openProvider(Long id) {

    }

    @Override
    public List<Provider> queryList() {
        return null;
    }

    @Override
    public Provider queryProviderDetail(Long id) {
        return null;
    }
}
