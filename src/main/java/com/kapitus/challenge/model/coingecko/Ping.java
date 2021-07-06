package com.kapitus.challenge.model.coingecko;

import com.google.gson.annotations.SerializedName;

public class Ping {

    @SerializedName("gecko_says")
    private String geckoSays;

    public String getGeckoSays() {
        return geckoSays;
    }
}