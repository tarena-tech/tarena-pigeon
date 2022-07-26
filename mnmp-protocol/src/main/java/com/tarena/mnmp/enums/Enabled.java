package com.tarena.mnmp.enums;

public enum Enabled {

    NO(0, "未启用"),
    YES(1, "已启用");

    private Integer val;
    private String description;

    Enabled(Integer val, String description) {
        this.val = val;
        this.description = description;
    }

    public Integer getVal() {
        return val;
    }

    public String getDescription() {
        return description;
    }
}
