package com.mkg_dhbw.xcalc.repositories;

import android.os.AsyncTask;
import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.History;
import com.mkg_dhbw.xcalc.models.HistoryRequest;

import java.time.LocalDate;

public class GetHistoryRepositoryTask extends AsyncTask<HistoryRequest, String, History> {
    @Override
    protected History doInBackground(HistoryRequest... historyRequests) {
        ApiRepository repo = new ApiRepository();
        History history = null;
        HistoryRequest historyRequest = historyRequests[0];

        try {
            history = repo.getHistory(historyRequest.getStartDate(), historyRequest.getEndDate(), historyRequest.getBaseCurrency());
        } catch (Exception e) {
            Log.w("HISTORY-RATES", "Something went wrong while fetching History Rates from the API");
            e.printStackTrace();
        }
        return history;
    }

    @Override
    protected void onPostExecute(History history) {
        super.onPostExecute(history);
    }
}
