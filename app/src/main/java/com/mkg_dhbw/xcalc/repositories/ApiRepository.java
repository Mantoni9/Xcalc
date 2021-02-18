package com.mkg_dhbw.xcalc.repositories;

import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRepository {

    String baseUrl = "https://api.exchangeratesapi.io";

    public LatestRates getLatestRates(Currency baseCurrency) throws Exception {
        URL url = new URL(String.format("%s/latest", baseUrl));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        Log.i("TEST", "Response Code von HTTP-Request: " + connection.getResponseCode() + " - " + connection.getResponseMessage() );

        InputStream is = connection.getInputStream();
        InputStreamReader ris = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(ris);
        StringBuilder resultJSON = new StringBuilder();

        String line = "";
        while ( (line = reader.readLine()) != null) {

            resultJSON.append(line);
        }

        return LatestRates.decodeJSON(resultJSON.toString());
    }

}
