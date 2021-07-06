package com.kapitus.challenge;

import com.kapitus.challenge.service.coingecko.ApiError;

public class PriceApiException extends RuntimeException {
    private static final long serialVersionUID = 6066241466741235696L;

    private final ApiError error;

    public PriceApiException(ApiError error) {
        this.error = error;
    }

    public PriceApiException(Throwable cause) {
        super(cause);
        error = null;
    }

    public PriceApiException(String message, Throwable cause) {
        super(message, cause);
        error = null;
    }

    public PriceApiException(String message) {
        super(message);
        error = null;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            return error.toString();
        }
        return super.getMessage();
    }
}
