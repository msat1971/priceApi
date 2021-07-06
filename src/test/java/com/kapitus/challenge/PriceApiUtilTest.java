package com.kapitus.challenge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceApiUtilTest {

    @Test
    void getStackTrace() {
        PriceApiException e = new PriceApiException("Test");
        assertTrue(PriceApiUtil.getStackTrace(e).length() > 10);
    }
}