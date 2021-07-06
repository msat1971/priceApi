package com.kapitus.challenge;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PriceApiUtil {

    public static String convertToJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static <T> T convertFromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
