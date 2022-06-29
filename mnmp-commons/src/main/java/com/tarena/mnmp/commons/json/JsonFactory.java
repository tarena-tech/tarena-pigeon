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

package com.tarena.mnmp.commons.json;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JsonFactory {
    private volatile static Json json;

    public static Json getProvider() {
        if (json != null) {
            return json;
        }
        synchronized (JsonFactory.class) {
            if (json != null) {
                return json;
            }
            ServiceLoader<Json> loader = ServiceLoader.load(Json.class);
            Iterator<Json> it = loader.iterator();
            if (it.hasNext()) {
                json = it.next();
                return json;
            }
            throw new RuntimeException(
                "Json Provider could not be instantiated: ");
        }
    }
}
