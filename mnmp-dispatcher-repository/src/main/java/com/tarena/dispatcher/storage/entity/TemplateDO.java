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

package com.tarena.dispatcher.storage.entity;

import java.io.Serializable;
import lombok.Data;
@Data
public class TemplateDO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 模板全称
     */
    private String name;
    /**
     * 模板CODE码
     */
    private String code;
    /**
     * 模板内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer noticeType;
    /**
     * 所属应用
     */
    private Long appId;
    private String appCode;

}
