package com.mkg_dhbw.xcalc.repositories;

import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.History;
import com.mkg_dhbw.xcalc.models.LatestRates;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class ApiRepository {
    String baseUrl = "https://api.frankfurter.app";

    public LatestRates getLatestRates(Currency baseCurrency) throws Exception {
        URL url = new URL(String.format("%s/latest?base=%s", baseUrl, baseCurrency.toString()));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        Log.i("LATEST-RATES", "Response Code von HTTP-Request: " + connection.getResponseCode() + " - " + connection.getResponseMessage());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = connection.getInputStream();
            InputStreamReader ris = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(ris);
            StringBuilder resultJSON = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                resultJSON.append(line);
            }

            return LatestRates.decodeJSON(resultJSON.toString());
        }
        return null;
    }

    public History getHistory(LocalDate startDate, LocalDate endDate, Currency baseCurrency) throws Exception {
        URL url = new URL(String.format("%s/%s..%s?base=%s", baseUrl, startDate, endDate, baseCurrency));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        Log.i("HISTORY-RATES", "Response Code von HTTP-Request: " + connection.getResponseCode() + " - " + connection.getResponseMessage());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = connection.getInputStream();
            InputStreamReader ris = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(ris);
            StringBuilder resultJSON = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                resultJSON.append(line);
            }

            Log.i("HISTORY-RATES", "" + resultJSON.toString());
            return History.decodeJSON(resultJSON.toString());
        }
        return null;
    }
}
