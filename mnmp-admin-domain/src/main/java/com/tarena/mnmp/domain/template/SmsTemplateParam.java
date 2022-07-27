package com.tarena.mnmp.domain.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "创建模板入参")
public class SmsTemplateParam {


    private Long id;

    @ApiModelProperty(value = "模板编码", required = true)
    @NotBlank(message = "模板编码不能为空")
    private String code;

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appid为必填项")
    private Long appId;

    @ApiModelProperty(value = "appCode", required = true)
    @NotBlank(message = "appcode为必填项")
    private String appCode;


    @ApiModelProperty(value = "clientConfig，前端请设置文本框，让用户自己填写json或者其他形式的数据", required = true)
    @NotBlank(message = "密钥配置为必填项")
    private String clientConfig;

    @ApiModelProperty(value = "模板名称", required = true)
    @NotBlank(message = "模板名称为必填项")
    private String name;

    @ApiModelProperty(value = "模板类型: 0-全部，1-短信通通知，2-验证码，3-推广短信", required = true)
    @NotBlank(message = "模板类型必填项")
    private Integer templateType;

    @ApiModelProperty("通知类型： 1-sms,2-email,3-wechat")
    @NotNull(message = "通知类型为必填项")
    private Integer noticeType;

    @ApiModelProperty(value = "模板内容",required = true)
    @NotBlank(message = "模板内容为必填项")
    private String content;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核状态, 修改时 使用")
    private Integer auditStatus;

    @ApiModelProperty("审核时填写的文案, 修改时 使用")
    private String auditResult;

    @ApiModelProperty("是否可用 (0否 1是）, 修改时 使用")
    private Integer enabled;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
