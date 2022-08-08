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

package com.tarena.mnmp.domain.sign;

import com.tarena.mnmp.domain.SignDO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SignDao {
    Integer save(SignDO aDo);

    void disable(Long id);

    Integer modify(SignDO aDo);

    void enable(Long id);

    SignDO findById(Long id);

    List<SignDO> querySigns(SignQuery query);

    Long queryCount(SignQuery query);

    void changeEnableByAppId(@Param("id") Long id, @Param("enable") Integer enable);
}
