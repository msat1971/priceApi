package com.kapitus.challenge.service.coingecko;

import com.kapitus.challenge.PriceApiException;
import com.kapitus.challenge.PriceApiUtil;
import com.kapitus.challenge.lambda.HandlerApiGateway;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private final String API_BASE_URL = "https://api.coingecko.com/api/v3/";

    private final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private final Retrofit retrofit = builder.build();

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public <T> T executeSync(Call<T> call) throws PriceApiException{
        logger.info("Call {}", call.request().toString());
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                try {
                    ApiError apiError = getCoinGeckoApiError(response);
                    apiError.setCode(response.code());
                    throw new PriceApiException(apiError);
                } catch (IOException e) {
                    throw new PriceApiException(response.toString(), e);
                }
            }
        } catch (IOException e) {
            throw new PriceApiException(e);
        }
    }

    private ApiError getCoinGeckoApiError(Response<?> response) throws IOException {
        return (ApiError) retrofit.responseBodyConverter(ApiError.class, new Annotation[0])
                .convert(response.errorBody());

    }
}
