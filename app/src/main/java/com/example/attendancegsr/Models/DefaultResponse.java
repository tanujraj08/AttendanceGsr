package com.example.attendancegsr.Models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("status")
    private String sts;

    @SerializedName("message")
    private String msg;

    public DefaultResponse(String sts, String msg) {
        this.sts = sts;
        this.msg = msg;
    }

    public String getSts() {
        return sts;
    }

    public String getMsg() {
        return msg;
    }

}
