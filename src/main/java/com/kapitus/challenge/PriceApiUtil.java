package com.kapitus.challenge;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The type Price api util.
 */
public class PriceApiUtil {

    /**
     * Convert to json string.
     *
     * @param o the o
     * @return the string
     */
    public static String convertToJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * Convert from json t.
     *
     * @param <T>      the type parameter
     * @param json     the json
     * @param classOfT the class of t
     * @return the t
     */
    public static <T> T convertFromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * Gets stack trace.
     *
     * @param e the e
     * @return the stack trace
     */
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
