package com.skittles.buyticket.param;

import javax.validation.constraints.NotNull;

public class MsgLoginParam {
   @NotNull
    private String phoneNumber;
    @NotNull
    private String msgCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
