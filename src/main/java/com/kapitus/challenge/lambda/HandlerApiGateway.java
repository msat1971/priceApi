package com.kapitus.challenge.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.kapitus.challenge.PriceApi;
import com.kapitus.challenge.PriceApiException;
import com.kapitus.challenge.PriceApiUtil;
import com.kapitus.challenge.model.coingecko.GetCoinsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * The type Handler api gateway.
 */
public class HandlerApiGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger logger = LoggerFactory.getLogger(HandlerApiGateway.class);

    /**
     * Log environment.
     *
     * @param event   the event
     * @param context the context
     */
    private static void logEnvironment(Object event, Context context) {
        LambdaLogger logger = context.getLogger();
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + PriceApiUtil.convertToJson(System.getenv()));
        logger.log("CONTEXT: " + PriceApiUtil.convertToJson(context));
        // log event details
        logger.log("EVENT: " + PriceApiUtil.convertToJson(event));
        logger.log("EVENT TYPE: " + event.getClass().toString());
    }

    /**
     * Generate success response.
     *
     * @param apiGatewayProxyResponseEvent the api gateway proxy response event
     * @param responseMessage              the request message
     */
    private static void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String responseMessage) {
        apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
        apiGatewayProxyResponseEvent.setStatusCode(200);
        apiGatewayProxyResponseEvent.setBody(responseMessage);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        logEnvironment(event, context);
        String httpMethod = event.getHttpMethod();
        try {
            if (httpMethod.equalsIgnoreCase("GET")) {
                Map<String, String> pathParams = event.getPathParameters();
                if (pathParams.containsKey("ticker")) {
                    GetCoinsResponse responseObject;
                    String tickerString;
                    String responseMessage;

                    tickerString = pathParams.get("ticker");

                    //Get any matching prices
                    responseObject = PriceApi.getCoinPrice(tickerString);
                    // Convert response to json
                    responseMessage = PriceApiUtil.convertToJson(responseObject);
                    logger.info("responseMessage: {}", responseMessage);

                    generateResponse(apiGatewayProxyResponseEvent, responseMessage);
                } else {
                    throw new PriceApiException("Missing path parameter value for 'ticker'");
                }
            } else {
                throw new PriceApiException(String.format("Unrecognized httpMethod: %s", httpMethod));
            }
        } catch (PriceApiException p) {
            // Any unhandled exceptions fall here
            logger.error(p.getMessage(), p);
            GetCoinsResponse responseObject;
            String responseMessage;

            // The response is formatted as a ResponseError
            responseObject = PriceApi.createExceptionResponse("Failed in " + this.getClass().getName(), p);
            responseMessage = PriceApiUtil.convertToJson(responseObject);
            generateResponse(apiGatewayProxyResponseEvent, responseMessage);
        }


        return apiGatewayProxyResponseEvent;
    }
}
