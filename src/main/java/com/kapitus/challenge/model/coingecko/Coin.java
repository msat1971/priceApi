package com.kapitus.challenge.model.coingecko;

import com.google.gson.annotations.SerializedName;

public class Coin {
    @SerializedName("symbol")
    private String symbol;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("price")
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
