package com.tarena.dispatcher.storage.mapper;

import com.tarena.dispatcher.storage.entity.SmsSignDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 短息类型消息签名表(NoticeSmsSign)表
 *
 * @author liuhuan
 */
@Mapper
public interface SmsSignDao {

    @Select("select id , name from notice_sms_sign where id = #{signId}")
    SmsSignDO selectById(@Param("signId") Long signId);
}

