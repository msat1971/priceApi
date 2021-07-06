package com.kapitus.challenge.model.coingecko;

import com.google.gson.annotations.SerializedName;

public class GetCoinsResponse {

    @SerializedName("data")
    private Object data = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("error")
    private ErrorObject error = null;

    public void setError(ErrorObject error) {
        this.error = error;
    }

    public GetCoinsResponse() {
        super();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
