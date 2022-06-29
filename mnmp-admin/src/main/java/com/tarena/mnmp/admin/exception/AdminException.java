package com.tarena.mnmp.admin.exception;

import com.tarena.mnmp.commons.protocol.BusinessException;

public class AdminException extends BusinessException {
    public AdminException(String code, String msg) {
        super(code, msg);
    }
}
