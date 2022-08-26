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

package com.tarena.mnmp.mq.rocketmq.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {
    public static void main(String[] args) {
        while (true) {
            String msg = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input sending msg");
            try {
                msg = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (msg.equals("q")) {
                System.exit(-1);
            }
        }
    }
}
