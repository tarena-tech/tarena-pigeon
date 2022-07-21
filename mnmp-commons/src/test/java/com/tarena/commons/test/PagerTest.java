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

package com.tarena.commons.test;

import com.tarena.mnmp.commons.pager.PagerResultAddition;
import java.util.ArrayList;
import java.util.List;

public class PagerTest {

    public static void main(String[] args) {
        PagerResultAddition<String,Integer> pagerResultAddition=new PagerResultAddition<>();
        pagerResultAddition.setAddition(1000);
        pagerResultAddition.setCurrentPageIndex(1);
        pagerResultAddition.setPageSize(100);
        pagerResultAddition.setRecordCount(10000L);
        List<String> list=new ArrayList<>();
        list.add("TEST1");
        list.add("TEST2");
        list.add("TEST3");
        pagerResultAddition.setList(list);
        System.out.println(pagerResultAddition.getLastPageIndex());
    }
}
