package com.mkg_dhbw.xcalc.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LatestRates {

    private Currency baseCurrency;
    private List<Rate> rates;

    public LatestRates(Currency baseCurrency, List<Rate> rates) {
        this.baseCurrency = baseCurrency;
        this.rates = rates;
    }

    public LatestRates() {

    }

    public static LatestRates decodeJSON(String encodedJson) throws JSONException {
        StringBuffer sbuf = new StringBuffer();
        JSONObject jsonObject = new JSONObject(encodedJson);

        // Base
        String baseCurrency = jsonObject.getString("base");

        // Date
        String date = jsonObject.getString("date");

        // rates
        JSONObject rates = jsonObject.getJSONObject("rates");
        List<Rate> rateList = new ArrayList<Rate>();

        for (Currency cur : Currency.values()) {
            if (!cur.toString().contains(baseCurrency))
                rateList.add(new Rate(cur, rates.getDouble(cur.toString())));
        }

        return new LatestRates(Currency.valueOf(baseCurrency), rateList);
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "LatestRates{" +
                "baseCurrency=" + baseCurrency +
                ", rates=" + rates +
                '}';
    }
}
