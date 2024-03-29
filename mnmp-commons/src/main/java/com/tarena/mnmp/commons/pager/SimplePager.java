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
package com.tarena.mnmp.commons.pager;

import java.io.Serializable;

public class SimplePager implements Serializable {

    public SimplePager() {
    }

    protected Integer pageSize = 10;
    /**
     * current page index
     */
    protected Integer currentPageIndex;

    public SimplePager(Integer pageSize, Integer currentPageIndex) {
        if (pageSize != null) {
            this.pageSize = pageSize;
        }
        if (currentPageIndex == null) {
            currentPageIndex = 1;
        }
        this.currentPageIndex = currentPageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPageIndex(Integer currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public String getLimitClause() {
        //no page
        if (pageSize <= 0) {
            return "";
        }
        if (this.currentPageIndex == null) {
            this.currentPageIndex = 1;
        }
        int offset = pageSize * (this.currentPageIndex <= 1 ? 0 : this.currentPageIndex - 1);
        return " limit " + offset + "," + this.getPageSize();
    }
}
