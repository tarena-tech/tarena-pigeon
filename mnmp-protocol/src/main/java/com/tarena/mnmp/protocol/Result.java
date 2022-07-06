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

import com.tarena.mnmp.constant.Constant;
import java.io.Serializable;

public class Result<T> implements Serializable {
    private Result() {
        this.code = Constant.RESULT_OK_CODE;
    }

    public Result(T data) {
        if (data instanceof BusinessException) {
            BusinessException error = (BusinessException) data;
            this.code = error.getCode();
            this.error = error.getMessage();
        } else {
            this.code = Constant.RESULT_OK_CODE;
            this.data = data;
        }
    }

    private Result(String code, String message) {
        this.code = code;
        this.error = message;
    }

    private String code;

    private String error;

    private T data;

    private static Result success = new Result();

    public static Result success() {
        return success;
    }

    public static Result fail(String errorCode, String message) {
        return new Result(errorCode, message);
    }

    public static Result fail(BusinessException business) {
        return new Result(business.getCode(), business.getMessage());
    }

    public static Result fail() {
        String code = "-1";
        String msg = "system error";
        return new Result(code, msg);
    }

    public T getData() {
        return this.data;
    }

    public boolean isSucceed() {
        return this.code.equals(Constant.RESULT_OK_CODE);
    }

    public String getCode() {
        return this.code;
    }

    public String getError() {
        return error;
    }
}
