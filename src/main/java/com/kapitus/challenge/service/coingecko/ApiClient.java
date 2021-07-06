package com.kapitus.challenge.service.coingecko;

import com.kapitus.challenge.PriceApiException;
import com.kapitus.challenge.model.coingecko.Coin;
import com.kapitus.challenge.model.coingecko.Ping;

import java.util.List;
import java.util.Map;

public interface ApiClient {
    Ping ping() throws PriceApiException;

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies) throws PriceApiException;

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol,
                                              boolean include24hrChange, boolean includeLastUpdatedAt) throws PriceApiException;

    List<Coin> getCoinList() throws PriceApiException;
}
