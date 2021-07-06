package com.kapitus.challenge.service.coingecko;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApiError implements Serializable {

    private static final long serialVersionUID = -8667568693410259867L;

    @SerializedName("code")
    private int code;
    @SerializedName("error")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
