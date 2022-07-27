package com.tarena.mnmp.enums;

public enum AuditStatus {


    REJECT(-1, "拒绝"),
    WAITING(0, "等待审核"),
    PASS(1, "通过"),
    ;


    private Integer status;

    private String desc;

    AuditStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
