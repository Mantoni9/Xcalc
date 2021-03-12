package com.mkg_dhbw.xcalc.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mkg_dhbw.xcalc.models.LatestRates;

public class InsertHistoryRequestInDatabaseTask extends AsyncTask<LatestRates, String, LatestRates> {

    private Context context;

    public InsertHistoryRequestInDatabaseTask(Context context) {
        this.context = context;
    }

    @Override
    protected LatestRates doInBackground(LatestRates... latestRates) {

        SQLiteRepository repository = new SQLiteRepository(context);

        repository.SaveEntry(latestRates[0]);

        repository.DeleteEntry(1);

        return repository.ReadEntry();
    }

    @Override
    protected void onPostExecute(LatestRates latestRates) {
        super.onPostExecute(latestRates);
        // do stuff in UI e.g. set TextViews...
        Log.i("SQLITE", latestRates.toString());
    }
}
