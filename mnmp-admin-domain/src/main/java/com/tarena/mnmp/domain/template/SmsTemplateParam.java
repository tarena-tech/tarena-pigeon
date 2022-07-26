package com.tarena.mnmp.domain.template;

import java.util.Date;

public class SmsTemplateParam {


    private Long id;

    private String code;

    private Long appId;

    private String appCode;

    private String clientConfig;

    private String name;

    private Integer templateType;

    private Integer noticeType;

    private String content;

    private String remark;

    private Integer auditStatus;

    private String auditResult;

    private Integer enabled;

    private Integer useCount;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private Integer createUserId;

    private String createUserName;


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

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(String clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override public String toString() {
        return "SmsTemplateDO{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", appId=" + appId +
            ", appCode='" + appCode + '\'' +
            ", clientConfig='" + clientConfig + '\'' +
            ", name='" + name + '\'' +
            ", templateType=" + templateType +
            ", noticeType=" + noticeType +
            ", content='" + content + '\'' +
            ", remark='" + remark + '\'' +
            ", auditStatus=" + auditStatus +
            ", auditResult='" + auditResult + '\'' +
            ", enabled=" + enabled +
            ", useCount=" + useCount +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", deleted=" + deleted +
            ", createUserId=" + createUserId +
            ", createUserName='" + createUserName + '\'' +
            '}';
    }
}
