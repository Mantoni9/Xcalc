package com.mkg_dhbw.xcalc.repositories;

import android.os.AsyncTask;
import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;

public class GetLatestRepositoryTask extends AsyncTask<Currency, String, LatestRates> {

    @Override
    protected LatestRates doInBackground(Currency... currencies) {
        ApiRepository repo = new ApiRepository();
        LatestRates rates = new LatestRates();

        try {
            rates = repo.getLatestRates(currencies[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rates;
    }

    @Override
    protected void onPostExecute(LatestRates latestRates) {
        super.onPostExecute(latestRates);
        // do stuff in UI e.g. set TextViews...
        Log.i("LATEST-RATES", latestRates.toString());
    }
}
