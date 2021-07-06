package com.kapitus.challenge.service.coingecko;

import com.kapitus.challenge.model.coingecko.Coin;
import com.kapitus.challenge.model.coingecko.Ping;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface ApiService {
    @GET("ping")
    Call<Ping> ping();

    @GET("simple/price")
    Call<Map<String, Map<String, Double>>> getPrice(@Query("ids") String ids,
                                                    @Query("vs_currencies") String vsCurrencies,
                                                    @Query("include_market_cap") boolean includeMarketCap,
                                                    @Query("include_24hr_vol") boolean include24hrVol,
                                                    @Query("include_24hr_change") boolean include24hrChange,
                                                    @Query("include_last_updated_at") boolean includeLastUpdatedAt);

    @GET("coins/list")
    Call<List<Coin>> getCoinList();

}
