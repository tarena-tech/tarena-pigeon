package com.tarena.mnmp.enums;

public enum TaskAuditStatus {

    WAIT(0, "WAIT"),
    PASS(1, "PASS"),
    REJECT(2, "REJECT"),
    ;


    private Integer status;

    private String message;

    TaskAuditStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }



}
