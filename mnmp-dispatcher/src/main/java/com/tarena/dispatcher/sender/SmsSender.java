package com.tarena.dispatcher.sender;

import com.tarena.dispatcher.SmsTarget;
import com.tarena.dispatcher.bo.PhoneBizIdReceiptBO;
import java.util.List;

public interface SmsSender {
    String send(SmsTarget smsTarget) throws Exception;

    List<PhoneBizIdReceiptBO> fetchReceipt(List<PhoneBizIdReceiptBO> receipts);
}
