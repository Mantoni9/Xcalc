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
        History history = new History();

        try {
            history = repo.getHistory(LocalDate.parse("2021-01-01"), LocalDate.parse("2021-02-01"), Currency.EUR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return history;
    }

    @Override
    protected void onPostExecute(History history) {
        super.onPostExecute(history);
        // do stuff in UI e.g. set TextViews...
        Log.i("HISTORY-RATES", history.toString());
    }
}
