package com.kapitus.challenge.service.kapitus;

import com.kapitus.challenge.model.coingecko.Coin;
import com.kapitus.challenge.model.coingecko.Ping;
import com.kapitus.challenge.service.coingecko.ApiClient;
import com.kapitus.challenge.service.coingecko.ApiService;
import com.kapitus.challenge.service.coingecko.Client;

import java.util.List;
import java.util.Map;

public class CoinGeckoApi implements ApiClient {
    private final ApiService apiService;
    private final Client coinGeckoApi;

    public CoinGeckoApi() {
        this.coinGeckoApi = new Client();
        this.apiService = coinGeckoApi.createService(ApiService.class);

    }

    public Ping ping() {
        return coinGeckoApi.executeSync(apiService.ping());
    }

    public Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies) {
        return getPrice(ids, vsCurrencies, false, false, false, false);
    }

    public Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt) {
        return coinGeckoApi.executeSync(apiService.getPrice(ids, vsCurrencies, includeMarketCap, include24hrVol, include24hrChange, includeLastUpdatedAt));
    }

    public List<Coin> getCoinList() {
        return coinGeckoApi.executeSync(apiService.getCoinList());
    }
}
