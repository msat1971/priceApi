package com.kapitus.challenge;

import com.kapitus.challenge.model.coingecko.Coin;
import com.kapitus.challenge.model.coingecko.GetCoinsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceApiTest {

    String testTickers;
    String testIds;
    String testTicker;
    String testId;
    String noRecordResults;

    @BeforeEach
    void setUp() {
        testTicker = "btc";
        testId = "bitcoin";
        testTickers = "BTC, ETH, DOGE";
        testIds = "bitcoin,ethereum,binance-peg-dogecoin,dogecoin";

        noRecordResults = "{\"status\":\"No matching prices found\",\"error\":{\"code\":\"N/A\",\"message\":\"No prices for fake ticker\"}}";
    }

    @Test
    void serviceIsRunning() {
        assertTrue(PriceApi.serviceIsRunning());
    }

    @Test
    void loadTickers() {
        PriceApi.loadTickers();
        assertTrue(PriceApi.isTickersLoaded());
    }

    @Test
    void getCoinIdsMulti() {
        String coinIDs;
        List<Coin> coinList;
        try {
            coinList = PriceApi.getCoinsFromTickers(testTickers);
            coinIDs = PriceApi.getCoinIds(coinList);
            assertEquals(testIds, coinIDs);
        } catch (PriceApiException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getCoinIdsSingle() {
        String coinId;
        List<Coin> coinList;
        try {
            coinList = PriceApi.getCoinsFromTickers(testTicker);
            coinId = PriceApi.getCoinIds(coinList);
            assertEquals(testId, coinId);
        } catch (PriceApiException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getPrices() {
        List<Coin> coinList;
        coinList =  PriceApi.getCoinPricesAsList(testTickers);
        assertEquals(4, coinList.size());
    }

    @Test
    void testZeroPrices() {
        GetCoinsResponse response = PriceApi.getCoinPrice("fake ticker");
        assertEquals(noRecordResults, PriceApiUtil.convertToJson(response));
    }
}