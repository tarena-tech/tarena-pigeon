package com.tarena.dispatcher.storage.mapper;

import com.tarena.dispatcher.storage.entity.TemplateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 消息模板主表(NoticeTemplate)表
 *
 * @author liuhuan
 */
@Mapper
public interface TemplateDao {

    @Select("select code,content,app_code as appCode from notice_template where id = #{templateId}")
    TemplateDO selectById(@Param("templateId") Long templateId);

}

