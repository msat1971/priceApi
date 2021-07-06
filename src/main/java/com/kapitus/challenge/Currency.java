package com.kapitus.challenge;

// See https://www.coingecko.com/api/documentations/v3#/simple/get_simple_supported_vs_currencies

public enum Currency {
    BTC ("btc"),
    ETH ("eth"),
    LTC ("ltc"),
    BCH ("bch"),
    BNB ("bnb"),
    EOS ("eos"),
    XRP ("xrp"),
    XLM ("xlm"),
    LINK ("link"),
    DOT ("dot"),
    YFI ("yfi"),
    USD ("usd"),
    AED ("aed"),
    ARS ("ars"),
    AUD ("aud"),
    BDT ("bdt"),
    BHD ("bhd"),
    BMD ("bmd"),
    BRL ("brl"),
    CAD ("cad"),
    CHF ("chf"),
    CLP ("clp"),
    CNY ("cny"),
    CZK ("czk"),
    DKK ("dkk"),
    EUR ("eur"),
    GBP ("gbp"),
    HKD ("hkd"),
    HUF ("huf"),
    IDR ("idr"),
    ILS ("ils"),
    INR ("inr"),
    JPY ("jpy"),
    KRW ("krw"),
    KWD ("kwd"),
    LKR ("lkr"),
    MMK ("mmk"),
    MXN ("mxn"),
    MYR ("myr"),
    NGN ("ngn"),
    NOK ("nok"),
    NZD ("nzd"),
    PHP ("php"),
    PKR ("pkr"),
    PLN ("pln"),
    RUB ("rub"),
    SAR ("sar"),
    SEK ("sek"),
    SGD ("sgd"),
    THB ("thb"),
    TRY ("try"),
    TWD ("twd"),
    UAH ("uah"),
    VEF ("vef"),
    VND ("vnd"),
    ZAR ("zar"),
    XDR ("xdr"),
    XAG ("xag"),
    XAU ("xau"),
    BITS ("bits"),
    SATS ("sats");

    private final String name;

    private Currency(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
