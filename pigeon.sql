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

create table notice_app
(
    id             bigint auto_increment comment '主键'
        primary key,
    create_user_id bigint   default -1                not null,
    code           varchar(32)                        null,
    name           varchar(64)                        null,
    leader         varchar(64)                        null,
    team_members   varchar(256)                       null,
    remarks        varchar(1024)                      null,
    enabled        tinyint  default 1                 not null comment '是否可用 0否 1是',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    audit_status   bigint   default 0                 null comment '审核状态 -1未通过 0审核中 1通过',
    audit_result   varchar(1024)                      null
)
    comment '应用表';

create index idx_leader
    on notice_app (leader);

create index idx_user_id
    on notice_app (create_user_id);

create table notice_phone_white_list
(
    id             bigint auto_increment comment '表格主键'
        primary key,
    phone          varchar(20)                        not null comment '电话号码',
    app_code       varchar(20)                        not null comment '班级名称,搜索筛选',
    app_name       varchar(20)                        not null,
    create_time    datetime default CURRENT_TIMESTAMP not null,
    create_user_id bigint                             not null
);

create table notice_provider
(
    id               bigint auto_increment comment '主键'
        primary key,
    name             varchar(64)                        null,
    code             varchar(64)                        null,
    notice_type      tinyint                            not null comment '支持的消息类型 1:SMS 2:xxx 3:xxx',
    official_website varchar(64)                        null,
    contacts         varchar(64)                        null,
    phone            varchar(20)                        null,
    client_config    varchar(512)                       null,
    remarks          varchar(1000)                      null,
    enabled          tinyint  default 1                 not null comment '是否可用 0否 1是',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime                           null comment '修改时间',
    audit_status     bigint   default 0                 not null comment '0审核中,1通过,-1未通过',
    audit_result     varchar(255)                       null
)
    comment '消息服务商表';

create index idx_name
    on notice_provider (name)
    comment '名称索引';

create table notice_role
(
    id          bigint auto_increment
        primary key,
    name        varchar(100) null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table notice_sms_record_target
(
    id                bigint(19) auto_increment comment '主键'
        primary key,
    app_code          varchar(255)                       null,
    task_id           bigint   default 0                 null comment '任务ID',
    target            varchar(32)                        null,
    content           varchar(2200)                      null,
    status            tinyint(3)                         not null comment '消息发送状态 0发送给供应商失败 1发送给供应商成功 2发送给目标失败 3发送给目标成功',
    trigger_time      varchar(32)                        null,
    push_time         datetime                           not null comment '发送时间',
    push_receive_time datetime                           null comment '推送到达时间',
    biz_id            varchar(128)                       null,
    send_result       varchar(128)                       null,
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       datetime                           null comment '修改时间'
)
    comment '短息发送目标表';

create index idx_push_time
    on notice_sms_record_target (push_time)
    comment '发送时间索引';

create index idx_target_trigger_time
    on notice_sms_record_target (target, trigger_time)
    comment '目标及触发时间索引';

create table notice_sms_sign
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(64)                          null,
    code           varchar(255)                         null,
    audit_status   bigint     default 0                 not null comment '审核状态 -1未通过 0审核中 1通过',
    app_id         int                                  null comment '应用ID',
    app_code       varchar(32)                          null,
    remarks        varchar(1024)                        null,
    enabled        tinyint(1) default 0                 not null comment '是否启用 0否 1是',
    creator        varchar(32)                          null,
    create_time    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime                             null comment '修改时间',
    audit_result   varchar(255)                         null,
    create_user_id bigint     default -1                not null
)
    comment '短息类型消息签名表';

create index idx_create_user_id
    on notice_sms_sign (create_user_id);

create index idx_name
    on notice_sms_sign (name)
    comment '签名索引';

create table notice_sms_template
(
    id               bigint auto_increment comment '主键'
        primary key,
    code             varchar(64)       null,
    name             varchar(256)      null,
    template_type    tinyint           null comment '模板类型',
    notice_type      tinyint           null comment '通知类型',
    content          varchar(256)      null,
    app_id           int               null comment '应用ID',
    app_code         varchar(64)       null,
    provider_id      bigint(19)        null comment '供应商id',
    remark           varchar(1024)     null,
    audit_status     tinyint           null comment '审核状态',
    audit_result     varchar(1024)     null,
    enabled          tinyint           null comment '是否可用 (0否 1是）',
    deleted          tinyint           null comment '是否删除 (0:未删除,1:已删除）',
    use_count        int               null comment '使用次数',
    create_time      datetime          null comment '创建时间',
    update_time      datetime          null comment '更新时间',
    create_user_id   bigint default -1 not null comment '创建用户ID',
    create_user_name varchar(32)       null,
    constraint idx_code
        unique (code) comment '模板code 唯一索引'
);

create index idx_app_id
    on notice_sms_template (app_id, code, name, audit_status, enabled, deleted);

create table notice_task
(
    id                 bigint auto_increment comment '主键'
        primary key,
    name               varchar(64)                          null,
    task_status        tinyint(3)                           not null comment '任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败',
    task_type          tinyint(3) default 0                 not null comment '任务类型 0:立即 1:定时 2:周期 3:条件规则触发',
    notice_type        tinyint(3)                           not null comment '消息类型',
    template_id        int(10)                              not null comment '消息模板主表ID',
    sign_id            int(10)                              null comment '签名ID',
    app_id             bigint                               not null comment '所属应用',
    cycle_level        tinyint(3)                           null comment '周期类型 1:小时 2:日 3:周 4:月 5:年',
    cycle_num          int(10)                              null comment '周期数',
    first_trigger_time datetime   default CURRENT_TIMESTAMP null comment '任务首次触发时间',
    trigger_end_time   datetime                             null comment '任务触发结束时间',
    next_trigger_time  datetime                             null comment '下次任务触发时间',
    target_type        tinyint(3) default 1                 null comment '目标类型 1:文件上传 2.规则匹配',
    target_file_name   varchar(64)                          null,
    target_file_url    varchar(522)                         null,
    creator            int(10)    default -1                not null comment '创建人',
    creator_email      varchar(64)                          null,
    creator_name       varchar(64)                          null,
    dept_id            bigint                               null comment '所属部门',
    remark             varchar(512)                         null,
    error              varchar(1024)                        null,
    create_time        datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime                             null comment '更新时间',
    audit_status       tinyint(1) default 0                 not null comment '审核 -1审核拒绝，0待审核，1审核通过',
    audit_result       varchar(255)                         null,
    mock               tinyint(1) default 0                 not null comment '是否是mock，0否，1是',
    create_user_id     bigint     default -1                not null
)
    comment '消息任务表';

create index idx_name
    on notice_task (name)
    comment '任务名称索引';

create index idx_next_trigger_time
    on notice_task (next_trigger_time);

create table notice_task_target
(
    id          bigint(19) auto_increment comment '主键'
        primary key,
    task_id     int(10)                              not null comment '消息任务ID',
    target      varchar(64)                          null,
    params      varchar(500)                         null,
    deleted     tinyint(1) default 0                 not null comment '是否删除 0否 1是',
    create_time datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                             null comment '更新时间'
)
    comment '任务目标表';

create index idx_task_id
    on notice_task_target (task_id)
    comment '任务ID索引';

create table notice_user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    username    varchar(100) null,
    password    varchar(100) null,
    create_time timestamp    null comment '创建时间',
    update_time timestamp    null comment '更新时间'
);

create table notice_user_role
(
    id          bigint auto_increment
        primary key,
    role_id     bigint    null,
    user_id     bigint    null,
    create_time timestamp null,
    update_time timestamp null
);



INSERT INTO notice_role (id, name, create_time, update_time) VALUES (1, 'ROLE_root', '2022-08-08 17:37:51', '2022-08-08 17:37:51');
INSERT INTO notice_role (id, name, create_time, update_time) VALUES (2, 'ROLE_admin', '2022-08-08 17:37:51', '2022-08-08 17:37:51');
INSERT INTO notice_role (id, name, create_time, update_time) VALUES (3, 'ROLE_user', '2022-08-08 17:37:51', '2022-08-08 17:37:51');


INSERT INTO notice_user (id, username, password, create_time, update_time) VALUES (1, 'root', '$2a$10$CKYuNVLTZrxItObnUj/Vs.EdtoCQ/koC2M79m0uCxvvffhsu5uMf2', '2022-08-08 17:36:34', '2022-08-08 17:36:34');
INSERT INTO notice_user (id, username, password, create_time, update_time) VALUES (2, 'admin', '$2a$10$CKYuNVLTZrxItObnUj/Vs.EdtoCQ/koC2M79m0uCxvvffhsu5uMf2', '2022-08-08 17:36:34', '2022-08-08 17:36:34');
INSERT INTO notice_user (id, username, password, create_time, update_time) VALUES (3, 'user', '$2a$10$CKYuNVLTZrxItObnUj/Vs.EdtoCQ/koC2M79m0uCxvvffhsu5uMf2', '2022-08-08 17:36:34', '2022-08-08 17:36:34');



INSERT INTO notice_user_role (id, role_id, user_id, create_time, update_time) VALUES (1, 1, 1, '2022-08-08 17:36:34', '2022-08-08 17:36:34');
INSERT INTO notice_user_role (id, role_id, user_id, create_time, update_time) VALUES (2, 2, 2, '2022-08-08 17:36:34', '2022-08-08 17:36:34');
INSERT INTO notice_user_role (id, role_id, user_id, create_time, update_time) VALUES (3, 3, 3, '2022-08-08 17:36:34', '2022-08-08 17:36:34');
