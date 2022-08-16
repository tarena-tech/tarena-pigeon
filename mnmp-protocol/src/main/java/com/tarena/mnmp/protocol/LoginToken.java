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

package com.tarena.mnmp.protocol;

import java.io.Serializable;
import java.util.List;

public class LoginToken implements Serializable {
    private Long id;
    private String username;

    private String role;

    public String getRole() {
        return this.authorities.get(0);
    }

    public void setRole(String role) {
        this.role = role;
    }

    private List<String> authorities;

    private String deviceIp;

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override public String toString() {
        return "LoginToken{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", role='" + role + '\'' +
            ", authorities=" + authorities +
            ", deviceIp='" + deviceIp + '\'' +
            '}';
    }
}
