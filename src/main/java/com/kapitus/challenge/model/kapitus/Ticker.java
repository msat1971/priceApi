package com.kapitus.challenge.model.kapitus;

import com.kapitus.challenge.model.coingecko.Coin;

import java.util.ArrayList;
import java.util.List;

public class Ticker {
    String symbol = "";
    List<Coin> coinList = new ArrayList<>();

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<Coin> idList) {
        this.coinList = idList;
    }

}
