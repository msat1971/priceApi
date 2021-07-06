package com.kapitus.challenge;

import com.kapitus.challenge.model.coingecko.*;
import com.kapitus.challenge.model.kapitus.Ticker;
import com.kapitus.challenge.service.coingecko.ApiClient;
import com.kapitus.challenge.service.kapitus.CoinGeckoApi;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceApi {
    private static final Logger logger = LoggerFactory.getLogger(PriceApi.class);
    private static final ApiClient apiClient;
    private static final Map<String, Ticker> tickerMap;
    private static boolean tickersLoaded = false;

    static {
        apiClient = new CoinGeckoApi();
        tickerMap = new HashMap<>();
    }

    private PriceApi() {
    }

    public static boolean isTickersLoaded() {
        return tickersLoaded;
    }

    public static final boolean serviceIsRunning() {
        boolean running;
        try {
            apiClient.ping();
            running = true;
        } catch (PriceApiException e) {
            running = false;
        }
        return running;
    }

    public static final void loadTickers() {
        if (serviceIsRunning()) {
            logger.info("CoinGecko is live!");
            tickerMap.clear();
            List<Coin> coinList = apiClient.getCoinList();
            logger.info("Creating ticker to ID mapping");
            for (Coin coin : coinList) {
                String symbol = coin.getSymbol().toLowerCase();
                Ticker ticker;
                ticker = tickerMap.getOrDefault(symbol, new Ticker());
                ticker.setSymbol(symbol);
                ticker.getCoinList().add(coin);
                tickerMap.putIfAbsent(symbol, ticker);
            }
            logger.info("Generated {} ticker mappings", tickerMap.size());
            tickersLoaded = true;
        } else {
            logger.warn("CoinGecko is not live");
        }
    }

    public static final List<Coin> getCoinsFromTickers(String symbols) throws PriceApiException {
        logger.info("Finding coins for symbols \"{}\"", symbols);
        //Load tickers (ticker and Id lookup list) if not loaded
        if (!tickersLoaded) loadTickers();

        if (!tickersLoaded) {
            throw new PriceApiException("No matching ticker symbols are found.");
        } else {
            String[] symbolTokens = symbols.split("\\s*,\\s*");
            List<String> tickerList = new ArrayList<>(Arrays.asList(symbolTokens));

            List<Coin> coinList = new ArrayList<>();
            // For each symbol supplied, look for corresponding entry in tickerMap
            for (String tickerSymbol : tickerList) {
                String searchSymbol = tickerSymbol.trim().toLowerCase();
                if (tickerMap.containsKey(searchSymbol)) {
                    Ticker matchingTicker;
                    matchingTicker = tickerMap.get(searchSymbol);
                    coinList.addAll(matchingTicker.getCoinList());
                }
            }

            logger.info("Found {} matching coins", coinList.size());
            String json = PriceApiUtil.convertToJson(coinList);
            logger.debug("coinList = {}", json);
            return coinList;
        }
    }

    public static final String getCoinIds(List<Coin> coinList) throws PriceApiException {
        StringBuilder sb = new StringBuilder();
        for (Coin c : coinList) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(c.getId());
        }
        return sb.toString();
    }

    public static final List<Coin> getCoinPricesAsList(String symbols) throws PriceApiException {
        String idList;
        List<Coin> coinList;
        Map<String, Map<String, Double>> prices;
        try {
            coinList = getCoinsFromTickers(symbols);
            idList = getCoinIds(coinList);
        } catch (PriceApiException e) {
            throw new PriceApiException("Unable to get prices", e);
        }

        // Get all prices in a single call
        prices = apiClient.getPrice(idList, Currency.USD.toString());

        //loop coinList and copy prices
        for (Coin coin : coinList) {
            String id = coin.getId();
            if (prices.containsKey(id)) {
                Map<String, Double> priceObj = prices.get(id);
                Double price;
                price = priceObj.getOrDefault(Currency.USD.toString(), null);
                coin.setPrice(price.toString());
            }
        }
        return coinList;
    }

    public static GetCoinsResponse getCoinPrice(String tickerString) {
        List<Coin> coinList;
        GetCoinsResponse response = null;
        try {
            coinList = PriceApi.getCoinPricesAsList(tickerString);
            int size = coinList.size();
            if (size > 1) {
                logger.info("Returning array of coins");
                response = new ResponseMultiResource();
                response.setData(coinList);
                response.setStatus("success");

            } else if (size == 1 ) {
                response = new ResponseSingleResource();
                response.setData(coinList.get(0));
                response.setStatus("success");
            } else {
                response = new GetCoinsResponse();
                ErrorObject error = new ErrorObject();
                error.setMessage(String.format("No prices for %s", tickerString));
                error.setCode("N/A");
                response.setError(error);
                response.setStatus("No matching prices found");
            }
        } catch (PriceApiException e) {
            logger.error(e.getMessage(), e);
            response = createExceptionResponse("Failed getting prices", e);
        }

        return  response;

    }

    public static GetCoinsResponse createExceptionResponse(String status, Exception e) {
        GetCoinsResponse response = new GetCoinsResponse();
        ErrorObject error = new ErrorObject();
        error.setMessage(e.getMessage());
        error.setCode("N/A");
        error.setData(PriceApiUtil.getStackTrace(e));
        response.setError(error);
        response.setStatus(status);
        return response;
    }

    public static void main(String[] args) {
        String json = PriceApiUtil.convertToJson(PriceApi.getCoinPrice("BTC, ETH, DOGE"));
        logger.info(json);

        String jsonSingle = PriceApiUtil.convertToJson(PriceApi.getCoinPrice("BTC"));
        logger.info(jsonSingle);
    }

}
