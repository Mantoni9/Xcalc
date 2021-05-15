package com.mkg_dhbw.xcalc.repositories;

import android.os.AsyncTask;
import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;

public class GetLatestRepositoryTask extends AsyncTask<Currency, String, LatestRates> {

    @Override
    protected LatestRates doInBackground(Currency... currencies) {
        ApiRepository repo = new ApiRepository();
        LatestRates rates = null;
        try {
            rates = repo.getLatestRates(currencies[0]);
        } catch (Exception e) {
            Log.w("HISTORY-RATES", "Something went wrong while fetching History Rates from the API");
            e.printStackTrace();
        }
        return rates;
    }

    @Override
    protected void onPostExecute(LatestRates latestRates) {
        super.onPostExecute(latestRates);
    }
}
